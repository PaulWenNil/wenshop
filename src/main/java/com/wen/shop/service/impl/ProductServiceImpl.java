package com.wen.shop.service.impl;

import com.wen.shop.dao.ProductDao;
import com.wen.shop.dao.impl.ProductDaoImpl;
import com.wen.shop.domain.PageBean;
import com.wen.shop.domain.Product;
import com.wen.shop.service.ProductService;

import java.util.List;

public class ProductServiceImpl implements ProductService {
    @Override
    //查询热门商品
    public List<Product> findHot() throws Exception {
        ProductDao pd = new ProductDaoImpl();
        return pd.findHot();
    }

    @Override
    //查询最新商品
    public List<Product> findNew() throws Exception {
        ProductDao pd = new ProductDaoImpl();
        return pd.findNew();
    }

    @Override
    //单个商品详情
    public Product getById(String pid) throws Exception {
        ProductDao pd = new ProductDaoImpl();
        return pd.getById(pid);
    }

    @Override
    //分页展示商品
    public PageBean<Product> findByPage(int pageNumber, int pageSize, String cid) throws Exception {
        ProductDao pDao = new ProductDaoImpl();
        //创建pagebean
        PageBean<Product> pb = new PageBean<>(pageNumber, pageSize);

        //设置当前页数据
        List<Product> data = pDao.findByPage(pb,cid);
        pb.setData(data);

        //设置总记录数
        int totalRecord = pDao.getTotalRecord(cid);
        pb.setTotalRecord(totalRecord);

        return pb;
    }

    @Override
    //后台展示已上架商品
    public List<Product> findAll() throws Exception {
        ProductDao pDao = new ProductDaoImpl();
        return pDao.findAll();
    }

    @Override
    //后台保存商品
    public void save(Product p) throws Exception {
        ProductDao pDao = new ProductDaoImpl();
        pDao.save(p);
    }

    @Override
    //后台删除商品
    public void deletePro(String pid) throws Exception {
        ProductDao pDao = new ProductDaoImpl();
        pDao.deletePro(pid);
    }

    @Override
    //后台修改商品
    public void edit(Product p) throws Exception {
        ProductDao pDao = new ProductDaoImpl();
        pDao.edit(p);
    }

}
