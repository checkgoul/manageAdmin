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
}
