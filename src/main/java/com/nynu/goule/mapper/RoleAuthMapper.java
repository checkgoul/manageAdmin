package com.nynu.goule.mapper;

import com.nynu.goule.pojo.RoleAuth;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface RoleAuthMapper {
    List<Map<String, Object>> queryAuthId(String userId);
}
