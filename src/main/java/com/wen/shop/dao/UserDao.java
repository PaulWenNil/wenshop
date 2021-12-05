package com.wen.shop.dao;

import com.wen.shop.domain.User;

import java.sql.SQLException;

public interface UserDao {
    void save(User user) throws Exception;
    User getByCode(String code) throws Exception;
    void update(User user) throws Exception;

    User getByUsernameAndPwd(String username, String password) throws Exception;

    User findUser(String uid) throws Exception;
}