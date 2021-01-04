package com.nynu.goule.controller;

import com.nynu.goule.common.BaseController;
import com.nynu.goule.service.RoleService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;

@RestController
@RequestMapping("/manage/role")
public class RoleController extends BaseController {
    @Resource
    private RoleService roleService;

    @RequestMapping("/getAllRole")
    public ModelAndView getAllRoles(){
        return feedback(roleService.getAllRoles());
    }
}
