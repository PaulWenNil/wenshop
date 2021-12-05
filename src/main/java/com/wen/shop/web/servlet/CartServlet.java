package com.wen.shop.web.servlet;

import com.wen.shop.domain.Cart;
import com.wen.shop.domain.CartItem;
import com.wen.shop.domain.Product;
import com.wen.shop.service.ProductService;
import com.wen.shop.service.impl.ProductServiceImpl;
import com.wen.shop.web.servlet.base.BaseServlet;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

//购物车模块
@WebServlet(name = "CartServlet", value = "/cart")
public class CartServlet extends BaseServlet {
    //清空购物车
    public String clear(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //获取购物车 执行清空
        getCart(request).clearCart();

        //重定向
        response.sendRedirect(request.getContextPath()+"/jsp/cart.jsp");
        return null;
    }

    //从购物车中移除商品
    public String remove(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //获取商品的pid
        String pid = request.getParameter("pid");

        //获取购物车 执行移除
        getCart(request).removeFromCart(pid);

        //重定向
        response.sendRedirect(request.getContextPath()+"/jsp/cart.jsp");
        return null;
    }

    //加入购物车
    public String add2cart(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            //获取pid count
            String pid = request.getParameter("pid");
            int count = Integer.parseInt(request.getParameter("count"));

            //封装cartitem
                //调用service获取product
            ProductService ps = new ProductServiceImpl();
            Product product = ps.getById(pid);

                //创建cartitem
            CartItem cartItem = new CartItem(product, count);

            //将cartitem加入购物车
                //获取购物车
            Cart cart = getCart(request);

            cart.add2cart(cartItem);

            //重定向
            response.sendRedirect(request.getContextPath()+"/jsp/cart.jsp");
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("msg","加入购物车失败");
            return "/jsp/msg.jsp";
        }
        return null;
    }

    private Cart getCart(HttpServletRequest request) {
        Cart cart =(Cart)request.getSession().getAttribute("cart");
        if(cart == null){
            cart = new Cart();

            //将cart放入当前session中
            request.getSession().setAttribute("cart",cart);
        }
        return cart;
    }
}
