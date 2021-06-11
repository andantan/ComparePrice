package com.dev.Controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class HttpUtil {
    public static void forward(HttpServletRequest request, HttpServletResponse response, String path) {
        try {
            request.getRequestDispatcher(path).forward(request, response);
        } catch (Exception ERO) {
            System.out.println("[com.dev.Controller.HttpUtil::forward] " + ERO.getMessage());
        }
    }
}
