package com.nynu.goule.service;

import com.nynu.goule.common.Result;
import org.apache.ibatis.annotations.Mapper;
import java.util.Map;

public interface OperateLogService {

    void addOperateLog(Map<String, Object> param);

    Result getOperateLog(int pageNum, int pageSize);

    Result searchInfoByAny(Integer pageNum, Integer pageSize, String searchName, String opType, String opMenu);
}
