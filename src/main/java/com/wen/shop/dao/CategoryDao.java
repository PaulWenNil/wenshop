package com.wen.shop.dao;

import com.wen.shop.domain.Category;

import java.util.List;

public interface CategoryDao {
    List<Category> findAll() throws Exception;

    void save(Category c) throws Exception;

    void editCname(String cname, String cid) throws Exception;
}
