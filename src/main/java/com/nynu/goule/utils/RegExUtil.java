package com.nynu.goule.utils;

import java.util.regex.Pattern;

public class RegExUtil {

    private RegExUtil() {
    }

    /**
     * 正则表达式: 验证手机号
     */
    public static final String REGEX_MOBILE = "^1(3[0-9]|4[579]|5[0-35-9]|6[0-9]|7[012345678]|8[0-9])\\d{8}$";

    /**
     * 正则表达式: 验证邮箱
     */
    public static final String REGEX_EMAIL = "^([a-z0-9A-Z_]+[-|\\.]?)+[a-z0-9A-Z_]@([a-z0-9A-Z_]+(-[a-z0-9A-Z_]+)?\\.)+[a-zA-Z]{2,}$";

    /**
     * 正则表达式: 验证身份证号
     */
    public static final String REGEX_CREDNUM = "^[1-9]\\d{5}[1-9]\\d{3}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])\\d{4}$";

    //检查身份证是否合法 验证时请先验证长度是否为15为或者18位
    public static final String IDCARD = "\\d{6}(19|20)*[0-99]{2}(0[1-9]{1}|10|11|12)(0[1-9]{1}|1[0-9]|2[0-9]|30|31)(\\w*)";
    // 检查护照是否合法
    public static final String PASSPORT1 = "/^[a-zA-Z]{5,17}$/";
    public static final String PASSPORT2 = "/^[a-zA-Z0-9]{5,17}$/";
    // 港澳通行证验证
    public static final String HKMAKAO = "/^[HMhm]{1}([0-9]{10}|[0-9]{8})$/";
    //  台湾通行证验证
    public static final String TAIWAN1 = " /^[0-9]{8}$/";
    public static final String TAIWAN2 = "/^[0-9]{10}$/";

    /**
     * 校验身份证号码
     * 通过返回true ,否则返回false
     */
    public static boolean isCredNum(String credNum) {
        if (credNum.length() == 18) {
            return Pattern.matches(REGEX_CREDNUM, credNum);
        }
        if (credNum.length() == 15) {
            return true;
        }
        return false;
    }

    /**
     * 校验手机号
     * 通过返回true ,否则返回false
     */
    public static boolean isMobile(String mobile) {
        if (mobile.length() == 11) {
            return Pattern.matches(REGEX_MOBILE, mobile);
        }
        return false;
    }

    /**
     * 校验邮箱
     * 通过返回true ,否则返回false
     */
    public static boolean isEmail(String email) {
        if (email.contains("@") && email.contains(".") && email.length() < 51) {
            return Pattern.matches(REGEX_EMAIL, email);
        }
        return false;
    }

    /**
     * 判断是否为汉字
     */
    public static boolean isName(String name) {
        int n;
        for (int i = 0; i < name.length(); i++) {
            n = (int) name.charAt(i);
            if (!(19968 <= n && n < 40869)) {
                return false;
            }
        }
        return true;
    }
}