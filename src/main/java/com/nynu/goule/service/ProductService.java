package com.nynu.goule.service;

import com.nynu.goule.common.Result;
import com.nynu.goule.pojo.Product;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.List;
import java.util.Map;

public interface ProductService {

    Result getAll(int pageNum, int pageSize);

    Result queryProductByIndex(int pageNum, int pageSize, String productName, String description);

    Result addAndUpdateProduct(Map<String, Object> map) throws GeneralSecurityException;

    Result delProduct(int id);

    Result updateStatus(Map<String, Object> map);

    Result uploadImages(MultipartFile file) throws IOException;
}
