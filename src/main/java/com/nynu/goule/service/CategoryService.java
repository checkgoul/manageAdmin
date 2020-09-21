package com.nynu.goule.service;

import com.nynu.goule.common.Result;

public interface CategoryService {
    Result queryAll(String parentId);

    Result addCategory(String categoryName, String parentId);

    Result updateCategory(String categoryName, int id);

    Result delCategory(int id);
}
