package com.wen.shop.web.servlet;

import com.wen.shop.domain.Seller;
import com.wen.shop.service.SellerService;
import com.wen.shop.service.impl.SellerServiceImpl;
import com.wen.shop.utils.UUIDUtils;
import com.wen.shop.web.servlet.base.BaseServlet;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "addSellerServlet", value = "/addSeller")
public class AddSellerServlet extends BaseServlet {
    public String saveSeller(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            //封装Seller对象
            Seller seller = new Seller();
            seller.setSid(UUIDUtils.getId());
            seller.setSellername(request.getParameter("sellername"));
            seller.setPassword(request.getParameter("password"));
            seller.setName(request.getParameter("name"));

            //调用service完成添加操作
            SellerService sellerService = new SellerServiceImpl();
            sellerService.saveSeller(seller);

            //重定向
            response.sendRedirect(request.getContextPath()+"/manager?method=findAllSeller");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public String addUI(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        return "/manager/seller/add.jsp";
    }

}
