package com.nynu.goule.mapper;

import com.nynu.goule.pojo.LoginUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

/**
 * @author  goule
 * @date  2021/1/7 10:42
 */

@Mapper
public interface LoginUserMapper {

    Map<String, Object> login(String username);

    int addUser(Map<String, Object> userInfoMap);

    List<Map<String, Object>> queryUsernameCount(String username);

    List<Map<String, Object>> getAllUserInfo();

    int queryPhoneNum(Map<String, Object> map);

    LoginUser getUserIdByUserName(String username);

    LoginUser getOldPwd(Map<String, Object> map);

    int updatePwd(Map<String, Object> map);

    int delUser(Map<String, Object> map);

    int checkLock(String username);

    int lockUser(Map<String, Object> param);

    int breakLock(Map<String, Object> map);

    int resetPwd(Map<String, Object> map);

    List<Map<String, Object>> queryUserInfoById(Map<String, Object> map);

    List<Map<String, Object>> getOrgaInfo();

    String getOrgaIdByOrgaName(String orgaName);

    int queryAcctNum(Map<String, Object> paramMap);

    List<Map<String, Object>> queryUserInfoByAcct(Map<String, Object> paramMap);

    int updateUserInfo(Map<String, Object> userMapUpd);

    List<LoginUser> exportUserInfo();
}
