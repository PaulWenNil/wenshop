package com.wen.shop.web.servlet;

import com.wen.shop.domain.Seller;
import com.wen.shop.service.SellerService;
import com.wen.shop.service.impl.SellerServiceImpl;
import com.wen.shop.web.servlet.base.BaseServlet;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "EditSellerServlet", value = "/editSeller")
public class EditSellerServlet extends BaseServlet {
    public String editUI(HttpServletRequest request, HttpServletResponse response) throws Exception {
        SellerService sellerService = new SellerServiceImpl();
        String sid = request.getParameter("sid");
        Seller seller = sellerService.findSeller(sid);
        request.setAttribute("seller", seller);
        return "/manager/seller/edit.jsp";
    }

    public String editSeller(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            //获取销售员信息
            String name =request.getParameter("name");
            String sellername = request.getParameter("sellername");
            String sid = request.getParameter("sid");
            String password = request.getParameter("password");

            //调用service完成添加操作
            SellerService sellerService = new SellerServiceImpl();
            sellerService.updateSeller(sid, sellername, password, name);

            //重定向
            response.sendRedirect(request.getContextPath()+"/manager?method=findAllSeller");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
