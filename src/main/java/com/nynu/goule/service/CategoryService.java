package com.nynu.goule.service;

import com.nynu.goule.common.Result;

import java.util.Map;

public interface CategoryService {
    Result queryAll(String parentId);

    Result addCategory(Map<String, Object> categoryMap);

    Result updateCategory(Map<String, Object> categoryMap);

    Result delCategory(Map<String, Object> categoryMap);

    Result categoryInfo(String id);
}
