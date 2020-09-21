package com.nynu.goule.service.impl;

import com.nynu.goule.common.Result;
import com.nynu.goule.mapper.ProductMapper;
import com.nynu.goule.pojo.Product;
import com.nynu.goule.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.List;


@Service
public class ProductServiceImpl implements ProductService {

    @Resource
    private ProductMapper productMapper;

    @Override
    public Result getAll() {
        List<Product> product = productMapper.getAll();
        Result result = new Result();
        if(!StringUtils.isEmpty(product)){
            result.setData(product);
            result.setStatus("0");
        }else {
            result.setStatus("-9999");
            result.setMsg("查询失败");
        }
        return result;
    }

}
