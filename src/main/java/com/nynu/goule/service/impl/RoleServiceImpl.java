package com.nynu.goule.service.impl;

import com.nynu.goule.common.Result;
import com.nynu.goule.mapper.RoleAuthMapper;
import com.nynu.goule.service.RoleService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service
public class RoleServiceImpl implements RoleService {
    @Resource
    RoleAuthMapper roleAuthMapper;

    @Override
    public Result getAllRoles() {
        Result result = new Result();
        List<Map<String, Object>> roles = roleAuthMapper.getAllRoles();
        result.setData(roles);
        result.setStatus(Result.RTN_CODE.SUCCESS);
        return result;
    }
}
