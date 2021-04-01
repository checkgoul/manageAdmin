package com.nynu.goule.service.impl;

import com.nynu.goule.common.Result;
import com.nynu.goule.mapper.OrderMapper;
import com.nynu.goule.service.OrderService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service
public class OrderServiceImpl implements OrderService {

    @Resource
    private OrderMapper orderMapper;

    @Override
    public Result getAllOrder(Map<String, Object> map) {
        Result result = new Result();
        List<Map<String, Object>> orderList = orderMapper.getAllOrder(map);
        result.setData(orderList);
        result.setStatus(Result.RTN_CODE.SUCCESS);
        return result;
    }
}
