package com.nynu.goule.mapper;

import com.nynu.goule.pojo.LoginUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.Map;

@Mapper
public interface LoginUserMapper {

    Map<String, Object> login(String username);
}
