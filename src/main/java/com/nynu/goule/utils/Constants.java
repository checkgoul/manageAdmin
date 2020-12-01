package com.nynu.goule.utils;

import com.nynu.goule.mapper.OssInfoMapper;

import javax.annotation.Resource;
import java.util.Map;

public class Constants {
    @Resource
    private OssInfoMapper ossInfoMapper;
    Map<String, Object> map = ossInfoMapper.getOssInfo();

    public final  String END_POINT = (String) map.get("END_POINT"); //阿里云oss信息
    public final  String ACCESS_KEY_ID  = (String) map.get("ACCESS_KEY_ID");//访问阿里云API的验证
    public final  String ACCESS_KEY_SECRET =(String) map.get("ACCESS_KEY_SECRET");//访问阿里云API的验证
    public final  String BUCKET_NAME = (String) map.get("BUCKET_NAME"); //仓库信息
    public final  String FILEDIR = (String) map.get("FILEDIR");//object路径
}
