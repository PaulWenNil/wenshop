package com.wen.shop.dao.impl;

import com.wen.shop.dao.UserLoginDao;
import com.wen.shop.domain.User;
import com.wen.shop.domain.UserLogin;
import com.wen.shop.utils.DataSourceUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import java.util.List;

public class UserLoginDaoImpl implements UserLoginDao {
    @Override
    public void insert(User user, String ip, String os, String clientType, String browser, Long time, int loginNow) throws Exception {
        QueryRunner qr = new QueryRunner(DataSourceUtils.getDataSource());
        String sql = "insert into ul_record values(?,?,?,?,?,?,?)";
        qr.update(sql, time, user.getUid(), os, ip, browser, clientType, loginNow);
    }

    @Override
    public List<UserLogin> findAllRecord() throws Exception {
        QueryRunner qr = new QueryRunner(DataSourceUtils.getDataSource());
        String sql = "select * from ul_record order by `time` DESC";
        return qr.query(sql,new BeanListHandler<>(UserLogin.class));
    }
}
