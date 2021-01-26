package com.nynu.goule.service.impl;

import com.nynu.goule.common.Result;
import com.nynu.goule.mapper.LoginUserMapper;
import com.nynu.goule.mapper.RoleAuthMapper;
import com.nynu.goule.pojo.LoginUser;
import com.nynu.goule.pojo.OperateLog;
import com.nynu.goule.service.OperateLogService;
import com.nynu.goule.service.RoleService;
import com.nynu.goule.utils.JsonUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

/**
 * @author  goule
 * @date  2021/1/22 9:35
 */

@Service
public class RoleServiceImpl implements RoleService {
    @Resource
    RoleAuthMapper roleAuthMapper;
    @Resource
    LoginUserMapper loginUserMapper;
    @Resource
    private OperateLogService operateLogService;

    @Override
    public Result getAllRoles() {
        Result result = new Result();
        List<Map<String, Object>> roles = roleAuthMapper.getAllRoles();
        result.setData(roles);
        result.setStatus(Result.RTN_CODE.SUCCESS);
        return result;
    }

    @Override
    public Result getAccountRoles(Map<String, Object> param) {
        Result result = new Result();
        Map<String, Object> resultMap = new HashMap<>();
        //查询已有角色
        List<Map<String, Object>> existRoleList = roleAuthMapper.getExistRoleList(param);
        //遍历已有角色的Map 用于下条sql查询用
        //emmm  逻辑更改  代码没用了   因为必须要前端进行类比
//        List<String> existRoleIdList = new ArrayList<>();
//        for(Map<String, Object> existMap : existRoleList){
//            String roleId = (String) existMap.get("roleId");
//            existRoleIdList.add(roleId);
//        }
        //查询所有角色
        List<Map<String, Object>> allRoleList = roleAuthMapper.getAssignableRoleList();
        resultMap.put("existRoleList",existRoleList);
        resultMap.put("allRoleList",allRoleList);
        result.setData(resultMap);
        result.setStatus(Result.RTN_CODE.SUCCESS);
        return result;
    }

    @Override
    public Result updAcctRoles(Map<String, Object> param) {
        Result result = new Result();
        //被操作人账号
        String username = (String) param.get("username");
        //操作人账号
        String operator = (String) param.get("operator");
        List<String> newRoleList = (List<String>) param.get("targetKeys");
        //根据username 查询该用户的id 用于增加对应的角色
        LoginUser user = loginUserMapper.getUserIdByUserName(username);
        String userId = String.valueOf(user.getId());
        List<String> oldRoleList = roleAuthMapper.getOldRoleList(param);
        boolean isAdd = (newRoleList.size() > oldRoleList.size()) ? true : false;
        //得到需要新增或删除的权限id
        List<String> diffList = new ArrayList<>();
        if(isAdd){
            diffList = findDiff(newRoleList,oldRoleList);
            Map<String, Object> insertMap = new HashMap<>();
            insertMap.put("userId",userId);
            for(int i = 0; i < diffList.size(); i++){
                insertMap.put("roleId",diffList.get(i));
                roleAuthMapper.addAcctRoles(insertMap);
            }
            result.setMsg("用户添加角色成功");
            result.setStatus(Result.RTN_CODE.SUCCESS);

            // 记录操作日志  根据roleId集合去查询对应的map  用于记录
            Map<String, Object> afterMap = new HashMap<>();
            Map<String, Object> operateMap = new HashMap<>();
            List<String> newRoles = roleAuthMapper.findRoleNameById(diffList);
            StringBuffer sb = new StringBuffer();
            for(int i = 0; i<newRoles.size(); i++){
                sb.append(newRoles.get(i)).append(",");
            }
            afterMap.put("roleName", sb);
            String afterMsg = JsonUtil.convertObject2Json(afterMap);
            String msg = "用户" + username + "新增角色";
            operateMap.put("acctId", operator);
            operateMap.put("opType", OperateLog.OP_TYPE.ADD);
            operateMap.put("logCntt", msg);
            operateMap.put("opMenu", OperateLog.OP_PATH.USER_MANAGEMENT);
            operateMap.put("afterCntt", afterMsg);
            operateLogService.addOperateLog(operateMap);
        }else {
            Map<String, Object> delMap = new HashMap<>();
            delMap.put("userId",userId);
            diffList = findDiff(oldRoleList,newRoleList);
            for(int i = 0; i < diffList.size(); i++){
                delMap.put("roleId",diffList.get(i));
                roleAuthMapper.delAcctRoles(delMap);
            }
            result.setMsg("用户删除角色成功");
            result.setStatus(Result.RTN_CODE.SUCCESS);

            // 记录操作日志  根据roleId集合去查询对应的map  用于记录
            Map<String, Object> beforeMap = new HashMap<>();
            Map<String, Object> operateMap = new HashMap<>();
            List<String> newRoles = roleAuthMapper.findRoleNameById(diffList);
            StringBuffer sb = new StringBuffer();
            for(int i = 0; i<newRoles.size(); i++){
                sb.append(newRoles.get(i)).append(",");
            }
            beforeMap.put("roleName", sb);
            String beforeMsg = JsonUtil.convertObject2Json(beforeMap);
            String msg = "用户" + username + "删除角色";
            operateMap.put("acctId", operator);
            operateMap.put("opType", OperateLog.OP_TYPE.DELETE);
            operateMap.put("logCntt", msg);
            operateMap.put("opMenu", OperateLog.OP_PATH.USER_MANAGEMENT);
            operateMap.put("beforeCntt", beforeMsg);
            operateLogService.addOperateLog(operateMap);
        }
        return result;
    }

    /**
     * 返回两个list里的不同元素
     * @param list1  //多
     * @param list2  //少
     * @return
     */
    private List<String> findDiff (List<String> list1, List<String> list2) {
        List<String> resultList = new ArrayList<>();
        Iterator<String> iterator = list1.iterator();
        while (iterator.hasNext()) {
            String roleId1 = iterator.next();
            for (int i = 0; i < list2.size(); i++) {
                String roleId2 = list2.get(i);
                if (roleId1.equals(roleId2)) {
                    iterator.remove();
                }
            }
        }
        if (list1.size() != 0) {
            resultList.addAll(list1);
        }
        return resultList;
    }
}
