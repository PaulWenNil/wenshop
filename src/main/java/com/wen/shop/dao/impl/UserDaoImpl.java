package com.wen.shop.dao.impl;

import com.wen.shop.dao.UserDao;
import com.wen.shop.domain.User;
import com.wen.shop.utils.DataSourceUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;

import java.sql.SQLException;

public class UserDaoImpl implements UserDao {
    //用户注册
    public void save(User user) throws SQLException {
        QueryRunner qr = new QueryRunner(DataSourceUtils.getDataSource());
        String sql = "insert into user values(?,?,?,?,?,?,?,?,?,?);";
        qr.update(sql, user.getUid(), user.getUsername(), user.getPassword(),
                user.getName(),user.getEmail(),user.getTelephone(),
                user.getBirthday(),user.getSex(),user.getState(),user.getCode());
    }
    //用户激活
    //通过激活码获取用户
    public User getByCode(String code) throws Exception {
        QueryRunner qr = new QueryRunner(DataSourceUtils.getDataSource());
        String sql = "select * from user where code = ? limit 1";
        return qr.query(sql, new BeanHandler<>(User.class),code);
    }

    //更新用户
    public void update(User user) throws Exception {
        QueryRunner qr = new QueryRunner(DataSourceUtils.getDataSource());
        String sql ="update user set state = ?,code = ? where uid = ?";
        qr.update(sql, user.getState(), user.getCode(),user.getUid());
    }

    //用户登录
    public User getByUsernameAndPwd(String username, String password) throws Exception{
        QueryRunner qr = new QueryRunner(DataSourceUtils.getDataSource());
        String sql = "select * from user where username = ? and password = ? limit 1";
        return qr.query(sql,new BeanHandler<>(User.class),username,password);
    }


}