package com.nynu.goule.service;

import com.nynu.goule.common.Result;
import com.nynu.goule.pojo.LoginUser;

public interface LoginUserService {

    Result login(String username, String password) throws Exception;
}
