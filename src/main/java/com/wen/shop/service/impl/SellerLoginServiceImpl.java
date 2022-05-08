package com.wen.shop.service.impl;

import com.wen.shop.dao.SellerLoginDao;
import com.wen.shop.dao.impl.SellerLoginDaoImpl;
import com.wen.shop.domain.SellerLogin;
import com.wen.shop.service.SellerLoginService;

import java.util.List;

public class SellerLoginServiceImpl implements SellerLoginService {
    @Override
    public void insert(String sid, String ip, String os, String clientType, String browser, Long time, int status) throws Exception{
        SellerLoginDao sld = new SellerLoginDaoImpl();
        sld.insert(sid, ip, os, clientType, browser, time, status);
    }

    @Override
    public List<SellerLogin> findAllSellerLogin() throws Exception {
        SellerLoginDao sellerLoginDao = new SellerLoginDaoImpl();
        return sellerLoginDao.findAllRecord();
    }
}
