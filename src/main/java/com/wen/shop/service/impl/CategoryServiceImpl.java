package com.wen.shop.service.impl;

import com.wen.shop.dao.CategoryDao;
import com.wen.shop.dao.impl.CategoryDaoImpl;
import com.wen.shop.domain.Category;
import com.wen.shop.service.CategoryService;
import com.wen.shop.utils.JsonUtil;

import java.util.List;

public class CategoryServiceImpl implements CategoryService{
    //后台展示所有分类
    public List<Category> findList() throws Exception {
        CategoryDao cd = new CategoryDaoImpl();
        return cd.findAll();
    }

    //查询所有分类
    public String findAll() throws Exception {
        //调用dao 查询所有分类
        List<Category> list = findList();
        //将list转换成json字符串
        if(list!=null && list.size()>0){
            return JsonUtil.list2json(list);
        }
        return null;
    }


    //添加分类
    public void save(Category c) throws Exception {
        //调用dao 完成添加
        CategoryDao cd = new CategoryDaoImpl();
        cd.save(c);
    }

    //修改分类名
    public void editCname(String cname, String cid) throws Exception {
        //调用dao 完成修改
        CategoryDao cd = new CategoryDaoImpl();
        cd.editCname(cname,cid);
    }
}
