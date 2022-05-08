package com.wen.shop.dao;

import com.wen.shop.domain.User;
import com.wen.shop.domain.UserLogin;

import java.util.List;

public interface UserLoginDao {
    void insert(User user, String ip, String os, String clientType, String browser, Long time, int loginNow) throws Exception;

    List<UserLogin> findAllRecord() throws Exception;
}
