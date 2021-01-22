package com.nynu.goule.RoleServiceImplTest;

import com.nynu.goule.GouleApplication;
import com.nynu.goule.service.RoleService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = GouleApplication.class,webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class RoleServiceImplTest {

    @Resource
    private RoleService roleService;

    @Test
    public void testGetAccountRoles(){
        Map<String, Object> map1 = new HashMap<>();
        map1.put("username","wangyu");
        roleService.getAccountRoles(map1);
    }
}
