package com.nynu.goule.utils;

public class CommonConstants {

    public static interface AUTHID{
        //对应 authElement表中的数据,要想进行操作,必须拥有相对应的权限
        public final static String SELECT = "000101"; //查询权限
        public final static String INSERT = "000102"; //新增权限
        public final static String UPDATE = "000103"; //修改权限
        public final static String DELETE = "000104"; //删除权限
        public final static String CATEGORY_DELETE = "000105"; //品类管理页面删除权限
        public final static String CATEGORY_SELECT = "000106"; //品类管理页面查询权限
        public final static String CATEGORY_UPDATE = "000107"; //品类管理页面修改权限
        public final static String CATEGORY_INSERT = "000108"; //品类管理页面新增权限
    }

}
