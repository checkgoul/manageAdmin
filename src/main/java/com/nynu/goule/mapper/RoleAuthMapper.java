package com.nynu.goule.mapper;

import com.nynu.goule.pojo.RoleAuth;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface RoleAuthMapper {
    List<Map<String, Object>> queryAuthId(String userId);

    List<Map<String, Object>> getAllRoles();

    List<Map<String, Object>> getExistRoleList(Map<String, Object> map);

    List<Map<String, Object>> getAssignableRoleList();

    List<String> getOldRoleList(Map<String, Object> map);

    void addAcctRoles(Map<String, Object> insertMap);

    void delAcctRoles(Map<String, Object> insertMap);

    List<String> findRoleNameById(List<String> diffList);

    List<Map<String, Object>> getAllAuth();

    int updAuthState(Map<String, Object> param);

    List<Map<String, Object>> getExistAuthList(Map<String, Object> map);

    List<Map<String, Object>> getAssignableAuthList();

    List<String> getAuthsByRoleId(Map<String, Object> map);

    int addRoleAuths(Map<String, Object> insertMap);

    int getAuthUseful(String param);

    int delRoleAuths(Map<String, Object> insertMap);
}
