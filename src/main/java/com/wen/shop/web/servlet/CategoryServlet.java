package com.wen.shop.web.servlet;

import com.wen.shop.service.CategoryService;
import com.wen.shop.service.impl.CategoryServiceImpl;
import com.wen.shop.web.servlet.base.BaseServlet;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
@WebServlet(name = "CategoryServlet", value = "/category")
//前台分类
public class CategoryServlet extends BaseServlet {
    //查询所有分类
    public String findAll(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            //设置响应编码
            response.setContentType("text/html;charset=utf-8");
            //调用service，查询所有分类，返回值json字符串
            CategoryService cs = new CategoryServiceImpl();
            String value = cs.findAll();

            //将字符串写回浏览器
            response.getWriter().println(value);
        } catch (Exception e) {
        }
        return null;
    }
}

