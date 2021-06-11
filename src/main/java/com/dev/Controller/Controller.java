package com.dev.Controller;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public interface Controller {
    void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException;
}
