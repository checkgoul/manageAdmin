package com.nynu.goule.mapper;

import com.nynu.goule.pojo.MsgBoard;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MsgBoardMapper {
    List<MsgBoard> getAllMsg();
}
