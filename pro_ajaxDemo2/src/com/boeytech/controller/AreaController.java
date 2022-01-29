package com.boeytech.controller;

import com.boeytech.dao.AreaDao;
import com.boeytech.dao.impl.AreaDaoImpl;
import com.boeytech.pojo.Area;
import com.boeytech.service.AreaService;
import com.boeytech.service.impl.AreaServiceImpl;
import com.google.gson.Gson;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;


@WebServlet(urlPatterns = "/areaController.do")
public class AreaController extends HttpServlet {
    private AreaService areaService = new AreaServiceImpl();

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Integer parentID = 0;
        try {
            parentID = Integer.parseInt(req.getParameter("parentID"));
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }

        List<Area> areas = areaService.findByParentID(parentID);

        resp.setCharacterEncoding("UTF-8");
        resp.setContentType("text/html;charset=UTF-8");

        Gson gson = new Gson();
        String json = gson.toJson(areas);
        resp.getWriter().write(json);
        System.out.println(json);

    }
}
