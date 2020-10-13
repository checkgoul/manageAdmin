package com.nynu.goule.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.nynu.goule.common.Result;
import com.nynu.goule.mapper.CategoryMapper;
import com.nynu.goule.mapper.ProductMapper;
import com.nynu.goule.pojo.Product;
import com.nynu.goule.service.ProductService;
import com.nynu.goule.utils.StringUtil;
import com.nynu.goule.utils.ValidateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.security.GeneralSecurityException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
public class ProductServiceImpl implements ProductService {

    @Resource
    private ProductMapper productMapper;
    @Resource
    private CategoryMapper categoryMapper;

    @Override
    public Result getAll(int pageNum, int pageSize) {
        Result result = new Result();
        pageNum = pageNum == 1 ? 1 : pageNum;
        pageSize = pageSize == 0 ? 5 : pageSize;
        PageHelper.startPage(pageNum, pageSize);
        List<Product> products = productMapper.getAll();
        PageInfo<Product> pageInfo = new PageInfo<>(products);
        if(!StringUtils.isEmpty(pageInfo)){
            result.setData(pageInfo);
            result.setStatus("0");
        }else {
            result.setStatus("-9999");
            result.setMsg("查询失败");
        }
        return result;
    }

    @Override
    public Result queryProductByIndex(int pageNum, int pageSize, String productName, String description) {
        Result result = new Result();
        Map<String, Object> param = new HashMap<>();
        param.put("productName",productName);
        param.put("description",description);
        param.put("pageNum",pageNum);
        param.put("pageSize",pageSize);
        PageHelper.startPage(pageNum, pageSize);
        List<Product> products = productMapper.queryProductByIndex(param);
        PageInfo<Product> pageInfo = new PageInfo<>(products);
        if(!StringUtils.isEmpty(pageInfo)){
            result.setData(pageInfo);
            result.setStatus("0");
        }else {
            result.setStatus("-9999");
            result.setMsg("查询失败");
        }
        return result;
    }

    /**
     * @param map
     * @return
     */
    @Override
    public Result addNewProduct(Map<String, Object> map){
        Result result = new Result();
        String pCategoryId = (String) ValidateUtil.isBlankParam(map,"pCategoryId","父分类"); //所在父分类
        String price = (String) map.get("price"); //价格
        String productName = (String) ValidateUtil.isBlankParam(map,"productName","产品名"); //产品名称
        String description = (String) map.get("description"); //产品描述
        String detail = (String) map.get("detail"); //产品详情
        String categoryId = (String) ValidateUtil.isBlankParam(map,"categoryId","分类"); //所在分类
        String imgs = (String) map.get("imgs"); //产品图片 --/暂不可用
        int num = categoryMapper.queryCategoryNumById(Integer.valueOf(categoryId));
        int pNum = categoryMapper.queryCategoryNumById(Integer.valueOf(pCategoryId));
        if(num == 0 || pNum == 0){
            result.setStatus("-9999");
            result.setMsg("分类不存在");
        }else {
            int count = productMapper.addNewProduct(map);
            if (count >= 1){
                result.setStatus("0");
                result.setMsg("添加成功");
            }else{
                result.setStatus("-9999");
                result.setMsg("添加失败");
            }
        }
        return result;
    }

    @Override
    public Result delProduct(int id) {
        Result result = new Result();
        return null;
    }

}
