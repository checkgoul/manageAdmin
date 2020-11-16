package com.nynu.goule.mapper;

import com.nynu.goule.pojo.OperateLog;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;
@Mapper
public interface OperateLogMapper {
    void addOperateLog(Map<String, Object> map);

    List<OperateLog> getAllOperateLog(Map<String, Object> map);
}
