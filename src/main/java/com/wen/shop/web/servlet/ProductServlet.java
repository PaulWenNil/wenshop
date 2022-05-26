package com.wen.shop.web.servlet;

import com.wen.shop.dao.ExplorerDao;
import com.wen.shop.dao.impl.ExplorerDaoImpl;
import com.wen.shop.domain.Explorer;
import com.wen.shop.domain.PageBean;
import com.wen.shop.domain.Product;
import com.wen.shop.domain.User;
import com.wen.shop.service.ProductService;
import com.wen.shop.service.impl.ProductServiceImpl;
import com.wen.shop.utils.UUIDUtils;
import com.wen.shop.utils.UserAgentUtils;
import com.wen.shop.web.servlet.base.BaseServlet;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

//前台商品模块
@WebServlet(name = "ProductServlet", value = "/product")
public class ProductServlet extends BaseServlet {
    //通过pid获取商品详情
    public String getById(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            //获取pid
            String pid = request.getParameter("pid");

            //调用service获取单个商品 参数：pid 返回值：product
            ProductService ps = new ProductServiceImpl();
            Product pro = ps.getById(pid);

            //将product放入request域中，请求转发/jsp/product_info.jsp
            request.setAttribute("bean",pro);

            HttpSession session = request.getSession();
            User user = (User)session.getAttribute("user");

            if(user != null){
                String explorer_id = UUIDUtils.getId();
                request.setAttribute("explorer_id",explorer_id);

                String IP = UserAgentUtils.getIpAddr(request);
                Long time = System.currentTimeMillis();

                Explorer explorer = new Explorer(user.getUid(), IP, pid, time, 0L, explorer_id);
                ExplorerDao explorerDao = new ExplorerDaoImpl();
                explorerDao.insert(explorer);
            }
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("msg","查询此商品失败");
            return "/jsp/msg.jsp";
        }

        return "/jsp/product_info.jsp";
    }

    //通过pname获取商品详情
    public String getByName(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            //获取pname
            String pname = request.getParameter("pname");

            //调用service获取单个商品 参数：pname 返回值：product
            ProductService ps = new ProductServiceImpl();
            Product pro = ps.getByName(pname);

            //将product放入request域中，请求转发/jsp/product_info.jsp
            request.setAttribute("bean",pro);

            HttpSession session = request.getSession();
            User user = (User)session.getAttribute("user");

            if(user != null){
                String explorer_id = UUIDUtils.getId();
                request.setAttribute("explorer_id",explorer_id);

                String IP = UserAgentUtils.getIpAddr(request);
                Long time = System.currentTimeMillis();

                Explorer explorer = new Explorer(user.getUid(),IP, pro.getPid(), time,  0L, explorer_id);
                ExplorerDao explorerDao = new ExplorerDaoImpl();
                explorerDao.insert(explorer);
            }
        } catch (Exception e) {
            request.setAttribute("msg","查询此商品失败");
            return "/jsp/msg.jsp";
        }
        return "/jsp/product_info.jsp";
    }

    //分类商品分页展示
    public String findByPage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            //获取pagenumber、cid 设置pagesize
            int pageNumber = 1;
            try {
                pageNumber = Integer.parseInt(request.getParameter("pageNumber"));
            } catch (NumberFormatException e) {
            }
            int pageSize = 12;
            String cid = request.getParameter("cid");

            //调用service 分页查询商品 参数3个 返回值：pagebean
            ProductService ps = new ProductServiceImpl();
            PageBean<Product> bean = ps.findByPage(pageNumber,pageSize,cid);

            //将pagebean放入request中，请求转发product_list.jsp
            request.setAttribute("pb",bean);
        } catch (Exception e) {
            request.setAttribute("msg","分页查询失败");
            return "/jsp/msg.jsp";
        }

        return "/jsp/product_list.jsp";
    }
}
