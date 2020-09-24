package com.nynu.goule.utils;

import org.springframework.util.StringUtils;

import java.security.GeneralSecurityException;
import java.util.HashMap;
import java.util.Map;

public class ValidateUtil {

    public static String isBlankParam(Map<String, Object> map, String key, String name) throws GeneralSecurityException {
        if(StringUtils.isEmpty(map.get(key))){
            if (!StringUtils.isEmpty(name)){
                throw new GeneralSecurityException(name + "不能为空");
            }else{
                throw new GeneralSecurityException(key + "不能为空");
            }
        }
        return String.valueOf(map.get(key));
    }
}
