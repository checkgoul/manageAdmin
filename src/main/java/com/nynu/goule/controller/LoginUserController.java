package com.nynu.goule.controller;

import com.nynu.goule.common.BaseController;
import com.nynu.goule.common.Result;
import com.nynu.goule.service.LoginUserService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.util.Map;

@RestController
@RequestMapping("/portal")
public class LoginUserController extends BaseController {

    @Resource
    private LoginUserService loginUserService;

    @RequestMapping("/login")
    public ModelAndView UserLogin(@RequestBody Map<String, Object> paramMap) throws Exception {
        return feedback(loginUserService.login((String)paramMap.get("username"),(String)paramMap.get("password")));
    }
}
