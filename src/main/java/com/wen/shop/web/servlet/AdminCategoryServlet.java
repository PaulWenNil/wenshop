package com.wen.shop.web.servlet;

import com.wen.shop.domain.Category;
import com.wen.shop.service.CategoryService;
import com.wen.shop.service.impl.CategoryServiceImpl;
import com.wen.shop.utils.UUIDUtils;
import com.wen.shop.web.servlet.base.BaseServlet;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.rmi.RemoteException;
import java.util.List;

//后台分类管理模块
@WebServlet(name = "AdminCategoryServlet", value = "/adminCategory")
public class AdminCategoryServlet extends BaseServlet {
    //修改分类名称
    public String editCname(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            //获取cname
            String cname =request.getParameter("cname");
            String cid = request.getParameter("cid");

            //调用service完成添加操作
            CategoryService cs = new CategoryServiceImpl();
            cs.editCname(cname,cid);

            //重定向
            response.sendRedirect(request.getContextPath()+"/adminCategory?method=findAll");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    //添加分类
    public String save(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            //封装category对象
            Category c = new Category();
            c.setCid(UUIDUtils.getId());
            c.setCname(request.getParameter("cname"));

            //调用service完成添加操作
            CategoryService cs = new CategoryServiceImpl();
            cs.save(c);

            //重定向
            response.sendRedirect(request.getContextPath()+"/adminCategory?method=findAll");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    //跳转到添加分类页面
    public String addUI(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        return "/admin/category/add.jsp";
    }

    //展示所有分类
    public String findAll(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            //调用service获取所有分类
            CategoryService cs = new CategoryServiceImpl();
            List<Category> list = cs.findList();

            //将返回值放入request域中 请求转发
            request.setAttribute("list",list);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
        return "/admin/category/list.jsp";
    }
}
