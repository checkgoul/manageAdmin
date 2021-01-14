package com.nynu.goule.LoginUserServiceImplTest;


import com.nynu.goule.GouleApplication;
import com.nynu.goule.service.LoginUserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = GouleApplication.class,webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class LoginUserServiceImplTest {

    @Resource
    private LoginUserService loginUserService;

    @Test
    public void toGetAcctIdTest() {
        Map<String, Object> map = new HashMap<>();
        map.put("accountName","测试");
        loginUserService.toGetAcctId(map);
    }

    @Test
    @Transactional
    public void checkPwd(){
        Map<String, Object> map = new HashMap<>();
        map.put("username","admin");
        map.put("oldPwd1","");
        loginUserService.checkPwd(map);
    }
}
