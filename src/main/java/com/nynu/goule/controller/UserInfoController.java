package com.nynu.goule.controller;

import com.nynu.goule.common.BaseController;
import com.nynu.goule.service.LoginUserService;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ScheduledThreadPoolExecutor;

/**
 * @author  goule
 * @date  2021/1/11 10:51
 */
@RestController
@RequestMapping("/manage/user")
public class UserInfoController extends BaseController {

    @Resource
    private LoginUserService loginUserService;

    //使用RabbitTemplate,这提供了接收/发送等等方法
    @Autowired
    RabbitTemplate rabbitTemplate;

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

    @RequestMapping("/resetPwd")
    public ModelAndView resetPwd(@RequestBody Map<String, Object> map){
        return feedback(loginUserService.resetPwd(map));
    }

    @RequestMapping("/getOrgaInfo")
    public ModelAndView getOrgaInfo(){
        return feedback(loginUserService.getOrgaInfo());
    }

    @GetMapping("/sendDirectMessage")
    public String sendDirectMessage() {
        String messageId = String.valueOf(UUID.randomUUID());
        String messageData = "这一次是中文";
        String createTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("data","测试用例");
        paramMap.put("mag","获取成功");
        Map<String,Object> map=new HashMap<>();
        map.put("messageId",messageId);
        map.put("messageData",messageData);
        map.put("createTime",createTime);
        map.put("value",paramMap);
        //将消息携带绑定键值：TestDirectRouting 发送到交换机TestDirectExchange
        rabbitTemplate.convertAndSend("TestDirectExchange", "TestDirectRouting", map);
        return "ok";
    }
}
