package com.nynu.goule.service;

import com.nynu.goule.common.Result;

import java.io.IOException;
import java.util.Map;

public interface MsgBoardService {
    Result getAllMsg();

    Result addMsg(Map<String, Object> param) throws IOException;
}
