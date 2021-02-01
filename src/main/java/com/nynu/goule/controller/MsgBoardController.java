package com.nynu.goule.controller;

import com.nynu.goule.common.BaseController;
import com.nynu.goule.service.MsgBoardService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/manage/msg")
public class MsgBoardController extends BaseController {

    @Resource
    private MsgBoardService msgBoardService;

    @RequestMapping("/getMsgBoard")
    public ModelAndView getMsgBoard() {
        return feedback(msgBoardService.getAllMsg());
    }

    @RequestMapping("/addMsg")
    public ModelAndView addMsg() throws IOException {
        Map<String, Object> param = new HashMap<>();
        return feedback(msgBoardService.addMsg(param));
    }
}
