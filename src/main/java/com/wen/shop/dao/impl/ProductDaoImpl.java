package com.wen.shop.dao.impl;

import com.wen.shop.constant.Constant;
import com.wen.shop.dao.ProductDao;
import com.wen.shop.domain.PageBean;
import com.wen.shop.domain.Product;
import com.wen.shop.utils.DataSourceUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.util.List;

public class ProductDaoImpl implements ProductDao {

    @Override
    //查询热门
    public List<Product> findHot() throws Exception {
        QueryRunner qr =new QueryRunner(DataSourceUtils.getDataSource());
        String sql = "select * from product where is_hot = ? and pflag = ? order by pdate desc limit 9";
        return qr.query(sql,new BeanListHandler<>(Product.class), Constant.PRODUCT_IS_HOT,Constant.PRODUCT_IS_UP);
    }

    @Override
    //查询最新
    public List<Product> findNew() throws Exception {
        QueryRunner qr =new QueryRunner(DataSourceUtils.getDataSource());
        String sql = "select * from product where pflag = ? order by pdate desc limit 9";
        return qr.query(sql,new BeanListHandler<>(Product.class), Constant.PRODUCT_IS_UP);
    }

    @Override
    //查询单个商品
    public Product getById(String pid) throws Exception {
        QueryRunner qr = new QueryRunner(DataSourceUtils.getDataSource());
        String sql = "select * from product where pid = ? limit 1";
        return qr.query(sql, new BeanHandler<>(Product.class),pid);
    }

    @Override
    //查询当前页的数据
    public List<Product> findByPage(PageBean<Product> pb, String cid) throws Exception {
        QueryRunner qr = new QueryRunner(DataSourceUtils.getDataSource());
        String sql = "select * from product where cid = ? and pflag = ? order by pdate desc limit ?,?";
        return qr.query(sql,new BeanListHandler<>(Product.class),cid,Constant.PRODUCT_IS_UP,
                pb.getStartIndex(),pb.getPageSize());
    }

    @Override
    //获取总记录数
    public int getTotalRecord(String cid) throws Exception {
        QueryRunner qr = new QueryRunner(DataSourceUtils.getDataSource());
        String sql ="select count(*) from product where cid =? and pflag=?";
        return ((Long)qr.query(sql, new ScalarHandler<>(),cid,Constant.PRODUCT_IS_UP)).intValue();
    }
}
