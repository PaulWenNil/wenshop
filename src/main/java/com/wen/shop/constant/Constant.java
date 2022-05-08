package com.wen.shop.constant;

public interface Constant {
    //用户未激活
    int USER_IS_NOT_ACTIVE =0;

    //用户已激活
    int USER_IS_ACTIVE =1;

    //热门商品
    int PRODUCT_IS_HOT =1;

    //商品未下架
    int PRODUCT_IS_UP = 0;

    //商品已下架
    int PRODUCT_IS_DOWN = 1;

    //订单状态
    //未付款
    int ORDER_NOT_PAY = 0;

    //已付款
    int ORDER_PAY = 1;

    //已发货
    int ORDER_SEND = 2;

    //已完成
    int ORDER_FINISH = 3;

    //登录
    int LOGIN_NOW = 1;

    //退出
    int EXIT_NOW = 0;
}
