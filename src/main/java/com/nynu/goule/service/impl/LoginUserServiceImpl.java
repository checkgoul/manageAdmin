package com.nynu.goule.service.impl;

import com.nynu.goule.common.Result;
import com.nynu.goule.mapper.LoginUserMapper;
import com.nynu.goule.pojo.LoginUser;
import com.nynu.goule.service.LoginUserService;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.Map;
import java.util.UUID;

@Service
public class LoginUserServiceImpl implements LoginUserService {

    @Resource
    private LoginUserMapper loginUserMapper;

    @Override
    public Result login(String username, String password) throws Exception {
        Result result = new Result();
        Map<String, Object> rtnMap = loginUserMapper.login(username);
        String realPwd = null;
        if (!CollectionUtils.isEmpty(rtnMap) && rtnMap.size() > 0) {
            realPwd = (String) rtnMap.get("password");
        }
        if (realPwd == null || "".equals(realPwd)) {
            result.setStatus("1");
            result.setMsg("没有该用户!");
            return result;
        } else if (!password.equals(realPwd)) {
            result.setStatus("1");
            result.setMsg("密码错误!");
            return result;
        }
        result.setStatus("0");
        result.setData(rtnMap);
        return result;
    }
}
