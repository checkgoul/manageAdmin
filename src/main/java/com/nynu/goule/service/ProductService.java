package com.nynu.goule.service;

import com.nynu.goule.common.Result;
import com.nynu.goule.pojo.Product;

import java.util.List;
import java.util.Map;

public interface ProductService {

    Result getAll(int pageNum, int pageSize);

    Result queryProductByIndex(int pageNum, int pageSize, String productName, String description);
}
