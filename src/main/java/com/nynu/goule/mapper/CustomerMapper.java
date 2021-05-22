package com.nynu.goule.mapper;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface CustomerMapper {
    List<Map<String, Object>> getCtmAll();

    int delCtm(Map<String, Object> map);
}
