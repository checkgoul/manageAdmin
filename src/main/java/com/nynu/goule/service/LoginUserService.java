package com.nynu.goule.service;

import com.nynu.goule.common.Result;
import com.nynu.goule.pojo.LoginUser;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

public interface LoginUserService {

    Result login(Map<String, Object> paramMap, HttpServletRequest request) throws Exception;

    Result addUser(Map<String, Object> paramMap);
}
