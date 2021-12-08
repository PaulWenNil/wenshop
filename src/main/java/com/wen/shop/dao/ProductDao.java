package com.wen.shop.dao;

import com.wen.shop.domain.PageBean;
import com.wen.shop.domain.Product;

import java.util.List;

public interface ProductDao {
    List<Product> findHot() throws Exception;

    List<Product> findNew() throws Exception;

    Product getById(String pid) throws Exception;

    List<Product> findByPage(PageBean<Product> pb, String cid) throws Exception;

    int getTotalRecord(String cid) throws Exception;

    List<Product> findAll() throws Exception;

    void save(Product p) throws Exception;

    void deletePro(String pid) throws Exception;

    void edit(Product p) throws Exception;
}
