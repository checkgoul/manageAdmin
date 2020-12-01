package com.nynu.goule.utils;


import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.model.ObjectMetadata;
import com.aliyun.oss.model.PutObjectResult;

import org.springframework.stereotype.Component;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;

@Component
public class OSSUtil {
    //阿里云API的内或外网域名
    private static String ENDPOINT;
    //阿里云API的密钥Access Key ID
    private static String ACCESS_KEY_ID;
    //阿里云API的密钥Access Key Secret
    private static String ACCESS_KEY_SECRET;
    //阿里云API的bucket名称
    private static String BACKET_NAME;
    //阿里云API的文件夹名称
    private static String FOLDER;
    //初始化属性 由于有自己的仓库信息,所以Constants不上传至github
    static{
        ENDPOINT = Constants.END_POINT;
        ACCESS_KEY_ID = Constants.ACCESS_KEY_ID;
        ACCESS_KEY_SECRET = Constants.ACCESS_KEY_SECRET;
        BACKET_NAME = Constants.BUCKET_NAME;
        FOLDER = Constants.FILEDIR;
    }
    //oss连接
    OSS ossClient = new OSSClientBuilder().build(ENDPOINT, ACCESS_KEY_ID, ACCESS_KEY_SECRET);

    /**
     * 获取阿里云OSS客户端对象
     * @return ossClient
     */
    public  OSSClient getOSSClient(){
        return new OSSClient(ENDPOINT,ACCESS_KEY_ID, ACCESS_KEY_SECRET);
    }


    /**
     * 根据key删除OSS服务器上的文件
     * @param key Bucket下的文件的路径名+文件名 如："upload/cake.jpg"
     */
    public void deleteFile(String key){
        ossClient.deleteObject(BACKET_NAME, FOLDER + key);
    }

    /**
     * 上传图片至OSS
     * @param file 上传文件
     * @return String 返回的唯一MD5数字签名
     * */
    public Map<String, Object> uploadObject2OSS(File file) {
        Map<String, Object> map = new HashMap<>();
        String bucketName = BACKET_NAME;
        String folder = FOLDER;
        String resultStr = null;
        try {
            //以输入流的形式上传文件
            InputStream is = new FileInputStream(file);
            //文件名
            String fileName = file.getName();
            //文件大小
            Long fileSize = file.length();
            //创建上传Object的Metadata
            ObjectMetadata metadata = new ObjectMetadata();
            //上传的文件的长度
            metadata.setContentLength(is.available());
            //指定该Object被下载时的网页的缓存行为
            metadata.setCacheControl("no-cache");
            //指定该Object下设置Header
            metadata.setHeader("Pragma", "no-cache");
            //指定该Object被下载时的内容编码格式
            metadata.setContentEncoding("utf-8");
            //文件的MIME，定义文件的类型及网页编码，决定浏览器将以什么形式、什么编码读取文件。如果用户没有指定则根据Key或文件名的扩展名生成，
            //如果没有扩展名则填默认值application/octet-stream
            metadata.setContentType(getContentType(fileName));
            //指定该Object被下载时的名称（指示MINME用户代理如何显示附加的文件，打开或下载，及文件名称）
            metadata.setContentDisposition("filename/filesize=" + fileName + "/" + fileSize + "Byte.");
            //上传文件   (上传文件流的形式)
            PutObjectResult putResult = ossClient.putObject(bucketName, folder + fileName, is, metadata);
            //解析结果
            resultStr = putResult.getETag();
        } catch (Exception e) {
            e.printStackTrace();
        }
        String name = file.getName();
        String url = "http://goule.oss-cn-beijing.aliyuncs.com/pic/" + name;
        //http://goule.oss-cn-beijing.aliyuncs.com/pic/ad3.jpg
        map.put("name",name);
        map.put("url",url);
        return map;
    }

    /**
     * 上传base64的图片到阿里云
     * @param base64 baseUrl
     */
    public String uploadImg(String base64) {
        String fileName=OSSUtil.generateRandomFilename()+".jpeg";
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String name=dateFormat.format(new Date())+"/"+fileName;
        // 创建OSSClient实例。
        OSSClient ossClient = new OSSClient(ENDPOINT, ACCESS_KEY_ID,ACCESS_KEY_SECRET);

        // 上传Byte数组。
        byte[] imageBase64ToByteArray = ImageUtils.imageBase64ToByteArray(base64, 300*1024);

        ossClient.putObject(BACKET_NAME, FOLDER + fileName, new ByteArrayInputStream(imageBase64ToByteArray));

        System.out.println("上传图片至阿里云:"+ BACKET_NAME + "/" + FOLDER + name);

        // 关闭OSSClient。
        ossClient.shutdown();
        return name;
    }

    /**
     * 通过文件名判断并获取OSS服务文件上传时文件的contentType
     * @param fileName 文件名
     * @return 文件的contentType
     */
    public static  String getContentType(String fileName){
        //文件的后缀名
        String fileExtension = fileName.substring(fileName.lastIndexOf("."));
        if(".bmp".equalsIgnoreCase(fileExtension)) {
            return "image/bmp";
        }
        if(".gif".equalsIgnoreCase(fileExtension)) {
            return "image/gif";
        }
        if(".jpeg".equalsIgnoreCase(fileExtension) || ".jpg".equalsIgnoreCase(fileExtension)  || ".png".equalsIgnoreCase(fileExtension) ) {
            return "image/jpeg";
        }
        if(".html".equalsIgnoreCase(fileExtension)) {
            return "text/html";
        }
        if(".txt".equalsIgnoreCase(fileExtension)) {
            return "text/plain";
        }
        if(".vsd".equalsIgnoreCase(fileExtension)) {
            return "application/vnd.visio";
        }
        if(".ppt".equalsIgnoreCase(fileExtension) || "pptx".equalsIgnoreCase(fileExtension)) {
            return "application/vnd.ms-powerpoint";
        }
        if(".doc".equalsIgnoreCase(fileExtension) || "docx".equalsIgnoreCase(fileExtension)) {
            return "application/msword";
        }
        if(".xml".equalsIgnoreCase(fileExtension)) {
            return "text/xml";
        }
        //默认返回类型
        return "image/jpeg";
    }

    /**
     * @description 生成随机文件名，防止上传文件后文件名重复
     * @author hxy
     * @date 2020/8/14 12:08
     * @return
     */
    public static String generateRandomFilename(){
        String RandomFilename = "";
        Random rand = new Random();//生成随机数
        int random = rand.nextInt();

        Calendar calCurrent = Calendar.getInstance();
        int intDay = calCurrent.get(Calendar.DATE);
        int intMonth = calCurrent.get(Calendar.MONTH) + 1;
        int intYear = calCurrent.get(Calendar.YEAR);
        String now = String.valueOf(intYear) + "_" + String.valueOf(intMonth) + "_" +
                String.valueOf(intDay) + "_";

        RandomFilename = now + String.valueOf(random > 0 ? random : ( -1) * random);

        return RandomFilename;
    }



}
