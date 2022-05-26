package com.wen.shop.web.servlet;

import com.wen.shop.dao.ExplorerDao;
import com.wen.shop.dao.impl.ExplorerDaoImpl;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "RestServlet", value = "/Rest")
public class RestServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            String explorer_id = request.getParameter("explorer_id");
            Long period = Long.valueOf(request.getParameter("period"));
            ExplorerDao explorerDao = new ExplorerDaoImpl();
            explorerDao.update(period, explorer_id);
        } catch (Exception e) {
            e.printStackTrace();
            response.setStatus(500);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
