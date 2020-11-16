package com.nynu.goule.pojo;

import java.sql.Timestamp;
import java.util.Date;

public class OperateLog {
    private String logId; //日志id
    private String acctId; //操作账号
    private String opTime; //操作时间
    private String opType; //操作类型 1:新增 2:修改 3:删除
    private String opMenu; //操作菜单 枚举
    private String logCntt; //操作内容
    private String beforeCntt; //操作前数据
    private String afterCntt; //操作后数据

    public static interface OP_TYPE {
        public static final String ADD = "1";
        public static final String MODIFY = "2";
        public static final String DELETE = "3";
    }

    public static interface OP_PATH {
        public static final String CATEGORY_MANAGEMENT = "CATEGORY_MANAGEMENT";
        public static final String PRODUCT_MANAGEMENT = "PRODUCT_MANAGEMENT";
        public static final String USER_MANAGEMENT = "USER_MANAGEMENT";
        public static final String ROLE_MANAGEMENT = "ROLE_MANAGEMENT";
    }

    public String getLogId() {
        return logId;
    }

    public void setLogId(String logId) {
        this.logId = logId;
    }

    public String getAcctId() {
        return acctId;
    }

    public void setAcctId(String acctId) {
        this.acctId = acctId;
    }

    public String getOpType() {
        return opType;
    }

    public void setOpType(String opType) {
        this.opType = opType;
    }

    public String getLogCntt() {
        return logCntt;
    }

    public void setLogCntt(String logCntt) {
        this.logCntt = logCntt;
    }

    public String getBeforeCntt() {
        return beforeCntt;
    }

    public void setBeforeCntt(String beforeCntt) {
        this.beforeCntt = beforeCntt;
    }

    public String getAfterCntt() {
        return afterCntt;
    }

    public void setAfterCntt(String afterCntt) {
        this.afterCntt = afterCntt;
    }

    public String getOpMenu() {
        return opMenu;
    }

    public void setOpMenu(String opMenu) {
        this.opMenu = opMenu;
    }

    public String getOpTime() {
        return opTime;
    }

    public void setOpTime(String opTime) {
        this.opTime = opTime;
    }
}
