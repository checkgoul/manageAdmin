package com.nynu.goule.controller;

import com.nynu.goule.common.Result;
import com.nynu.goule.service.ProductService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;

@RestController
@RequestMapping("/index")
public class ProductController {

    @Resource
    private ProductService productService;

    @RequestMapping("/list")
    public @ResponseBody Result getAll(){
        Result result  = productService.getAll();
        return result;
    }
}
