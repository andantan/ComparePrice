package com.dev.Controller;

import com.dev.Pool.Pool;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;

public class FrontController extends HttpServlet {
    HashMap<String, Controller> map = null;

    @Override
    public void init(ServletConfig config) throws ServletException {
        map = new HashMap<>();

        map.put("/performance.do", new PerformanceController());
    }

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        map.get(request.getRequestURI().substring(request.getContextPath().length())).execute(request, response);
    }

    @Override
    public void destroy() {
        Pool.getInstance().getThreadPoolExecutor().shutdown();
    }
}
