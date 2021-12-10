package com.wen.shop.web.servlet;

import com.wen.shop.constant.Constant;
import com.wen.shop.domain.*;
import com.wen.shop.service.OrderService;
import com.wen.shop.service.impl.OrderServiceImpl;
import com.wen.shop.utils.UUIDUtils;
import com.wen.shop.web.servlet.base.BaseServlet;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.Date;

//订单模块
@WebServlet(name = "OrderServlet", value = "/order")
public class OrderServlet extends BaseServlet {
    //更改订单为已完成
    public String updateState(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            //获取oid
            String oid = request.getParameter("oid");

            //调用service 获取订单
            OrderService os = new OrderServiceImpl();
            Order order = os.getById(oid);

            //设置订单的状态，更新
            order.setState(Constant.ORDER_FINISH);
            os.update(order);

            request.setAttribute("msg","感谢您的这次购物，欢迎下次光临");
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("msg","确认收货失败，请去官网查看订单详情");
        }
        return "/jsp/msg.jsp";
    }

    //获取订单详情
    public String getById(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            //获取oid
            String oid = request.getParameter("oid");

            //调用service查询单个订单
            OrderService os = new OrderServiceImpl();
            Order order = os.getById(oid);

            //请求转发
            request.setAttribute("bean",order);
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("msg","查询订单详情失败");
            return "/jsp/msg.jsp";
        }
        return "/jsp/order_info.jsp";
    }

    //我的订单
    public String findMyOrdersByPage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            //获取pageNumber 设置pagesize 获取userid
            int pageNumber = Integer.parseInt(request.getParameter("pageNumber"));
            int pageSize = 3;

            User user = (User)request.getSession().getAttribute("user");
            if(user == null){
                //未登录
                request.setAttribute("msg","请先登录");
                return "/jsp/msg.jsp";
            }

            //调用service获取当前页所有数据（pagebean）
            OrderService os = new OrderServiceImpl();
            PageBean<Order> bean = os.findMyOrdersByPage(pageNumber,pageSize,user.getUid());

            //将pagebean放入request域中，请求转发order_list.jsp
            request.setAttribute("pb",bean);
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("msg","获取我的订单失败");
            return "/jsp/msg.jsp";
        }
        return "/jsp/order_list.jsp";
    }

    //保存订单
    public String save(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            //从session获取user
            User user =(User) request.getSession().getAttribute("user");
            if(user == null){
                //未登录
                request.setAttribute("msg","请先登录");
                return "/jsp/msg.jsp";
            }

            //获取购物车
            Cart cart = (Cart) request.getSession().getAttribute("cart");

            //封装订单(Order)对象
            //创建对象
            Order order = new Order();

            //设置oid
            order.setOid(UUIDUtils.getId());

            //设置ordertime
            order.setOrdertime(new Date());

            //设置total
            order.setTotal(cart.getTotal());

            //设置state
            order.setState(Constant.ORDER_NOT_PAY);

            //设置user
            order.setUser(user);

            //设置items(订单项列表)
            for (CartItem ci: cart.getCartItems()
                 ) {
                //封装成orderitem
                    //创建orderitem
                OrderItem oi = new OrderItem();

                    //设置itemid
                oi.setItemId(UUIDUtils.getId());

                    //设置count
                oi.setCount(ci.getCount());

                    //设置subtotal
                oi.setSubtotal(ci.getSubtotal());

                    //设置product
                oi.setProduct(ci.getProduct());

                    //设置order
                oi.setOrder(order);

                //将orderitem加入order的items
                order.getItems().add(oi);
            }

            //调用orderservice完成保存
            OrderService os = new OrderServiceImpl();
            os.save(order);

            //清空购物车
            cart.clearCart();

            //请求转发到order_info.jsp
            request.setAttribute("bean",order);
        } catch (Exception e) {
        }
        return "/jsp/order_info.jsp";
    }

    //支付
    public String pay(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            //获取收货人信息 获取oid 获取银行
            String address=request.getParameter("address");
            String name=request.getParameter("name");
            String telephone=request.getParameter("telephone");
            String oid=request.getParameter("oid");

            //从session获取user
            User user =(User) request.getSession().getAttribute("user");
            if(user == null){
                //未登录
                request.setAttribute("msg","请先登录");
                return "/jsp/msg.jsp";
            }

            //调用service获取订单 修改收货人信息 更新订单
            OrderService os= new OrderServiceImpl();
            Order order = os.getById(oid);

            order.setAddress(address);
            order.setName(name);
            order.setTelephone(telephone);
            os.update(order);

            String uid = user.getUid();
            int money = user.getMoney();
            if (money< order.getTotal()){
                request.setAttribute("msg","余额不足");
            }
            else{
                money -=order.getTotal();
                os.setMoney(uid,money);
                request.setAttribute("msg","支付成功,您的余额："+money+"元");

                order.setState(Constant.ORDER_PAY);
                os.update(order);
            }
            return "/jsp/msg.jsp";
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("msg","支付失败");
            return "/jsp/msg.jsp";
        }
    }
}
