package com.nynu.goule.controller;

import com.nynu.goule.common.BaseController;
import com.nynu.goule.common.Result;
import com.nynu.goule.service.ProductService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.security.GeneralSecurityException;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/manage/product")
public class ProductController extends BaseController {

    @Resource
    private ProductService productService;

    @RequestMapping("/all")
    public ModelAndView getAllProduct(@RequestParam(name = "pageNum", defaultValue = "0") Integer pageNum,
                                      @RequestParam(name = "pageSize", defaultValue = "5") Integer pageSize){
        return feedback(productService.getAll(pageNum,pageSize));
    }

    @RequestMapping("/search")
    public ModelAndView searchProduct(@RequestParam(name = "pageNum", defaultValue = "0") Integer pageNum,
                                      @RequestParam(name = "pageSize", defaultValue = "5") Integer pageSize,
                                      @RequestParam(name = "productName", defaultValue = "") String productName,
                                      @RequestParam(name = "description", defaultValue = "") String description){
        return feedback(productService.queryProductByIndex(pageNum,pageSize,productName,description));
    }

    @RequestMapping("/add")
    public ModelAndView addProduct(@RequestParam(name = "productName", defaultValue = "") String productName,
                                   @RequestParam(name = "description", defaultValue = "") String description,
                                   @RequestParam(name = "price", defaultValue = "") String price,
                                   @RequestParam(name = "categoryId", defaultValue = "") String categoryId,
                                   @RequestParam(name = "pCategoryId", defaultValue = "") String pCategoryId,
                                   @RequestParam(name = "imgs", defaultValue = "") String imgs,
                                   @RequestParam(name = "detail", defaultValue = "") String detail) throws GeneralSecurityException {
        Map<String, Object> param = new HashMap<>();
        param.put("pCategoryId",pCategoryId);
        param.put("price",price);
        param.put("productName",productName);
        param.put("description",description);
        param.put("detail",detail);
        param.put("categoryId",categoryId);
        param.put("imgs",imgs);
        return feedback(productService.addNewProduct(param));
    }
}
