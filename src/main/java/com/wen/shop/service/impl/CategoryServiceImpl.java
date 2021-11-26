package com.wen.shop.service.impl;

import com.wen.shop.dao.CategoryDao;
import com.wen.shop.dao.impl.CategoryDaoImpl;
import com.wen.shop.domain.Category;
import com.wen.shop.service.CategoryService;
import com.wen.shop.utils.JsonUtil;

import java.util.List;

public class CategoryServiceImpl implements CategoryService{
    //查询所有分类
    public String findAll() throws Exception {
        //调用dao 查询所有分类
        CategoryDao cd =new CategoryDaoImpl();
        List<Category> list = cd.findAll();
        //将list转换成json字符串
        if(list!=null && list.size()>0){
            return JsonUtil.list2json(list);
        }
        return null;
    }
}
