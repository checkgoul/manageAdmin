package com.nynu.goule.service;

import com.nynu.goule.common.Result;
import com.nynu.goule.pojo.LoginUser;

import java.util.Map;

public interface LoginUserService {

    Result login(Map<String, Object> paramMap) throws Exception;
}
