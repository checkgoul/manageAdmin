package com.nynu.goule.controller;

import com.nynu.goule.common.BaseController;
import com.nynu.goule.common.Result;
import com.nynu.goule.file.ThreadPoolHolder;
import com.nynu.goule.service.LoginUserService;
import com.nynu.goule.thread.UserInfoExport;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Map;

/**
 * @author  goule
 * @date  2021/4/7 16:44
 */

@RestController
@RequestMapping("/manage/export")
public class ExportTaskController extends BaseController {
    @Resource
    private LoginUserService loginUserService;

    @RequestMapping("/test")
    public Result testExport(Map<String, Object> map){
        Result result = new Result();
        result.setMsg("导出中，请稍后查看");
        result.setStatus(Result.RTN_CODE.SUCCESS);
        UserInfoExport t = new UserInfoExport(map, loginUserService);
        Thread thread = new Thread(t);
        thread.start();
        return result;
    }
}
