package com.nynu.goule.mapper;

import org.apache.ibatis.annotations.Mapper;

import java.util.Map;

@Mapper
public interface OssInfoMapper {
    Map<String, Object> getOssInfo();
}
