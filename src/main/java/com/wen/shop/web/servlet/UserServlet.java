package com.wen.shop.web.servlet;

import com.wen.shop.constant.Constant;
import com.wen.shop.domain.User;
import com.wen.shop.service.UserLoginService;
import com.wen.shop.service.UserService;
import com.wen.shop.service.impl.UserLoginServiceImpl;
import com.wen.shop.service.impl.UserServiceImpl;
import com.wen.shop.utils.UUIDUtils;
import com.wen.shop.utils.UserAgentUtils;
import com.wen.shop.web.servlet.base.BaseServlet;
import org.apache.commons.beanutils.BeanUtils;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet(name = "UserServlet", value = "/user")
public class UserServlet extends BaseServlet {
    //查询余额
    public String getM(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            UserService us = new UserServiceImpl();
            String uid = request.getParameter("uid");
            User user=us.findUser(uid);
            request.setAttribute("msg","您的余额为"+user.getMoney());
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("msg","查找余额失败");
            return "/jsp/msg.jsp";
        }
        return "/jsp/msg.jsp";
    }

    public String loginUI(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //跳转到登录页面
        return "/jsp/login.jsp";
    }

    //用户登录
    public String login(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            //获取用户名和密码
            String username = request.getParameter("username");
            String password = request.getParameter("password");

            //调用service完成登录 返回值：user
            UserService us = new UserServiceImpl();
            User user =us.login(username,password);

            //判断user 根据结果生成提示
            if(user==null){
                request.setAttribute("msg","用户名和密码不匹配");
                return "/jsp/login.jsp";
            }
            if(Constant.USER_IS_ACTIVE!= user.getState()){
                request.setAttribute("msg","请先去邮箱激活再登录");
                return "/jsp/msg.jsp";
            }
            UserLoginService uls = new UserLoginServiceImpl();
            String IP = UserAgentUtils.getIpAddr(request);
            String os = UserAgentUtils.getOS(request);
            String clientType = UserAgentUtils.getClientType(request);
            String browser = UserAgentUtils.getBrowser(request);
            Long time = System.currentTimeMillis();
            uls.insert(user, IP, os, clientType, browser, time, Constant.LOGIN_NOW);
            //登录成功，保存用户登录状态
            request.getSession().setAttribute("user",user);

            //跳转到index.jsp
            response.sendRedirect(request.getContextPath());

            return null;
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("msg","用户登录失败");
            return "/jsp/msg.jsp";
        }
    }

    //用户注册
    public String regist(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            //封装对象
            User user = new User();
            BeanUtils.populate(user,request.getParameterMap());

            //手动封装uid 激活状态state 激活码code
            user.setUid(UUIDUtils.getId());
            user.setState(Constant.USER_IS_NOT_ACTIVE);
            user.setCode(UUIDUtils.getCode());

            //调用service完成注册
            UserService us = new UserServiceImpl();
            us.regist(user);

            //页面转发
            request.setAttribute("msg","用户注册成功！请登录邮箱完成激活");
        } catch (Exception e) {
            e.printStackTrace();
            //转发到msg.jsp
            request.setAttribute("msg","用户注册失败！");
            return "/jsp/msg.jsp";
        }
        return "/jsp/msg.jsp";
    }

    public String registUI(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //跳转到注册页面
        return "/jsp/register.jsp";
    }

    //用户激活
    public String active(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            //接收code
            String code =request.getParameter("code");

            //调用service完成激活，返回值：user
            UserService us = new UserServiceImpl();
            User user = us.active(code);

            //判断user 生成不同的提示信息
            if(user == null){
                //没有找到用户，激活失败
                request.setAttribute("msg","激活失败，请重新激活或者重新注册");
                return "/jsp/msg.jsp";
            }

            //激活成功
            request.setAttribute("msg","激活成功，可以登录");
        } catch (Exception e) {
            request.setAttribute("msg","激活失败，请重新激活或者重新注册");
        }
        return "/jsp/msg.jsp";
    }

    //用户退出
    public String logout(HttpServletRequest request, HttpServletResponse response) throws Exception {

        User user = (User) request.getSession().getAttribute("user");

        UserLoginService uls = new UserLoginServiceImpl();

        String IP = UserAgentUtils.getIpAddr(request);
        String os = UserAgentUtils.getOS(request);
        String clientType = UserAgentUtils.getClientType(request);
        String browser = UserAgentUtils.getBrowser(request);
        Long time = System.currentTimeMillis();

        uls.insert(user, IP, os, clientType, browser, time, Constant.EXIT_NOW);

        request.getSession().invalidate();
        response.sendRedirect(request.getContextPath());
        return null;
    }
}