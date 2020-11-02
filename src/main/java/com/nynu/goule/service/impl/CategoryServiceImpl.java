package com.nynu.goule.service.impl;

import com.nynu.goule.common.Result;
import com.nynu.goule.mapper.CategoryMapper;
import com.nynu.goule.mapper.ProductMapper;
import com.nynu.goule.pojo.Category;
import com.nynu.goule.pojo.OperateLog;
import com.nynu.goule.service.CategoryService;
import com.nynu.goule.service.OperateLogService;
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

    @Override
    public Result queryAll(String parentId) {
        List<Category> categoryList = categoryMapper.queryAll(parentId);
        Result result = new Result();
        if (!StringUtils.isEmpty(categoryList)) {
            result.setData(categoryList);
            result.setStatus("0");
        } else {
            result.setStatus("-9999");
            result.setMsg("查询商品分类失败");
        }
        return result;
    }

    @Override
    public Result addCategory(String categoryName, String parentId) {
        Result result = new Result();
        if (!categoryName.isEmpty() && !"".equals(categoryName) && (categoryName.length() < 20)) {
            int num = categoryMapper.queryCategoryNum(categoryName);
            if (num >= 1) {
                result.setStatus("-9999");
                result.setMsg("该分类已存在");
            } else {
                int count = categoryMapper.addCategory(categoryName, parentId);
                if (count == 1) {
                    result.setMsg("添加成功");
                    result.setStatus("0");
                } else {
                    result.setMsg("添加失败");
                    result.setStatus("-9999");
                }
            }
        } else {
            result.setStatus("-9999");
            result.setMsg("分类名称不符合规范");
        }
        return result;
    }

    @Override
    public Result updateCategory(String categoryName, int id) {
        Result result = new Result();
        int num = categoryMapper.queryCategoryNum(categoryName);
        if (num >= 1) {
            result.setStatus("-9999");
            result.setMsg("该分类已存在");
        } else {
            int count = categoryMapper.updateCategory(categoryName, id);
            if (count >= 1) {
                result.setMsg("修改成功");
                result.setStatus("0");
            } else {
                result.setMsg("修改失败");
                result.setStatus("-9999");
            }
        }
        return result;
    }

    @Override
    public Result delCategory(int id) {
        Result result = new Result();
        Map<String ,Object> operateMap = new HashMap<>();
        int num = categoryMapper.queryCategoryNumById(id);
        if (num <= 0) {
            result.setMsg("删除失败");
            result.setStatus("-9999");
            return result;
        } else {
            String parentId = Integer.toString(id);
            int secondCategoryNum = categoryMapper.queryCategoryNumByParentId(parentId); // 父集下存在子集
            int categoryChildNum = productMapper.queryCategoryChildNumByParentId(parentId); // 子集下存在商品
            if (secondCategoryNum >= 1 || categoryChildNum >= 1) {
                result.setMsg("存在子分类,不可删除");
                result.setStatus("-9999");
                return result;
            } else {
                int count = categoryMapper.delCategory(id);
                if (count >= 1) {
                    result.setMsg("删除成功");
                    result.setStatus("0");
                    String msg = "删除id="+id+"的商品";
                    operateMap.put("acctId","admin");
                    operateMap.put("opType", OperateLog.OP_TYPE.DELETE);
                    operateMap.put("logCntt",msg);
                    operateMap.put("beforeCntt",id);
                } else {
                    result.setMsg("删除失败");
                    result.setStatus("-9999");
                    return result;
                }
            }
        }
        if(operateMap.size() > 0){
            operateLogService.addOperateLog(operateMap);
        }
        return result;
    }

    @Override
    public Result categoryInfo(String id) {
        Result result = new Result();
        Map<String, Object> categoryMap = new HashMap<>();
        categoryMap.put("id", Integer.valueOf(id));
        Category category = categoryMapper.getCategoryNameById(categoryMap);
        result.setData(category);
        result.setStatus("0");
        return result;
    }
}
