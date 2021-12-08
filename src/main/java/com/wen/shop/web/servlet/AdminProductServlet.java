package com.wen.shop.web.servlet;

import com.wen.shop.domain.Product;
import com.wen.shop.service.CategoryService;
import com.wen.shop.service.ProductService;
import com.wen.shop.service.impl.CategoryServiceImpl;
import com.wen.shop.service.impl.ProductServiceImpl;
import com.wen.shop.web.servlet.base.BaseServlet;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.List;

//后台商品模块
@WebServlet(name = "AdminProductServlet", value = "/adminProduct")
public class AdminProductServlet extends BaseServlet {
    //跳转到修改的页面上
    public String editUI(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            //调用categoryservice 查询所有分类
            CategoryService cs = new CategoryServiceImpl();
            request.setAttribute("list",cs.findList());
            request.setAttribute("pid",request.getParameter("pid"));
        } catch (Exception e) {
        }
        return "/admin/product/edit.jsp";
    }
    //跳转到添加的页面上
    public String addUI(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            //调用categoryservice 查询所有分类
            CategoryService cs = new CategoryServiceImpl();
            request.setAttribute("list",cs.findList());
        } catch (Exception e) {
        }
        return "/admin/product/add.jsp";
    }

    //展示已上架商品
    public String findAll(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            //调用service 查询已上架商品
            ProductService ps = new ProductServiceImpl();
            List<Product> list = ps.findAll();

            //将返回值放入request中，请求转发
            request.setAttribute("list",list);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
        return "/admin/product/list.jsp";
    }
}
