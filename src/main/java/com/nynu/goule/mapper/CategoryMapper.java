package com.nynu.goule.mapper;

import com.nynu.goule.pojo.Category;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

@Mapper
public interface CategoryMapper {

    List<Category> queryAll(String parentId);

    int addCategory(String categoryName, String parentId);

    int queryCategoryNum(String categoryName);

    int updateCategory(String categoryName, int id);

    int delCategory(int id);

    int queryCategoryNumById(int id);

    int queryCategoryNumByParentId(String parentId);

    Category getCategoryNameById(Map<String, Object> categoryMap);
}
