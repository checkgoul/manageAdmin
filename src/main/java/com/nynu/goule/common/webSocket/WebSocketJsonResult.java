package com.nynu.goule.common.webSocket;

/**
 *
 * JSON模型
 *
 * 用户后台向前台返回的JSON对象
 *
 *
 */
public class WebSocketJsonResult implements java.io.Serializable {

    private static final long serialVersionUID = -1118025395225258944L;

    private String message = "";

    private int code = 1;

    private Object result = null;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getResult() {
        return result;
    }

    public void setResult(Object obj) {
        this.result = obj;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public WebSocketJsonResult(){}

    public WebSocketJsonResult(Object result, int code, String message){
        this.result = result;
        this.code = code;
        this.message = message;
    }

    public static WebSocketJsonResult successInstance(){
        return new WebSocketJsonResult(null,1,"成功");
    }

    public static WebSocketJsonResult failInstance(String message){
        return new WebSocketJsonResult(null,-1,message);
    }

}
