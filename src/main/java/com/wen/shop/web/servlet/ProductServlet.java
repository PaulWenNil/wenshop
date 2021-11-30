package com.wen.shop.web.servlet;

import com.wen.shop.domain.PageBean;
import com.wen.shop.domain.Product;
import com.wen.shop.service.ProductService;
import com.wen.shop.service.impl.ProductServiceImpl;
import com.wen.shop.web.servlet.base.BaseServlet;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

//前台商品模块
@WebServlet(name = "ProductServlet", value = "/product")
public class ProductServlet extends BaseServlet {
    //商品详情
    public String getById(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            //获取pid
            String pid = request.getParameter("pid");

            //调用service获取单个商品 参数：pid 返回值：product
            ProductService ps = new ProductServiceImpl();
            Product pro = ps.getById(pid);

            //将product放入request域中，请求转发/jsp/product_info.jsp
            request.setAttribute("bean",pro);
        } catch (Exception e) {
            request.setAttribute("msg","查询此商品失败");
            return "/jsp/msg.jsp";
        }

        return "/jsp/product_info.jsp";
    }
    //分类商品分页展示
    public String findByPage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            //获取pagenumber、cid 设置pagesize
            int pageNumber = 1;
            try {
                pageNumber = Integer.parseInt(request.getParameter("pageNumber"));
            } catch (NumberFormatException e) {
            }
            int pageSize = 12;
            String cid = request.getParameter("cid");

            //调用service 分页查询商品 参数3个 返回值：pagebean
            ProductService ps = new ProductServiceImpl();
            PageBean<Product> bean = ps.findByPage(pageNumber,pageSize,cid);

            //将pagebean放入request中，请求转发product_list.jsp
            request.setAttribute("pb",bean);
        } catch (Exception e) {
            request.setAttribute("msg","分页查询失败");
            return "/jsp/msg.jsp";
        }

        return "/jsp/product_list.jsp";
    }
}
