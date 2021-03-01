package com.nynu.goule.controller;

import com.nynu.goule.common.BaseController;
import com.nynu.goule.service.LoginUserService;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.util.Map;

/**
 * @author  goule
 * @date  2021/1/11 10:51
 */
@RestController
@RequestMapping("/manage/user")
public class UserInfoController extends BaseController {

    @Resource
    private LoginUserService loginUserService;

    @RequestMapping("/addUser")
    public ModelAndView addUser(@RequestBody Map<String, Object> paramMap){
        return feedback(loginUserService.addUser(paramMap));
    }

    @RequestMapping("/toGetAcctId")
    public ModelAndView toGetAcctId(@RequestBody Map<String, Object> map){
        return feedback(loginUserService.toGetAcctId(map));
    }

    @RequestMapping("/userInfo")
    public ModelAndView getAllUserInfo(){
        return feedback(loginUserService.getAllUserInfo());
    }

    @RequestMapping("/checkPhone")
    public ModelAndView checkPhoneNum(@RequestBody Map<String, Object> map) {
        return feedback(loginUserService.checkPhoneNum(map));
    }

    @RequestMapping("/checkPwd")
    public ModelAndView checkPwd(@RequestBody Map<String, Object> map){
        return feedback(loginUserService.checkPwd(map));
    }

    @RequestMapping("/updPwd")
    public ModelAndView updatePwd(@RequestBody Map<String, Object> map){
        return feedback(loginUserService.updatePwd(map));
    }

    @RequestMapping("/delUser")
    public ModelAndView delUser(@RequestBody Map<String,Object> map){
        return feedback(loginUserService.delUser(map));
    }

    @RequestMapping("/lockUser")
    public ModelAndView lockUser(@RequestBody Map<String, Object> map){
        return feedback(loginUserService.lockUser(map));
    }

    @RequestMapping("/breakLock")
    public ModelAndView breakLock(@RequestBody Map<String, Object> map){
        return feedback(loginUserService.breakLock(map));
    }
}
