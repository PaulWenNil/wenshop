package com.wen.shop.dao;

import com.wen.shop.domain.Seller;

import java.util.List;

public interface SellerDao {
    Seller getBySellernameAndPwd(String sellername, String password) throws Exception;

    Seller findSeller(String sid) throws Exception;

    List<Seller> findAllSeller() throws Exception;

    void saveSeller(Seller seller) throws Exception;

    void updateSeller(String sid, String sellername, String password, String name) throws Exception;

    void deleteSeller(String sid) throws Exception;
}
