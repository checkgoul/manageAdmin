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
     * @param param
     * @return
     */
    Result getAccountRoles(Map<String, Object> param);

    /**
     * 删除或者新增账号角色
     * @param param
     * @return
     */
    Result updAcctRoles(Map<String, Object> param);

    /**
     * 查询所有的权限
     * @return
     */
    Result getAllAuth();

    /**
     * 修改权限状态
     * @return
     */
    Result updAuthState(Map<String, Object> param);

    /**
     * 获取该角色下的所有权限
     * @param map
     * @return
     */
    Result getRoleAuths(Map<String, Object> map);

    /**
     * 修改某个用户下的角色
     * @param map
     * @return
     */
    Result updRoleAuths(Map<String, Object> map);
}
