package com.nynu.goule.exception;

public class SystemMessage {
    private String Status;
    private String msg;

    public SystemMessage(String Status, String msg) {
        super();
        this.Status = Status;
        this.msg = msg;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
