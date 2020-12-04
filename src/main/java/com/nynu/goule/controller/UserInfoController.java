package com.nynu.goule.controller;

import com.nynu.goule.common.BaseController;
import com.nynu.goule.service.LoginUserService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.util.Map;

@RestController
@RequestMapping("/manage/user")
public class UserInfoController extends BaseController {

    @Resource
    private LoginUserService loginUserService;

    @RequestMapping("/addUser")
    public ModelAndView addUser(@RequestBody Map<String, Object> paramMap){
        return feedback(loginUserService.addUser(paramMap));
    }
}
