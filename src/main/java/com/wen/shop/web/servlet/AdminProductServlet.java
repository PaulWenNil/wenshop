package com.wen.shop.web.servlet;

import com.wen.shop.dao.OperationDao;
import com.wen.shop.dao.impl.OperationDaoImpl;
import com.wen.shop.domain.Operation;
import com.wen.shop.domain.Product;
import com.wen.shop.domain.Seller;
import com.wen.shop.service.CategoryService;
import com.wen.shop.service.ProductService;
import com.wen.shop.service.impl.CategoryServiceImpl;
import com.wen.shop.service.impl.ProductServiceImpl;
import com.wen.shop.utils.UUIDUtils;
import com.wen.shop.web.servlet.base.BaseServlet;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.List;

//后台商品模块
@WebServlet(name = "AdminProductServlet", value = "/adminProduct")
public class AdminProductServlet extends BaseServlet {
    //删除商品
    public String deletePro(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            ProductService ps = new ProductServiceImpl();
            String pid = request.getParameter("pid");
            Product product = ps.getById(pid);
            ps.deletePro(pid);

            OperationDao operationDao = new OperationDaoImpl();
            HttpSession session = request.getSession();
            Seller seller = (Seller) session.getAttribute("seller");
            String detail = "Delete product: "+ product.getPname();

            String operation_id = UUIDUtils.getId();
            Long time = System.currentTimeMillis();
            Operation operation = new Operation(seller.getSid(), detail, time, operation_id);
            operationDao.insert(operation);

            response.sendRedirect(request.getContextPath()+"/adminProduct?method=findAll");
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
        return null;
    }

    //跳转到修改的页面上
    public String editUI(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            //调用categoryservice 查询所有分类
            CategoryService cs = new CategoryServiceImpl();
            request.setAttribute("list",cs.findList());

            //调用productservice 查询商品
            ProductService ps = new ProductServiceImpl();
            String productId = request.getParameter("pid");
            Product p = ps.getById(productId);
            request.setAttribute("p",p);

            HttpSession session = request.getSession();
            session.setAttribute("pid",productId);
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
