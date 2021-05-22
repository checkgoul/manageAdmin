package com.nynu.goule.utils;

public class CommonConstants {

    public static interface AUTHID{
        //对应 authElement表中的数据,要想进行操作,必须拥有相对应的权限
        public final static String SELECT = "000101"; //查询权限
        public final static String INSERT = "000102"; //新增权限
        public final static String UPDATE = "000103"; //修改权限
        public final static String DELETE = "000104"; //删除权限

        public final static String CATEGORY_DELETE = "000105"; //分类管理页面删除权限
        public final static String CATEGORY_SELECT = "000106"; //分类管理页面查询权限
        public final static String CATEGORY_UPDATE = "000107"; //分类管理页面修改权限
        public final static String CATEGORY_INSERT = "000108"; //分类管理页面新增权限

        public final static String PRODUCT_DELETE = "000109"; //商品管理页面删除权限
        public final static String PRODUCT_SELECT = "000110"; //商品管理页面查询权限
        public final static String PRODUCT_UPDATE = "000111"; //商品管理页面修改权限
        public final static String PRODUCT_INSERT = "000112"; //商品管理页面新增权限

        public final static String USER_DELETE = "000113"; //用户管理页面删除权限
        public final static String USER_SELECT = "000114"; //用户管理页面查询权限
        public final static String USER_UPDATE = "000115"; //用户管理页面修改权限
        public final static String USER_INSERT = "000116"; //用户管理页面新增权限
    }

}
