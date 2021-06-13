package com.dev.Controller;

import com.dev.VO.Behavior;
import com.dev.VO.Parser;
import com.dev.VO.Pool;
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

        String productName = request.getParameter("product");
        String searchOption = request.getParameter("option");
        List<Behavior> behaviors;

        // +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
        // 한 번 크롤링된 정보는 데이터베이스에 저장
        // log 테이블: 상품 이름, 검색 옵션, 검색한 시간을 저장
        // url 테이블: 쿠팡, G마켓, 11번가 사이트에 상품 이름과 검색 옵션에 따른 url을 모두 저장
        // coupang, gmarket, street 테이블: 상품 이름, 하이퍼링크, 가격, 이미지 링크, 별점, 리뷰 수를 저장
        //
        // com.dev.DAO.ProductDAO::search -> log 테이블에서 상품 이름과 검색 옵션으로 이미 크롤링 된 상품인지 검사
        // log 테이블에서 정보가 존재할 시에는 1을 반환 후 com.dev.DAO.ProductDAO::load 실행
        // log 테이블에서 정보가 존재하지 않을 시에는 0을 반환 후 com.dev.VO.Pool::Pooling -> 크롤링 쓰레드 실행
        // 크롤링 실행 후 정보 추출 후 com.dev.DAO.ProductDAO::insert 실행
        // +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+

        if (Service.getInstance().search(productName, searchOption) == 1) {
            behaviors = Service.getInstance().load(productName, searchOption);
        } else {
            Pool.getInstance().pooling(new String[]{ String.join("+", productName.split(" ")), searchOption });

            behaviors = Pool.getInstance().getBehavior();

            Service.getInstance().insert(behaviors);
        }

        request.setAttribute("urls", Parser.getUrlsHashMap());
        request.setAttribute("productName", productName);
        request.setAttribute("searchOption", searchOption);
        request.setAttribute("behaviors", behaviors);

        HttpUtil.forward(request, response, "./result/result.jsp");
    }
}
