package com.nynu.goule.allTest;

import com.nynu.goule.GouleApplication;
import com.nynu.goule.pojo.LoginUser;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = GouleApplication.class,webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class test {

    @Resource
    private RedisTemplate<String, LoginUser> redisTemplate;
    private LoginUser d;
    @Before
    public void before(){
        d=new LoginUser();
        d.setId(123);
        d.setAccountName("456");
        d.setRoleId("123");
        d.setMail("dawda");
        d.setPassword("title");
    }
    @Test
    public void testSet(){
        this.redisTemplate.opsForValue().set("days",d);
        System.out.println((redisTemplate.opsForValue().get("days")));
    }
}
