package com.dev.Controller;

import com.dev.Pool.Behavior;
import com.dev.Pool.Parser;
import com.dev.Pool.Pool;
import com.dev.Service.Service;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class PerformanceController implements Controller {
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        response.setContentType("text/html;charset=UTF-8");

        List<Behavior> behaviors;
        String productName = request.getParameter("product");
        String searchOption = request.getParameter("option");

        if (Service.getInstance().search(productName, searchOption) == 1) {
            behaviors = Service.getInstance().load(productName, searchOption);
        } else {
            Pool.getInstance().pooling(new String[]{String.join("+", productName.split(" ")), searchOption});

            behaviors = Pool.getInstance().getBehavior();

            Service.getInstance().insert(behaviors);
        }

        request.setAttribute("urls", Parser.getUrlsHashMap());
        request.setAttribute("productName", productName);
        request.setAttribute("behaviors", behaviors);

        HttpUtil.forward(request, response, "/result.jsp");
    }
}
