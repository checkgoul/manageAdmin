package com.nynu.goule.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

public class MsgBoard {
    private int id;
    private String name;
    private String message;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm")
    private Date createTime;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
