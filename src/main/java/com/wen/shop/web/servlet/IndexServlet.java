package com.wen.shop.web.servlet;

import com.wen.shop.domain.Product;
import com.wen.shop.service.ProductService;
import com.wen.shop.service.impl.ProductServiceImpl;
import com.wen.shop.web.servlet.base.BaseServlet;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.List;

//首页模块
@WebServlet(name = "IndexServlet", value = "/index")
public class IndexServlet extends BaseServlet {

    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            //调用productservice查询最新商品 和 热门商品
            ProductService ps = new ProductServiceImpl();
            List<Product> hotList = ps.findHot();
            List<Product> newList = ps.findNew();

            //将两个list都放入request域中
            request.setAttribute("hList",hotList);
            request.setAttribute("nList",newList);
        } catch (Exception e) {
        }
        return "/jsp/index.jsp";
    }
}
