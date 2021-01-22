package com.nynu.goule.service;

import com.nynu.goule.common.Result;

import java.util.Map;

/**
 * @author  goule
 * @date  2021/1/22 9:35
 */

public interface RoleService {

    Result getAllRoles();

    /**
     * 根据账号去查询当前账号的可赋角色和已有角色
     * @return
     */
    Result getAccountRoles(Map<String, Object> param);
}
