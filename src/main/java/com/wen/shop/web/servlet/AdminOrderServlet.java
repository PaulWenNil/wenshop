package com.wen.shop.web.servlet;

import com.wen.shop.constant.Constant;
import com.wen.shop.dao.OperationDao;
import com.wen.shop.dao.impl.OperationDaoImpl;
import com.wen.shop.domain.Operation;
import com.wen.shop.domain.Order;
import com.wen.shop.domain.OrderItem;
import com.wen.shop.domain.Seller;
import com.wen.shop.service.OrderService;
import com.wen.shop.service.impl.OrderServiceImpl;
import com.wen.shop.utils.JsonUtil;
import com.wen.shop.utils.MailUtils;
import com.wen.shop.utils.UUIDUtils;
import com.wen.shop.web.servlet.base.BaseServlet;
import net.sf.json.JSONArray;
import net.sf.json.JsonConfig;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "AdminOrderServlet", value = "/adminOrder")
//后台订单模块
public class AdminOrderServlet extends BaseServlet {
    //修改订单状态
    public String updateState(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            //获取oid
            String oid = request.getParameter("oid");

            //调用service 获取订单
            OrderService os = new OrderServiceImpl();
            Order order = os.getById(oid);

            //设置订单的状态，更新
            order.setState(Constant.ORDER_SEND);
            os.update(order);

            //发送确认收货邮件
            String emailMsg = "您购买的订单号为："+oid+"的商品已发货,若已收货请<a href='http://120.79.99.56:8080/shop/order?method=updateState&oid="+oid+"'>点此确认收货</a>";
            MailUtils.sendMail(order.getUser().getEmail(), emailMsg);

            OperationDao operationDao = new OperationDaoImpl();
            HttpSession session = request.getSession();
            Seller seller = (Seller) session.getAttribute("seller");
            String detail = "Dispatch order: "+ order.getOid();

            String operation_id = UUIDUtils.getId();
            Long time = System.currentTimeMillis();
            Operation operation = new Operation(seller.getSid(), detail, time, operation_id);
            operationDao.insert(operation);

            //重定向
            response.sendRedirect(request.getContextPath()+"/adminOrder?method=findAllByState&state=1");
        } catch (Exception e) {
        }
        return null;
    }

    //后台展示订单详情
    public String showDetail(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            //设置编码
            response.setContentType("text/html;charset=utf-8");

            //获取oid
            String oid = request.getParameter("oid");

            //调用service获取订单
            OrderService os = new OrderServiceImpl();
            Order order = os.getById(oid);

            //获取订单的订单项列表转换成json 写回浏览器
            if(order!= null){
                List<OrderItem> list = order.getItems();
                if(list!=null && list.size()>0){
                    JsonConfig config = JsonUtil.configJson(new String[]{"order", "pdate", "pdesc", "itemid"});
                    response.getWriter().println(JSONArray.fromObject(list,config));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    //后台按状态查询订单列表
    public String findAllByState(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            //获取state
            String state = request.getParameter("state");

            //调用service获取不同列表
            OrderService os = new OrderServiceImpl();
            List<Order> list= os.findAllByState(state);

            //将list放入request域中 请求转发
            request.setAttribute("list",list);
        } catch (Exception e) {
        }
        return "/admin/order/list.jsp";
    }
}
