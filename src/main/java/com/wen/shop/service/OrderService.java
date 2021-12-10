package com.wen.shop.service;

import com.wen.shop.domain.Order;
import com.wen.shop.domain.PageBean;
import com.wen.shop.domain.User;

import java.util.List;

public interface OrderService {
    void save(Order order) throws Exception;

    PageBean<Order> findMyOrdersByPage(int pageNumber, int pageSize, String uid) throws Exception;

    Order getById(String oid) throws Exception;

    void update(Order order) throws Exception;

    void setMoney(String uid,int money) throws Exception;

    List<Order> findAllByState(String state) throws Exception;
}
