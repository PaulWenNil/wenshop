package com.wen.shop.service;

import com.wen.shop.domain.SellerLogin;

import java.util.List;

public interface SellerLoginService{

    void insert(String sid, String ip, String os, String clientType, String browser, Long time, int status)
            throws Exception;

    List<SellerLogin> findAllSellerLogin() throws Exception;
}
