package com.nynu.goule.service.impl;

import com.nynu.goule.common.Result;
import com.nynu.goule.mapper.RoleAuthMapper;
import com.nynu.goule.service.RoleService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author  goule
 * @date  2021/1/22 9:35
 */

@Service
public class RoleServiceImpl implements RoleService {
    @Resource
    RoleAuthMapper roleAuthMapper;

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
}
