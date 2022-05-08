package com.wen.shop.service;

import com.wen.shop.domain.User;
import com.wen.shop.domain.UserLogin;

import java.util.List;

public interface UserLoginService {
    void insert(User user, String ip, String os, String clientType, String browser, Long time, int loginNow) throws Exception;

    List<UserLogin> findAllUserLogin() throws Exception;
}
