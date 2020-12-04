package com.nynu.goule.controller;

import com.nynu.goule.common.BaseController;
import com.nynu.goule.service.ProductService;
import com.nynu.goule.utils.OSSUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.*;

@RestController
@RequestMapping("/manage/product")
public class ProductController extends BaseController {

    @Resource
    private ProductService productService;

    @RequestMapping("/all")
    public ModelAndView getAllProduct(@RequestParam(name = "pageNum", defaultValue = "0") Integer pageNum,
                                      @RequestParam(name = "pageSize", defaultValue = "5") Integer pageSize) {
        return feedback(productService.getAll(pageNum, pageSize));
    }

    @RequestMapping("/search")
    public ModelAndView searchProduct(@RequestParam(name = "pageNum", defaultValue = "0") Integer pageNum,
                                      @RequestParam(name = "pageSize", defaultValue = "5") Integer pageSize,
                                      @RequestParam(name = "productName", defaultValue = "") String productName,
                                      @RequestParam(name = "description", defaultValue = "") String description) {
        return feedback(productService.queryProductByIndex(pageNum, pageSize, productName, description));
    }

    @RequestMapping("/addOrUpdate")
    public ModelAndView addOrUpdateProduct(@RequestBody Map<String, Object> map) throws GeneralSecurityException {
        return feedback(productService.addAndUpdateProduct(map));
    }

    @RequestMapping("/del")
    public ModelAndView delProduct(int id) {
        return feedback(productService.delProduct(id));
    }

    @RequestMapping("/updateStatus")
    public ModelAndView updateStatus(@RequestBody Map<String, Object> map){
        return feedback(productService.updateStatus(map));
    }

    @RequestMapping("/uploadImages")
    public ModelAndView uploadImages(MultipartFile file) throws IOException {
        //MultipartFile file = (MultipartFile) map.get("image");
        return feedback(productService.uploadImages(file));
    }

    @RequestMapping("/delImg")
    public ModelAndView delImages(@RequestBody Map<String, Object> map){
        return feedback(productService.deleteImages(map));
    }

}
