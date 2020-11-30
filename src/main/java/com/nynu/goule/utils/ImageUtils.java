package com.nynu.goule.utils;

import org.apache.commons.codec.binary.Base64;

public class ImageUtils {

    public static byte[] imageBase64ToByteArray(String base64Str,int size) {
        //判断是否包含前缀，去掉前缀
        if (base64Str.contains(",")) {
            base64Str = base64Str.split(",")[1];
        }
        byte[] decodeFromString = Base64.decodeBase64(base64Str);
        System.out.println("验证base64图片大小:base64:"+ base64Str+" 限制大小："+size);
        if (decodeFromString.length / 1024 > size) {
            throw new RuntimeException("图片过大");
        }
        return decodeFromString;
    }
}