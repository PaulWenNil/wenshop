package com.wen.shop.dao.impl;

import com.wen.shop.dao.OrderDao;
import com.wen.shop.domain.*;
import com.wen.shop.utils.DataSourceUtils;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.util.List;
import java.util.Map;

public class OrderDaoImpl implements OrderDao {
    @Override
    //保存订单
    public void save(Order order) throws Exception {
        QueryRunner qr = new QueryRunner();
        String sql = "insert into orders values(?,?,?,?,?,?,?,?)";
        qr.update(DataSourceUtils.getConnection(),sql,order.getOid(),order.getOrdertime(),
                order.getTotal(),order.getState(),order.getAddress(),order.getName(),
                order.getTelephone(),order.getUser().getUid());
    }

    @Override
    //保存订单项
    public void saveItem(OrderItem oi) throws Exception {
        QueryRunner qr = new QueryRunner();
        String sql = "insert into orderitem values(?,?,?,?,?)";
        qr.update(DataSourceUtils.getConnection(),sql,oi.getItemId(),oi.getCount(),
                oi.getSubtotal(),oi.getProduct().getPid(),oi.getOrder().getOid());
    }

    @Override
    //获取我的订单的总条数
    public int getTotalRecord(String uid) throws Exception {
        QueryRunner qr = new QueryRunner(DataSourceUtils.getDataSource());
        String sql = "select count(*) from orders where uid = ?" ;
        return ((Long)qr.query(sql,new ScalarHandler<>(),uid)).intValue();
    }

    @Override
    //获取我的订单 当前页数据
    public List<Order> findMyOrdersByPage(PageBean<Order> pb, String uid) throws Exception {
        QueryRunner qr = new QueryRunner(DataSourceUtils.getDataSource());
        //查询所有订单（基本信息）
        String sql = "select * from orders where uid =? order by ordertime desc limit ?,?";
        List<Order> list = qr.query(sql, new BeanListHandler<>(Order.class), uid, pb.getStartIndex(), pb.getPageSize());

        //遍历订单集合 获取每一个订单，查询每个订单的订单项
        for (Order order: list
             ) {
            sql= "select * from orderitem oi,product p where oi.pid = p.pid and oi.oid=?";
            List<Map<String, Object>> mapList = qr.query(sql, new MapListHandler(), order.getOid());

            //遍历mapList 获取每一个订单项详情，封装成orderitem，将其加入当前订单的订单项列表中
            for (Map<String, Object> map: mapList
                 ) {
                //封装成orderitem
                    //创建orderitem
                OrderItem oi = new OrderItem();

                    //封装orderitem
                BeanUtils.populate(oi,map);

                    //手动封装product
                Product p = new Product();
                BeanUtils.populate(p,map);
                oi.setProduct(p);

                //将orderitem放入order的订单项列表
                order.getItems().add(oi);
            }
        }
        return list;
    }

    @Override
    //订单详情
    public Order getById(String oid) throws Exception {
        //查询订单基本信息
        QueryRunner qr = new QueryRunner(DataSourceUtils.getDataSource());
        String sql = "select * from orders where oid =?";
        Order order = qr.query(sql, new BeanHandler<>(Order.class), oid);

        //查询订单项
        sql= "select * from orderitem oi,product p where oi.pid = p.pid and oi.oid=?";
        List<Map<String, Object>> mapList = qr.query(sql, new MapListHandler(), oid);

        //获取每一个订单项详情，封装成orderitem 加入到当前订单的items中
        for (Map<String, Object> map: mapList
        ) {
            //封装成orderitem
                //创建orderitem
            OrderItem oi = new OrderItem();

                //封装orderitem
            BeanUtils.populate(oi,map);

                //手动封装product
            Product p = new Product();
            BeanUtils.populate(p,map);
            oi.setProduct(p);

                //将orderitem放入order的订单项列表
            order.getItems().add(oi);
        }
        return order;
    }

    @Override
    //更新订单信息
    public void update(Order order) throws Exception {
        QueryRunner qr = new QueryRunner(DataSourceUtils.getDataSource());
        String sql = "update orders set state=?,address=?,name=?,telephone=? where oid=?";
        qr.update(sql,order.getState(),order.getAddress(),order.getName(),order.getTelephone(),order.getOid());
    }

   @Override
    //设置用户余额
    public void setMoney(String uid, int money) throws Exception {
        QueryRunner qr = new QueryRunner(DataSourceUtils.getDataSource());
        String sql = "update user set money=? where uid=?";
        qr.update(sql,money,uid);
    }
}
