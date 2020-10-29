package com.nynu.goule.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 字符串工具类
 */
public class StringUtil {

    private StringUtil() {
    }

    /**
     * 判断字符串是否非null && 非空字符
     */
    public static boolean isNotEmpty(String param) {
        return param != null && !"".equals(param.trim());
    }

    /**
     * 判断字符串是否为null || 空字符串
     */
    public static boolean isEmpty(String param) {
        return param == null || "".equals(param.trim());
    }

    /**
     * 判断对象是否为null || 空字符串
     */
    public static boolean isEmpty(Object obj) {
        if (obj == null) {
            return true;
        } else {
            return "".equals(obj.toString().trim());
        }
    }

    /**
     * true = is num
     */
    public static boolean isNum(String str) {
        String regex = "^(-?\\d+)(\\.\\d+)?$";
        return matchRegex(str, regex);
    }

    private static boolean matchRegex(String value, String regex) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(value);
        return matcher.matches();
    }

    /**
     * 获取字符串的值
     */
    public static String getStringValue(Object object) {
        if (object != null) {
            return String.valueOf(object);
        }
        return "";
    }

    /**
     * 根据key获取value
     */
    public static String getString(Map inputParam, String key) {
        String resultValue = null;
        if (null != inputParam && null != inputParam.get(key)) {
            resultValue = String.valueOf(inputParam.get(key));
        }
        return resultValue;
    }

    /**
     * 获取两个List的不同元素
     */
    private static List<String> getDiffrent(List<String> list1, List<String> list2) {
        List<String> diff = new ArrayList<>();
        for (String str : list1) {
            if (!list2.contains(str)) {
                diff.add(str);
            }
        }
        return diff;
    }

    /**
     * 获取两个List的相同元素
     */
    private static List<String> getEqualDiffrent(List<String> list1, List<String> list2) {
        List<String> diff = new ArrayList<>();
        for (String str : list1) {
            if (list2.contains(str)) {
                diff.add(str);
            }
        }
        return diff;
    }

    /**
     * 获取数据的不同元素
     */
    private static List<Object> getDiffrent(Object[] str1, Object[] str2) {
        List<Object> list1 = Arrays.asList(str1);
        List<Object> list2 = Arrays.asList(str1);
        List<Object> diff = new ArrayList<>();
        for (Object str : list1) {
            if (list2.contains(str)) {
                diff.add(str);
            }
        }
        return diff;
    }

    /**
     * 获取数据的相同元素
     */
    private static List<Object> getNotEqualDiffrent(Object[] str1, Object[] str2) {
        List<Object> list1 = Arrays.asList(str1);
        List<Object> list2 = Arrays.asList(str1);
        List<Object> diff = new ArrayList<>();
        for (Object str : list1) {
            if (!list2.contains(str)) {
                diff.add(str);
            }
        }
        return diff;
    }
}
