package com.nynu.goule.service.impl;

import com.nynu.goule.common.Result;
import com.nynu.goule.mapper.CustomerMapper;
import com.nynu.goule.service.CustomerService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class CustomerServiceImpl implements CustomerService {
    @Resource
    private CustomerMapper customerMapper;

    @Override
    public Result getCtmAll() {
        Result result = new Result();
        List<Map<String, Object>> customerList = customerMapper.getCtmAll();
        result.setData(customerList);
        result.setStatus(Result.RTN_CODE.SUCCESS);
        return result;
    }

    @Override
    public Result delCtm(Map<String, Object> map) {
        Result result = new Result();
        List<Map<String, Object>> idList = (List<Map<String, Object>>) map.get("ids");
        List<Map<String, Object>> ctmNameList = (List<Map<String, Object>>) map.get("ctmName");
        String username = (String) map.get("username");
        if(idList.size() > 0){
            int num = customerMapper.delCtm(map);
            if(num > 0){
                result.setMsg("注销用户成功");
                result.setStatus(Result.RTN_CODE.SUCCESS);
            }else{
                result.setMsg("注销用户失败");
                result.setStatus(Result.RTN_CODE.SUCCESS);
            }
        }
        return result;
    }
}
