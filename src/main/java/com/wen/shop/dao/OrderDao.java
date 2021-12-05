package com.wen.shop.dao;

import com.wen.shop.domain.Order;
import com.wen.shop.domain.OrderItem;
import com.wen.shop.domain.PageBean;
import com.wen.shop.domain.User;

import java.util.List;

public interface OrderDao {
    void save(Order order) throws Exception;

    void saveItem(OrderItem oi) throws Exception;

    int getTotalRecord(String uid) throws Exception;

    List<Order> findMyOrdersByPage(PageBean<Order> pb, String uid) throws Exception;

    Order getById(String oid) throws Exception;

    void update(Order order) throws Exception;

    void setMoney(String uid, int money) throws Exception;
}
