package com.nynu.goule.service;

import com.nynu.goule.common.Result;
import com.nynu.goule.pojo.LoginUser;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @author  goule
 * @date  2021/1/7 10:08
 */

public interface LoginUserService {

    /**
     * 登录接口
     * @param paramMap
     * @param request
     * @return
     * @throws Exception
     */
    Result login(Map<String, Object> paramMap, HttpServletRequest request) throws Exception;

    Result addUser(Map<String, Object> paramMap);

    Result toGetAcctId(Map<String, Object> map);

    Result getAllUserInfo();

    Result checkPhoneNum(Map<String, Object> map);

    Result checkPwd(Map<String, Object> map);

    Result updatePwd(Map<String, Object> map);

    /**
     * 删除用户(批量或单个)
     * @param map
     * @return
     */
    Result delUser(Map<String, Object> map);

    Result lockUser(Map<String, Object> map);

    Result breakLock(Map<String, Object> map);

    Result resetPwd(Map<String, Object> map);
}
