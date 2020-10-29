package com.nynu.goule.service.impl;

import com.nynu.goule.common.Result;
import com.nynu.goule.mapper.LoginUserMapper;
import com.nynu.goule.pojo.LoginUser;
import com.nynu.goule.service.LoginUserService;
import com.nynu.goule.utils.StringUtil;
import com.nynu.goule.utils.ValidateUtil;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
public class LoginUserServiceImpl implements LoginUserService {

    @Resource
    private LoginUserMapper loginUserMapper;

    @Override
    public Result login(Map<String, Object> paramMap,HttpServletRequest request) throws Exception {
        Result result = new Result();
        String username = ValidateUtil.isBlankParam(paramMap, "username", "用户名");
        String password = ValidateUtil.isBlankParam(paramMap, "username", "密码");
        String verification1 = ValidateUtil.isBlankParam(paramMap, "verification", "验证码");
        // 从session中取验证码
        String checkCode1 = (String) request.getSession().getAttribute("verifyCode");
        //统一对字母转小写处理,防止对比失败
        String verification = verification1.toLowerCase();
        String checkCode = checkCode1.toLowerCase();
        if (!(checkCode.equals(verification))) {
            result.setStatus(Result.RTN_CODE.ERROR);
            result.setMsg("验证码错误!");
            return result;
        } else {
            Map<String, Object> rtnMap = loginUserMapper.login(username);
            String realPwd = null;
            if (!CollectionUtils.isEmpty(rtnMap) && rtnMap.size() > 0) {
                realPwd = (String) rtnMap.get("password");
            }
            if (realPwd == null || "".equals(realPwd)) {
                result.setStatus(Result.RTN_CODE.ERROR);
                result.setMsg("没有该用户!");
                return result;
            } else if (!password.equals(realPwd)) {
                result.setStatus(Result.RTN_CODE.ERROR);
                result.setMsg("密码错误!");
                return result;
            }
            result.setStatus(Result.RTN_CODE.SUCCESS);
            result.setData(rtnMap);
            return result;
        }
    }

}
