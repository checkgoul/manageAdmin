package com.nynu.goule.pojo;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.Date;

public class Category {

    private int id;
    private String categoryName;
    private String parentId;
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date addTime;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public Date getAddTime() {
        return addTime;
    }

    public void setAddTime(Date addTime) {
        this.addTime = addTime;
    }
}
