package com.wen.shop.dao.impl;

import com.wen.shop.dao.SellerDao;
import com.wen.shop.domain.Seller;
import com.wen.shop.utils.DataSourceUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import java.util.List;

public class SellerDaoImpl implements SellerDao {
    @Override
    public Seller getBySellernameAndPwd(String sellername, String password) throws Exception{
        //销售员登录
        QueryRunner qr = new QueryRunner(DataSourceUtils.getDataSource());
        String sql = "select * from seller where sellername = ? and password = ? limit 1";
        return qr.query(sql,new BeanHandler<>(Seller.class),sellername,password);
    }

    @Override
    public Seller findSeller(String sid) throws Exception {
        QueryRunner qr = new QueryRunner(DataSourceUtils.getDataSource());
        String sql = "select * from seller where sid=?";
        return qr.query(sql, new BeanHandler<>(Seller.class), sid);
    }

    @Override
    public List<Seller> findAllSeller() throws Exception {
        QueryRunner qr = new QueryRunner(DataSourceUtils.getDataSource());
        String sql = "select * from seller";
        return qr.query(sql, new BeanListHandler<>(Seller.class));
    }

    @Override
    public void saveSeller(Seller seller) throws Exception {
        QueryRunner qr = new QueryRunner(DataSourceUtils.getDataSource());
        String sql = "insert into seller values(?,?,?,?)";
        qr.update(sql,seller.getSid(),seller.getSellername(),
                seller.getPassword(),seller.getName());
    }

    @Override
    public void updateSeller(String sid, String sellername, String password, String name) throws Exception {
        QueryRunner qr = new QueryRunner(DataSourceUtils.getDataSource());
        String sql = "update seller set sellername=?, password=?, name=? where SID=?";
        qr.update(sql,sellername, password, name, sid);
    }

    @Override
    public void deleteSeller(String sid) throws Exception {
        QueryRunner qr = new QueryRunner(DataSourceUtils.getDataSource());
        String sql = "delete from seller where SID=?";
        qr.update(sql,sid);
    }
}
