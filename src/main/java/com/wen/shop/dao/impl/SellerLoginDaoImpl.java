package com.wen.shop.dao.impl;

import com.wen.shop.dao.SellerLoginDao;
import com.wen.shop.domain.SellerLogin;
import com.wen.shop.utils.DataSourceUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import java.util.List;

public class SellerLoginDaoImpl implements SellerLoginDao {
    @Override
    public void insert(String sid, String ip, String os, String clientType, String browser, Long time, int status) throws Exception {
        QueryRunner qr = new QueryRunner(DataSourceUtils.getDataSource());
        String sql = "insert into sl_record values(?,?,?,?,?,?,?)";
        qr.update(sql, sid, time, ip, os, browser, clientType, status);
    }

    @Override
    public List<SellerLogin> findAllRecord() throws Exception {
        QueryRunner qr = new QueryRunner(DataSourceUtils.getDataSource());
        String sql = "select * from sl_record";
        return qr.query(sql,new BeanListHandler<>(SellerLogin.class));
    }
}
