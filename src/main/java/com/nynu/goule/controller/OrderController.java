package com.nynu.goule.controller;

import com.nynu.goule.common.BaseController;
import com.nynu.goule.service.OrderService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.util.Map;

@RestController
@RequestMapping("/manage/order")
public class OrderController extends BaseController {

    @Resource
    private OrderService orderService;

    @RequestMapping("/getOrder")
    public ModelAndView getAllOrder(@RequestBody Map<String, Object> map){
        return feedback(orderService.getAllOrder(map));
    }
}
