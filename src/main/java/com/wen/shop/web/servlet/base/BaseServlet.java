package com.wen.shop.web.servlet.base;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.lang.reflect.Method;
import java.io.IOException;

@WebServlet(name = "BaseServlet", value = "/BaseServlet")
public class BaseServlet extends HttpServlet {
    protected void service(HttpServletRequest request, HttpServletResponse response)throws ServletException{
        try {
            //获取方法名称
            String methodName = request.getParameter("method");
            //判断是否为空
            if (methodName == null || methodName.trim().length()==0) {
                methodName = "execute" ;
            }
            //获取方法对象
            Method method = this.getClass().getMethod(methodName, HttpServletRequest.class,HttpServletResponse.class);
            //执行方法，获取返回值
            String path = (String) method.invoke(this, request, response);
            //判断返回值是否为空，若不会为空统一处理请求转发
            if(path!= null){
                request.getRequestDispatcher(path).forward(request,response);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }

    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServletException,IOException {
        response.setContentType("text/html;charset=utf-8");
        response.getWriter().println("请输入完整的路径");
        return null;
    }
}
