package com.nynu.goule.service.impl;

import com.nynu.goule.common.Result;
import com.nynu.goule.mapper.MsgBoardMapper;
import com.nynu.goule.pojo.MsgBoard;
import com.nynu.goule.service.MsgBoardService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class MsgBoardServiceImpl implements MsgBoardService {

    @Resource
    private MsgBoardMapper msgBoardMapper;

    @Override
    public Result getAllMsg() {
        Result result = new Result();
        List<MsgBoard> msgBoardList =  msgBoardMapper.getAllMsg();
        result.setData(msgBoardList);
        result.setStatus("0");
        return result;
    }
}
