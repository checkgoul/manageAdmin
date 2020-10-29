package com.nynu.goule.common;

public class Result {
    private Object data;
    private String Status;
    private String msg;

    public static interface RTN_CODE {
        public static final String SUCCESS = "0";
        public static final String ERROR = "-9999";
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
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
