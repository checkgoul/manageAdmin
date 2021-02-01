package com.nynu.goule.service.impl;

import com.nynu.goule.common.Result;
import com.nynu.goule.common.webSocket.WebSocketServer;
import com.nynu.goule.mapper.MsgBoardMapper;
import com.nynu.goule.pojo.MsgBoard;
import com.nynu.goule.service.MsgBoardService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.websocket.Session;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class MsgBoardServiceImpl implements MsgBoardService {

    @Resource
    private MsgBoardMapper msgBoardMapper;

    /**
     * 查询所有留言板信息
     * @return
     */
    @Override
    public Result getAllMsg() {
        Result result = new Result();
        List<MsgBoard> msgBoardList = msgBoardMapper.getAllMsg();
        result.setData(msgBoardList);
        result.setStatus("0");
        return result;
    }

    @Override
    public Result addMsg(Map<String, Object> param) throws IOException {
        Result result = new Result();
        String msg = (String) param.get("msg");
        String userId = (String) param.get("userId");
        String msg1 = "实验连接";
        String userId1 = "10";
        WebSocketServer.sendInfo(msg1,userId1);
        return result;
    }
}
