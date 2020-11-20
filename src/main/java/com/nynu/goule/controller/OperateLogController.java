package com.nynu.goule.controller;

import com.nynu.goule.common.BaseController;
import com.nynu.goule.service.OperateLogService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;

@RequestMapping("/ws/log")
@RestController
public class OperateLogController extends BaseController {
    @Resource
    private OperateLogService operateLogService;

    @RequestMapping("/info")
    public ModelAndView getOperateLog(@RequestParam(name = "pageNum", defaultValue = "0") Integer pageNum,
                                      @RequestParam(name = "pageSize", defaultValue = "8") Integer pageSize){
        return feedback(operateLogService.getOperateLog(pageNum,pageSize));
    }

    @RequestMapping("/searchByAny")
    public ModelAndView searchByAny(@RequestParam(name = "pageNum", defaultValue = "0") Integer pageNum,
                                    @RequestParam(name = "pageSize", defaultValue = "8") Integer pageSize,
                                    @RequestParam(name = "searchName", defaultValue = "") String searchName,
                                    @RequestParam(name = "opType", defaultValue = "") String opType,
                                    @RequestParam(name = "opMenu", defaultValue = "") String opMenu){
        return feedback(operateLogService.searchInfoByAny(pageNum, pageSize, searchName, opType, opMenu));
    }
}