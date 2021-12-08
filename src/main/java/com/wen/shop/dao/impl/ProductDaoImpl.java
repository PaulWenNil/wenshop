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
        String sql ="select count(*) from product where cid = ? and pflag = ?";
        return ((Long)qr.query(sql, new ScalarHandler<>(),cid,Constant.PRODUCT_IS_UP)).intValue();
    }

    @Override
    //后台展示已上架商品
    public List<Product> findAll() throws Exception {
        QueryRunner qr = new QueryRunner(DataSourceUtils.getDataSource());
        String sql = "select * from product where pflag = ? order by pdate desc";
        return qr.query(sql,new BeanListHandler<>(Product.class),Constant.PRODUCT_IS_UP);
    }

    @Override
    //后台保存商品
    public void save(Product p) throws Exception {
        QueryRunner qr = new QueryRunner(DataSourceUtils.getDataSource());
        String sql = "insert into product values(?,?,?,?,?,?,?,?,?,?)";
        qr.update(sql,p.getPid(), p.getPname(),p.getMarket_price(),
                p.getShop_price(), p.getPimage(),p.getPdate(),
                p.getIs_hot(),p.getPdesc(),p.getPflag(),
                p.getCategory().getCid());
    }

    @Override
    //后台删除商品
    public void deletePro(String pid) throws Exception {
        QueryRunner qr = new QueryRunner(DataSourceUtils.getDataSource());
        String sql = "delete from product where pid=?";
        qr.update(sql,pid);
    }

    @Override
    //后台修改商品
    public void edit(Product p) throws Exception {
        QueryRunner qr = new QueryRunner(DataSourceUtils.getDataSource());
        String sql = "update product set pname=?,market_price=?,shop_price=?,pimage=?,is_hot=?,pdesc=?,cid=? where pid=?";
        qr.update(sql, p.getPname(),p.getMarket_price(),
                p.getShop_price(), p.getPimage(), p.getIs_hot(),
                p.getPdesc(), p.getCategory().getCid(),p.getPid());
    }
}
