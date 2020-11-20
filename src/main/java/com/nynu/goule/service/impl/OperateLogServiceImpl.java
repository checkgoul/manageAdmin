package com.nynu.goule.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.nynu.goule.common.Result;
import com.nynu.goule.exception.GeneralException;
import com.nynu.goule.mapper.OperateLogMapper;
import com.nynu.goule.pojo.OperateLog;
import com.nynu.goule.service.OperateLogService;
import com.nynu.goule.utils.DateUtil;
import com.nynu.goule.utils.ValidateUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.sql.Timestamp;
import java.util.*;

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
        Timestamp time = DateUtil.getCurrentTimestamp();
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
        operateMap.put("logId",logId); //
        operateMap.put("acctId",acctId);
        operateMap.put("opType",opType);
        operateMap.put("logCntt",logCntt);
        operateMap.put("opMenu",param.get("opMenu"));
        operateMap.put("Year",getYearAndMonth()); //
        operateMap.put("beforeCntt",param.get("beforeCntt"));
        operateMap.put("afterCntt",param.get("afterCntt"));
        operateMap.put("opTime",time); //
        operateLogMapper.addOperateLog(operateMap);
    }

    @Override
    public Result getOperateLog(int pageNum, int pageSize) {
        Result result = new Result();
        pageNum = pageNum == 1 ? 1 : pageNum;
        pageSize = pageSize == 0 ? 8 : pageSize;
        PageHelper.startPage(pageNum, pageSize);
        Map<String, Object> map = new HashMap<>();
        map.put("Year",getYearAndMonth());
        List<OperateLog> operateLogList = operateLogMapper.getAllOperateLog(map);
        PageInfo<OperateLog> operateLogPageInfo = new PageInfo<>(operateLogList);
        result.setData(operateLogPageInfo);
        result.setStatus(Result.RTN_CODE.SUCCESS);
        return result;
    }

    @Override
    public Result searchInfoByAny(Integer pageNum, Integer pageSize, String searchName, String opType, String opMenu) {
        Result result = new Result();
        Map<String, Object> searchMap = new HashMap<>();
        pageNum = pageNum == 1 ? 1 : pageNum;
        pageSize = pageSize == 0 ? 8 : pageSize;
        PageHelper.startPage(pageNum, pageSize);
        searchMap.put("searchName", searchName);
        searchMap.put("opType", opType);
        searchMap.put("opMenu", opMenu);
        searchMap.put("Year",getYearAndMonth());
        List<OperateLog> searchInfoList = operateLogMapper.searchInfoByAny(searchMap);
        PageInfo<OperateLog> operateLogPageInfo = new PageInfo<>(searchInfoList);
        result.setData(operateLogPageInfo);
        result.setStatus(Result.RTN_CODE.SUCCESS);
        return result;
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
