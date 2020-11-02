package com.nynu.goule.mapper;

import org.apache.ibatis.annotations.Mapper;

import java.util.Map;
@Mapper
public interface OperateLogMapper {
    void addOperateLog(Map<String, Object> map);
}
