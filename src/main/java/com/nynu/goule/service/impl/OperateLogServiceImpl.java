package com.nynu.goule.service.impl;

import com.nynu.goule.exception.GeneralException;
import com.nynu.goule.mapper.OperateLogMapper;
import com.nynu.goule.pojo.OperateLog;
import com.nynu.goule.service.OperateLogService;
import com.nynu.goule.utils.ValidateUtil;
import org.springframework.stereotype.Service;

import javax.annotation.RegEx;
import javax.annotation.Resource;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
public class OperateLogServiceImpl implements OperateLogService {

    @Resource
    private OperateLogMapper operateLogMapper;

    @Override
    public void addOperateLog(Map<String, Object> param) {
        String logId; //日志id和记录时间都由代码自动生成
        String acctId = ValidateUtil.isBlankParam(param,"acctId","主账号");
        String opType = ValidateUtil.isBlankParam(param,"opType","操作类型");
        String logCntt = ValidateUtil.isBlankParam(param,"logCntt","操作内容");
        Map<String, Object> operateMap = new HashMap<>();
        try {
            UUID uuid = UUID.randomUUID();
            logId = uuid.toString().replace("-", ""); //UUID会生成带有'-'的主键,这里给去除掉
        }catch (GeneralException e){
            throw new GeneralException("生成主键失败!");
        }
        if(!(OperateLog.OP_TYPE.ADD.equals(opType)) && !(OperateLog.OP_TYPE.MODIFY.equals(opType)) && !(OperateLog.OP_TYPE.DELETE.equals(opType))){
            throw new GeneralException("操作类型错误!");
        }
        operateMap.put("logId",logId);
        operateMap.put("acctId",acctId);
        operateMap.put("opType",opType);
        operateMap.put("logCntt",logCntt);
        operateMap.put("Year",getYearAndMonth());
        operateMap.put("beforeCntt",param.get("beforeCntt"));
        operateMap.put("afterCntt",param.get("afterCntt"));
        operateLogMapper.addOperateLog(operateMap);
    }

    public static String getYearAndMonth(){
        Calendar c = Calendar.getInstance();
        String year = String.valueOf(c.get(Calendar.YEAR));
        int month = c.get(Calendar.MONTH) + 1;
        if(month < 10){
            return year + "0" + month;
        }else {
            return year + month;
        }
    }
}
