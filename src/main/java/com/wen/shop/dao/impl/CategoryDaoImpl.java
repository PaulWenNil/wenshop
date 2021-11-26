package com.wen.shop.dao.impl;

import com.wen.shop.dao.CategoryDao;
import com.wen.shop.domain.Category;
import com.wen.shop.utils.DataSourceUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import java.util.List;

public class CategoryDaoImpl implements CategoryDao {
    //查询所有分类
    public List<Category> findAll() throws Exception {
        QueryRunner qr = new QueryRunner(DataSourceUtils.getDataSource());
        String sql = "select * from category";
        return qr.query(sql,new BeanListHandler<>(Category.class));
    }
}
