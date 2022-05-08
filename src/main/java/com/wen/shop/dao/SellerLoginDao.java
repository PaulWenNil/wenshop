package com.wen.shop.dao;

import com.wen.shop.domain.SellerLogin;

import java.util.List;

public interface SellerLoginDao {
    void insert(String sid, String ip, String os, String clientType, String browser, Long time, int status) throws Exception;

    List<SellerLogin> findAllRecord() throws Exception;
}
