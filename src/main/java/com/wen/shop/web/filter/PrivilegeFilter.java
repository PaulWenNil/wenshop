package com.wen.shop.web.filter;

import com.wen.shop.domain.User;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class PrivilegeFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws IOException, ServletException {
        //强转
        HttpServletRequest request=(HttpServletRequest) req;
        HttpServletResponse response=(HttpServletResponse) resp;

        //逻辑
            //从session中获取用户
        User user = (User)request.getSession().getAttribute("user");

        if(user==null){
            //未登录
            request.setAttribute("msg","请先登录");
            request.getRequestDispatcher("/jsp/msg.jsp").forward(request,response);
            return;
        }
        //放行
        chain.doFilter(request,response);
    }

    @Override
    public void destroy() {
        Filter.super.destroy();
    }
}
