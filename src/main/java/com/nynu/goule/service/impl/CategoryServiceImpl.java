package com.nynu.goule.service.impl;

import com.nynu.goule.common.Result;
import com.nynu.goule.mapper.CategoryMapper;
import com.nynu.goule.mapper.LoginUserMapper;
import com.nynu.goule.mapper.ProductMapper;
import com.nynu.goule.mapper.RoleAuthMapper;
import com.nynu.goule.pojo.Category;
import com.nynu.goule.pojo.LoginUser;
import com.nynu.goule.pojo.OperateLog;
import com.nynu.goule.pojo.RoleAuth;
import com.nynu.goule.service.CategoryService;
import com.nynu.goule.service.OperateLogService;
import com.nynu.goule.utils.CommonConstants;
import com.nynu.goule.utils.JsonUtil;
import com.nynu.goule.utils.Security;
import com.nynu.goule.utils.StringUtil;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Resource
    private CategoryMapper categoryMapper;
    @Resource
    private ProductMapper productMapper;
    @Resource
    private OperateLogService operateLogService;

    Security security = new Security();
    /**
     * 查询所有的分类,前端分页(分类数量较小
     * @param parentId
     * @return
     */
    @Override
    public Result queryAll(String parentId) {
        List<Category> categoryList = categoryMapper.queryAll(parentId);
        Result result = new Result();
        if (!StringUtils.isEmpty(categoryList)) {
            result.setData(categoryList);
            result.setStatus(Result.RTN_CODE.SUCCESS);
        } else {
            result.setStatus(Result.RTN_CODE.ERROR);
            result.setMsg("查询商品分类失败");
        }
        return result;
    }

    /**
     * 添加分类
     * @param categoryMap
     * @return
     */
    @Override
    public Result addCategory(Map<String, Object> categoryMap) {
        Result result = new Result();
        Map<String ,Object> operateMap = new HashMap<>();
        String categoryName = (String) categoryMap.get("categoryName");
        String parentId = (String) categoryMap.get("parentId");
        String mainAcctId = (String) categoryMap.get("username");
        String authType = CommonConstants.AUTHID.INSERT + "," + CommonConstants.AUTHID.CATEGORY_INSERT;
        boolean isAuth = security.checkAccountAuth(mainAcctId, authType);
        if(isAuth) {
            if (!categoryName.isEmpty() && !"".equals(categoryName) && (categoryName.length() < 20)) {
                int num = categoryMapper.queryCategoryNum(categoryName);
                if (num >= 1) {
                    result.setStatus(Result.RTN_CODE.ERROR);
                    result.setMsg("该分类已存在");
                    return result;
                } else {
                    int count = categoryMapper.addCategory(categoryName, parentId);
                    if (count == 1) {
                        //记录操作日志
                        result.setMsg("添加成功");
                        result.setStatus(Result.RTN_CODE.SUCCESS);
                        if ("0".equals(parentId)) {
                            String msg = "新增: 一级分类==>“" + categoryName + "”";
                            operateMap.put("logCntt", msg);
                        } else {
                            String msg = "新增: 二级分类==>“" + categoryName + "”";
                            operateMap.put("logCntt", msg);
                        }
                        Map<String, Object> categoryNameMap = new HashMap<>();
                        categoryNameMap.put("categoryName", categoryName);
                        String afterMsg = JsonUtil.convertObject2Json(categoryNameMap);
                        operateMap.put("acctId", mainAcctId);
                        operateMap.put("opType", OperateLog.OP_TYPE.ADD);
                        operateMap.put("opMenu", OperateLog.OP_PATH.CATEGORY_MANAGEMENT);
                        operateMap.put("afterCntt", afterMsg);
                        operateLogService.addOperateLog(operateMap);
                    } else {
                        result.setMsg("添加失败");
                        result.setStatus(Result.RTN_CODE.ERROR);
                        return result;
                    }
                }
            } else {
                result.setStatus(Result.RTN_CODE.ERROR);
                result.setMsg("分类名称不符合规范");
                return result;
            }
        }else{
            result.setMsg("您没有当前操作权限");
            result.setStatus(Result.RTN_CODE.ERROR);
        }
        return result;
    }

    /**
     * 修改分类名称
     * @param categoryMap
     * @return
     */
    @Override
    public Result updateCategory(Map<String, Object> categoryMap) {
        Result result = new Result();
        Map<String ,Object> operateMap = new HashMap<>();
        Map<String ,Object> map = new HashMap<>();
        int id = (int) categoryMap.get("id");
        String categoryName = (String) categoryMap.get("categoryName");
        String mainAcctId = (String) categoryMap.get("username");
        map.put("id",id);
        String authType = CommonConstants.AUTHID.UPDATE + "," + CommonConstants.AUTHID.CATEGORY_UPDATE;
        boolean isAuth = security.checkAccountAuth(mainAcctId,authType);
        if(isAuth) {
            Category category = categoryMapper.getCategoryNameById(map);
            //删除前的名称
            String beforeCategoryName = category.getCategoryName();
            int num = categoryMapper.queryCategoryNum(categoryName);
            if (num >= 1) {
                result.setStatus(Result.RTN_CODE.ERROR);
                result.setMsg("该分类已存在");
                return result;
            } else {
                int count = categoryMapper.updateCategory(categoryName, id);
                if (count >= 1) {
                    //记录操作日志
                    Map<String, Object> beforeMap = new HashMap<>();
                    Map<String, Object> afterMap = new HashMap<>();
                    beforeMap.put("categoryName", beforeCategoryName);
                    afterMap.put("categoryName", categoryName);
                    String beforeMsg = JsonUtil.convertObject2Json(beforeMap);
                    String afterMsg = JsonUtil.convertObject2Json(afterMap);
                    result.setMsg("修改成功");
                    result.setStatus(Result.RTN_CODE.SUCCESS);
                    String msg = "修改原分类名: “" + beforeCategoryName + "”";
                    operateMap.put("acctId", mainAcctId);
                    operateMap.put("opType", OperateLog.OP_TYPE.MODIFY);
                    operateMap.put("logCntt", msg);
                    operateMap.put("opMenu", OperateLog.OP_PATH.CATEGORY_MANAGEMENT);
                    operateMap.put("beforeCntt", beforeMsg);
                    operateMap.put("afterCntt", afterMsg);
                    operateLogService.addOperateLog(operateMap);
                } else {
                    result.setMsg("修改失败");
                    result.setStatus(Result.RTN_CODE.ERROR);
                    return result;
                }
            }
        }else{
            result.setMsg("您没有当前操作权限");
            result.setStatus(Result.RTN_CODE.ERROR);
        }
        return result;
    }

    /**
     * 删除分类
     * @param categoryMap
     * @return
     */
    @Override
    public Result delCategory(Map<String, Object> categoryMap) {
        Result result = new Result();
        Map<String ,Object> operateMap = new HashMap<>();
        Map<String ,Object> map = new HashMap<>();
        int id = (int) categoryMap.get("id");
        String mainAcctId = (String) categoryMap.get("username");
        String authType = CommonConstants.AUTHID.DELETE + "," + CommonConstants.AUTHID.CATEGORY_DELETE;
        boolean isAuth = security.checkAccountAuth(mainAcctId,authType);
        if(isAuth) {
            map.put("id", id);
            //入库操作需要
            Category category = categoryMapper.getCategoryNameById(map);
            Category category1 = categoryMapper.getCategoryParentIdById(map);
            String parentId1 = category1.getParentId();
            //删除前的名称
            String beforeCategoryName = category.getCategoryName();
            int num = categoryMapper.queryCategoryNumById(id);
            if (num <= 0) {
                result.setMsg("删除失败");
                result.setStatus(Result.RTN_CODE.ERROR);
                return result;
            } else {
                String parentId = Integer.toString(id);
                // 父集下存在子集
                int secondCategoryNum = categoryMapper.queryCategoryNumByParentId(parentId);
                // 子集下存在商品
                int categoryChildNum = productMapper.queryCategoryChildNumByParentId(parentId);
                if (secondCategoryNum >= 1 || categoryChildNum >= 1) {
                    result.setMsg("存在子分类,不可删除");
                    result.setStatus(Result.RTN_CODE.ERROR);
                    return result;
                } else {
                    int count = categoryMapper.delCategory(id);
                    if (count >= 1) {
                        result.setMsg("删除成功");
                        result.setStatus(Result.RTN_CODE.SUCCESS);
                        if ("0".equals(parentId1)) {
                            String msg = "删除: 一级分类==>“" + beforeCategoryName + "”";
                            operateMap.put("logCntt", msg);
                        } else {
                            String msg = "删除: 二级分类==>“" + beforeCategoryName + "”";
                            operateMap.put("logCntt", msg);
                        }
                        //记录操作日志
                        Map<String, Object> beforeMap = new HashMap<>();
                        beforeMap.put("categoryName", beforeCategoryName);
                        String beforeMsg = JsonUtil.convertObject2Json(beforeMap);
                        operateMap.put("acctId", mainAcctId);
                        operateMap.put("opType", OperateLog.OP_TYPE.DELETE);
                        operateMap.put("opMenu", OperateLog.OP_PATH.CATEGORY_MANAGEMENT);
                        operateMap.put("beforeCntt", beforeMsg);
                    } else {
                        result.setMsg("删除失败");
                        result.setStatus(Result.RTN_CODE.ERROR);
                        return result;
                    }
                }
            }
            if (operateMap.size() > 0) {
                operateLogService.addOperateLog(operateMap);
            }
        }else{
            result.setMsg("您没有当前操作权限");
            result.setStatus(Result.RTN_CODE.ERROR);
        }
        return result;

    }

    /**
     * 根据分类id查询分类名称
     * @param id
     * @return
     */
    @Override
    public Result categoryInfo(String id) {
        Result result = new Result();
        Map<String, Object> categoryMap = new HashMap<>();
        categoryMap.put("id", Integer.valueOf(id));
        Category category = categoryMapper.getCategoryNameById(categoryMap);
        result.setData(category);
        result.setStatus(Result.RTN_CODE.SUCCESS);
        return result;
    }
}
