package com.wen.shop.web.servlet;


import com.wen.shop.domain.Seller;
import com.wen.shop.domain.SellerLogin;
import com.wen.shop.domain.UserLogin;
import com.wen.shop.service.SellerLoginService;
import com.wen.shop.service.SellerService;
import com.wen.shop.service.UserLoginService;
import com.wen.shop.service.impl.SellerLoginServiceImpl;
import com.wen.shop.service.impl.SellerServiceImpl;
import com.wen.shop.service.impl.UserLoginServiceImpl;
import com.wen.shop.web.servlet.base.BaseServlet;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "ManagerServlet", value = "/manager")
public class ManagerServlet extends BaseServlet {
    //查找用户登录退出
    public String findUserLoginRecord(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            //调用service
            UserLoginService userLoginService = new UserLoginServiceImpl();
            List<UserLogin> list = userLoginService.findAllUserLogin();

            //将返回值放入request中，请求转发
            request.setAttribute("list", list);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
        return "/manager/userloginlist.jsp";
    }

    //查找销售员登录退出
    public String findSellerLoginRecord(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            //调用service
            SellerLoginService sellerLoginService = new SellerLoginServiceImpl();
            List<SellerLogin> list = sellerLoginService.findAllSellerLogin();

            //将返回值放入request中，请求转发
            request.setAttribute("list", list);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
        return "/manager/sellerloginlist.jsp";
    }

    public String findAllSeller(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            //调用service 查询所有销售员
            SellerService sellerService = new SellerServiceImpl();
            List<Seller> list = sellerService.findAllSeller();

            //将返回值放入request中，请求转发
            request.setAttribute("list",list);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
        return "/manager/seller/list.jsp";
    }

    public String deleteSeller(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            String sid = request.getParameter("sid");
            SellerService sellerService = new SellerServiceImpl();
            sellerService.deleteSeller(sid);
            //重定向
            response.sendRedirect(request.getContextPath()+"/manager?method=findAllSeller");
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
       return null;
    }
}
