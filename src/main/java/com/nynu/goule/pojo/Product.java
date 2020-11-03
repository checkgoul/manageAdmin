package com.nynu.goule.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

public class Product {
    private int id;
    private String categoryId;
    private String pCategoryId;
    private String productName;
    private String price;
    private String description;
    private int productStatus;  //1:在售0:下线
    private String imgs;
    private String detail;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getpCategoryId() {
        return pCategoryId;
    }

    public void setpCategoryId(String pCategoryId) {
        this.pCategoryId = pCategoryId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getProductStatus() {
        return productStatus;
    }

    public void setProductStatus(int productStatus) {
        this.productStatus = productStatus;
    }

    public String getImgs() {
        return imgs;
    }

    public void setImgs(String imgs) {
        this.imgs = imgs;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }
}
