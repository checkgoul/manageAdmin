package com.nynu.goule.utils;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;
/**
 * @author  goule
 * @date  2021/10/29 13:41
 */
public class Encrypt {

    /**
     * 密钥长度必须为16位
     */
    private static final String KEY = "studycode@1024go";
    /**
     * 偏移量
     */
    private static final String OFFSET="og4201@edocyduts";
    /**
     * 编码
     */
    private static final String ENCODING = "UTF-8";
    /**
     * 算法
     */
    private static final String ALGORITHM ="AES";
    /**
     * 默认加密算法，CBC模式
     */
    private static final String CIPHER_ALGORITHM = "AES/CBC/PKCS5Padding";

    public static String AESencrypt(String data) throws Exception{
        Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
        SecretKeySpec keySpec = new SecretKeySpec(KEY.getBytes("ASCII"),ALGORITHM);
        //CBC模式偏移量iv
        IvParameterSpec iv = new IvParameterSpec(OFFSET.getBytes());
        cipher.init(Cipher.ENCRYPT_MODE, keySpec,iv);
        byte[] encrypted = cipher.doFinal(data.getBytes(ENCODING));
        return Base64.getEncoder().encodeToString(encrypted);
    }

    public static String AESdecrypt(String data) throws Exception{
        Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
        SecretKeySpec keySpec = new SecretKeySpec(KEY.getBytes("ASCII"),ALGORITHM);
        IvParameterSpec iv = new IvParameterSpec(OFFSET.getBytes());
        cipher.init(Cipher.DECRYPT_MODE, keySpec,iv);
        byte[] buffer = Base64.getDecoder().decode(data);
        byte[] encrypted = cipher.doFinal(buffer);
        return new String(encrypted,ENCODING);
    }

    public static void main(String[] args) throws Exception {
        System.out.println(Encrypt.AESencrypt("sql@123456"));
        System.out.println(Encrypt.AESdecrypt(AESencrypt("sql@123456")));

    }
}
