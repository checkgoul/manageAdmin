package com.nynu.goule.controller;

import com.nynu.goule.common.BaseController;
import com.nynu.goule.service.CustomerService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.util.Map;

/**
 * @author admin
 */

@RestController
@RequestMapping("/manage/customer")
public class CustomerController extends BaseController {
    @Resource
    private CustomerService customerService;

    @RequestMapping("/getCtmAll")
    public ModelAndView getCtmAll (){
        return feedback(customerService.getCtmAll());
    }

    @RequestMapping("/delCtm")
    public ModelAndView delCtm (@RequestBody Map<String, Object> map) {return feedback(customerService.delCtm(map));}
}
