package com.nynu.goule.controller;

import com.nynu.goule.common.BaseController;
import com.nynu.goule.service.OperateLogService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;

@RequestMapping("/ws/log")
@RestController
public class OperateLogController extends BaseController {
    @Resource
    private OperateLogService operateLogService;

    @RequestMapping("/info")
    public ModelAndView getOperateLog(){
        return feedback(operateLogService.getOperateLog());
    }
}