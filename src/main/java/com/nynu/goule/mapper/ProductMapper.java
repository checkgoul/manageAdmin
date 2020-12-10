package com.nynu.goule.mapper;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.nynu.goule.common.Result;
import com.nynu.goule.pojo.Product;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

@Mapper
public interface ProductMapper {

    List<Map<String, Object>> getAll();

    List<Product> queryProductByIndex(Map<String, Object> map);

    int addNewProduct(Map<String, Object> map);

    int queryCategoryChildNumByParentId(String parentId);

    int updateStatusById(Map<String, Object> param);

    Map<String, Object> getProductInfoById(Map<String, Object> map);
    List<Map<String, Object>> getProductInfoByIdNew(Map<String, Object> map);

    int updateProduct(Map<String, Object> map);

    List<Map<String, Object>> getProductByCategoryId(Map<String, Object> map);
}
