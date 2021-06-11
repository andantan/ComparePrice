package com.dev.Pool._11_st;

import com.dev.Pool.Behavior;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.util.ArrayList;
import java.util.HashMap;

public final class StreetBehavior implements Behavior {
    private final HashMap<String, String> sorter = new HashMap<>();
    private final StreetParser streetParser = StreetParser.getStreetParser();
    private HashMap<Integer, HashMap<String, String>> extractedProductInfo = new HashMap<>();
    private String searchProduct;
    private String searchOption;
    private double time;

    @Override
    public synchronized void setTime(long time) {
        this.time = (double) time / 1000;
    }

    @Override
    public synchronized void setList(HashMap<Integer, HashMap<String, String>> list){
        this.extractedProductInfo = list;
    }

    @Override
    public synchronized HashMap<Integer, HashMap<String, String>> getList() {
        return extractedProductInfo;
    }

    @Override
    public synchronized void initialize(String[] searchSet) {
        sorter.put("판매량순", "#sortCd%%A%%누적%20판매순%%4");
        sorter.put("최신순", "#sortCd%%N%%최신순%%10");
        sorter.put("높은 가격순", "#sortCd%%H%%높은%20가격순%%6");
        sorter.put("낮은 가격순", "#sortCd%%L%%낮은%20가격순%%8");

        searchProduct = searchSet[0];
        searchOption = searchSet[1];
    }

    @Override
    public synchronized void behave() {
        Document document = streetParser.getDocument(searchProduct, sorter.get(searchOption), "street");
        ArrayList<Element> searchProductList = streetParser.getSearchProduct(document);

        for (int i = 0; i < PRODUCT_NEEDS; i++)
            extractedProductInfo.put(i + 1, streetParser.getProductInfo(searchProductList.get(i)));
    }

    @Override
    public void print() {
        System.out.println("(StreetBehavior finished) processing time: " + time + " second");
        System.out.println("Extracted product infomation list");
        System.out.printf("URL: https://search.11st.co.kr/Search.tmall?kwd=%s%s\n", searchProduct, sorter.get(searchOption));
        System.out.print("-------------------------------------------------------------------------------");
        System.out.println("-------------------------------------------------------------------------------");

        for (int i = 1; i < PRODUCT_NEEDS + 1; i++) {
            System.out.println("(" + i + ") Product name: " + extractedProductInfo.get(i).get("productName"));
            System.out.println("Product link: " + extractedProductInfo.get(i).get("productHref"));
            System.out.println("Product price: " + extractedProductInfo.get(i).get("productPrice") + "원");
            System.out.println("Product image source: " + extractedProductInfo.get(i).get("productImageSrc"));
            System.out.println("Product rating: " + extractedProductInfo.get(i) .get("productRatingStar"));
            System.out.println("Product rating count: " + extractedProductInfo.get(i).get("productRatingCount"));
            System.out.print("-------------------------------------------------------------------------------");
            System.out.println("-------------------------------------------------------------------------------");
        }
    }
}
