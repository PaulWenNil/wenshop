package com.wen.shop.service;

import com.wen.shop.domain.Category;

import java.util.List;

public interface CategoryService {
    String findAll() throws Exception;

    List<Category> findList() throws Exception;

    void save(Category c) throws Exception;

    void editCname(String cname, String cid) throws Exception;
}
