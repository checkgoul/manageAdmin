package com.nynu.goule.mapper;

import com.nynu.goule.pojo.LoginUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

@Mapper
public interface LoginUserMapper {

    Map<String, Object> login(String username);

    int addUser(Map<String, Object> userInfoMap);

    List<Map<String, Object>> queryUsernameCount(String username);

    List<Map<String, Object>> getAllUserInfo();

    int queryPhoneNum(Map<String, Object> map);

    LoginUser getUserIdByUserName(String username);
}
