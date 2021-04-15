package com.nynu.goule.thread;

import com.nynu.goule.common.Result;
import com.nynu.goule.pojo.LoginUser;
import com.nynu.goule.service.LoginUserService;
import com.nynu.goule.utils.ExcelUtil;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;

@Configuration      //1.主要用于标记配置类，兼备Component的效果。
public class UserInfoExport implements Runnable {

    private Map<String, Object> map;
    private LoginUserService loginUserService;

    public UserInfoExport(Map<String, Object> map, LoginUserService loginUserService) {
        this.map = map;
        this.loginUserService = loginUserService;
    }

    @Override
    public void run() {
        Map<String, List<String>> memberMap = getMember();
        String[] strArray = excelTitle();
        ExcelUtil.createExcel(memberMap, strArray, "userInfo");
    }

    /**
     * 创建excel title
     */
    protected static String[] excelTitle() {
        String[] strArray = { "username", "AccountName", "TelPhone","Id","Mail","Password","PrsnIdNum","AddTime"};
        return strArray;
    }

    protected Map<String, List<String>> getMember() {
        //查询导出所有的管理员信息
        Result result = loginUserService.exportUserInfo();
        List<LoginUser> userList = new ArrayList<>();
        Map<String, List<String>> map = new HashMap<>();
        userList = (List<LoginUser>) result.getData();
        for(int i = 0; i < userList.size(); i++){
            ArrayList<String> members = new ArrayList<>();
            members.add(userList.get(i).getUsername() + "");
            members.add(userList.get(i).getAccountName() + "");
            members.add(userList.get(i).getTelPhone() + "");
            members.add(userList.get(i).getId() + "");
            members.add(userList.get(i).getMail() + "");
            members.add(userList.get(i).getPassword() + "");
            members.add(userList.get(i).getPrsnIdNum() + "");
            members.add(userList.get(i).getAddTime() + "");
            map.put(userList.get(i).getUsername() + "", members);
        }
        return map;
    }
}
