package com.nynu.goule.utils;

import com.nynu.goule.exception.GeneralException;
import org.springframework.util.StringUtils;

import java.security.GeneralSecurityException;
import java.util.HashMap;
import java.util.Map;

/**
 * 检测map集合内某元素是否为空
 */
public class ValidateUtil {

    public static String isBlankParam(Map<String, Object> map, String key, String name) {
        if (StringUtils.isEmpty(map.get(key))) {
            if (!StringUtils.isEmpty(name)) {
                String message = name + "不能为空";
                return message;
            } else {
                String message = key + "不能为空";
                return message;
            }
        }
        return String.valueOf(map.get(key));
    }
}
