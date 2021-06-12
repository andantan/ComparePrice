package com.dev.VO;

import java.util.HashMap;

public interface Behavior {

    // +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
    // 각각의 Behavior은 Parser을 갖고 있음
    // CoupangBehavior ---(실행)--> CoupangParser ---(반환)--> CoupangBehavior
    // GmarketBehavior ---(실행)--> GmarketParser ---(반환)--> GmarketBehavior
    // StreetBehavior ---(실행)--> StreetParser ---(반환)--> StreetBehavior
    //
    // 모든 Behavior(CoupangBehavior, GmarketBehavior, StreetBehavior)는 call 메소드 실행 시
    // Parser 객체를 상속받은 각각의 Parser(CoupangParser, GmarketParser, StreetParser)을 실행함
    // +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+

    // 각 쇼핑몰의 상품 추출 갯수
    int PRODUCT_NEEDS = 10;

    // Behavior를 구현한 각각의 객체에 상품 이름과 검색 옵션에 맞는 파라미터값을 설정
    void initialize(String[] set);

    // 크롤링 실행
    void behave();

    void print();

    // Jsoup 크롤링 및 Selenium 로딩 시간 계산
    void setTime(long time);

    // 데이터 로드시 각각의 쇼핑몰 Behavior 리스트에 세팅
    void setList(HashMap<Integer, HashMap<String, String>> list);

    // 데이터 로드 후 세팅되거나 크롤링 완료후 세팅된 리스트 반환
    HashMap<Integer, HashMap<String, String>> getList();

    // Jsoup 및 Selenium 실행
    default void call(String[] set) {
        long beforeTime = System.currentTimeMillis();

        initialize(set);
        behave();

        long afterTime = System.currentTimeMillis();

        setTime(afterTime - beforeTime);
    }
}
