package com.nynu.goule.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.nynu.goule.common.Result;
import com.nynu.goule.mapper.ProductMapper;
import com.nynu.goule.pojo.Product;
import com.nynu.goule.service.ProductService;
import com.nynu.goule.utils.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
public class ProductServiceImpl implements ProductService {

    @Resource
    private ProductMapper productMapper;

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

}
