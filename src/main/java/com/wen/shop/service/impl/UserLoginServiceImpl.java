package com.wen.shop.service.impl;

import com.wen.shop.dao.UserLoginDao;
import com.wen.shop.dao.impl.UserLoginDaoImpl;
import com.wen.shop.domain.User;
import com.wen.shop.domain.UserLogin;
import com.wen.shop.service.UserLoginService;

import java.util.List;

public class UserLoginServiceImpl implements UserLoginService {
    @Override
    public void insert(User user, String ip, String os, String clientType, String browser, Long time, int loginNow) throws Exception {
        UserLoginDao userLoginDao = new UserLoginDaoImpl();
        userLoginDao.insert(user, ip, os, clientType, browser, time, loginNow);
    }

    @Override
    public List<UserLogin> findAllUserLogin() throws Exception {
        UserLoginDao userLoginDao = new UserLoginDaoImpl();
        return userLoginDao.findAllRecord();
    }
}
