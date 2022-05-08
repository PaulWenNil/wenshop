package com.wen.shop.web.servlet;

import com.wen.shop.constant.Constant;
import com.wen.shop.domain.Seller;
import com.wen.shop.service.SellerLoginService;
import com.wen.shop.service.SellerService;
import com.wen.shop.service.impl.SellerLoginServiceImpl;
import com.wen.shop.service.impl.SellerServiceImpl;
import com.wen.shop.utils.UserAgentUtils;
import com.wen.shop.web.servlet.base.BaseServlet;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
@WebServlet(name = "SellerServlet", value = "/seller")
public class SellerServlet extends BaseServlet {
    public String login(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            //获取用户名和密码
            String sellername = request.getParameter("sellername");
            String password = request.getParameter("password");

            //调用service完成登录 返回值：seller
            SellerService ss = new SellerServiceImpl();
            Seller seller = ss.login(sellername,password);

            //判断seller 根据结果生成提示
            if(seller==null){
                request.setAttribute("msg","用户名和密码不匹配");
                return "/admin/index.jsp";
            }

            String sid = seller.getSid();
            SellerLoginService sls = new SellerLoginServiceImpl();
            String IP = UserAgentUtils.getIpAddr(request);
            String os = UserAgentUtils.getOS(request);
            String clientType = UserAgentUtils.getClientType(request);
            String browser = UserAgentUtils.getBrowser(request);
            Long time = System.currentTimeMillis();
            sls.insert(sid, IP, os, clientType, browser, time, Constant.LOGIN_NOW);

            //登录成功，保存登录状态
            request.getSession().setAttribute("seller", seller);

            return "/admin/home.jsp";
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("msg","用户登录失败");
            return "/admin/index.jsp";
        }
    }

    public String logout(HttpServletRequest request, HttpServletResponse response) throws Exception {

        Seller seller = (Seller) request.getSession().getAttribute("seller");
        String sid = seller.getSid();

        SellerLoginService sls = new SellerLoginServiceImpl();

        String IP = UserAgentUtils.getIpAddr(request);
        String os = UserAgentUtils.getOS(request);
        String clientType = UserAgentUtils.getClientType(request);
        String browser = UserAgentUtils.getBrowser(request);
        Long time = System.currentTimeMillis();

        sls.insert(sid, IP, os, clientType, browser, time, Constant.EXIT_NOW);

        request.getSession().invalidate();
        response.sendRedirect(request.getContextPath()+"/admin");
        return null;
    }
}
