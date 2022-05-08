package com.wen.shop.service.impl;

import com.wen.shop.dao.SellerDao;
import com.wen.shop.dao.impl.SellerDaoImpl;
import com.wen.shop.domain.Seller;
import com.wen.shop.service.SellerService;

import java.util.List;


public class SellerServiceImpl implements SellerService {
    @Override
    public Seller login(String sellername, String password) throws Exception{
        SellerDao sd = new SellerDaoImpl();
        return sd.getBySellernameAndPwd(sellername, password);
    }

    @Override
    public Seller findSeller(String sid) throws Exception {
        SellerDao sellerDao = new SellerDaoImpl();
        return sellerDao.findSeller(sid);
    }

    @Override
    public List<Seller> findAllSeller() throws Exception {
        SellerDao sellerDao = new SellerDaoImpl();
        return sellerDao.findAllSeller();
    }

    @Override
    public void saveSeller(Seller seller) throws Exception {
        SellerDao sellerDao = new SellerDaoImpl();
        sellerDao.saveSeller(seller);
    }

    @Override
    public void updateSeller(String sid, String sellername, String password, String name) throws Exception {
        SellerDao sellerDao = new SellerDaoImpl();
        sellerDao.updateSeller(sid, sellername, password, name);
    }

    @Override
    public void deleteSeller(String sid) throws Exception {
        SellerDao sellerDao = new SellerDaoImpl();
        sellerDao.deleteSeller(sid);
    }
}
