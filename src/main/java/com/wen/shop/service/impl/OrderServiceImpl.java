package com.wen.shop.service.impl;

import com.wen.shop.dao.OrderDao;
import com.wen.shop.dao.impl.OrderDaoImpl;
import com.wen.shop.domain.Order;
import com.wen.shop.domain.OrderItem;
import com.wen.shop.domain.PageBean;
import com.wen.shop.domain.User;
import com.wen.shop.service.OrderService;
import com.wen.shop.utils.DataSourceUtils;

import java.sql.SQLException;
import java.util.List;

public class OrderServiceImpl implements OrderService {
    @Override
    //保存订单
    public void save(Order order) throws Exception{
        try {
            //获取Dao
            OrderDao od = new OrderDaoImpl();

            //开启事务
            DataSourceUtils.startTransaction();

            //向orders表中插入一条
            od.save(order);

            //向orderitem中插入n条
            for (OrderItem oi:order.getItems()
                 ) {
                od.saveItem(oi);
            }

            //事务控制
            DataSourceUtils.commitAndClose();
        } catch (Exception e) {
            e.printStackTrace();
            DataSourceUtils.rollbackAndClose();
            throw e;
        }
    }

    @Override
    //我的订单
    public PageBean<Order> findMyOrdersByPage(int pageNumber, int pageSize, String uid) throws Exception {
        OrderDao od = new OrderDaoImpl();

        //创建pagebean
        PageBean<Order> pb = new PageBean<>(pageNumber, pageSize);

        //查询总条数 设置总计条数
        int totalRecord = od.getTotalRecord(uid);
        pb.setTotalRecord(totalRecord);

        //查询当前页数据 设置当前也数据
        List<Order> data = od.findMyOrdersByPage(pb,uid);
        pb.setData(data);
        return pb;
    }

    @Override
    //订单详情
    public Order getById(String oid) throws Exception{
        OrderDao od = new OrderDaoImpl();
        return od.getById(oid);
    }

    @Override
    //修改订单
    public void update(Order order) throws Exception {
        OrderDao od = new OrderDaoImpl();
        od.update(order);
    }


    @Override
    public void setMoney(String uid,int money) throws Exception {
        OrderDao od = new OrderDaoImpl();
        od.setMoney(uid,money);
    }
}
