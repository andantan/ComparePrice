package com.dev.Pool.Gmarket;

import com.dev.Pool.Behavior;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.util.ArrayList;
import java.util.HashMap;

public final class GmarketBehavior implements Behavior {
    private final HashMap<String, String> sorter = new HashMap<>();
    private final GmarketParser gmarketParser = GmarketParser.getInstance();
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
        sorter.put("판매량순", "&s=8");
        sorter.put("최신순", "&s=3");
        sorter.put("높은 가격순", "&s=2");
        sorter.put("낮은 가격순", "&s=1");

        searchProduct = searchSet[0];
        searchOption = searchSet[1];
    }

    @Override
    public synchronized void behave() {
        Document document = gmarketParser.getDocument(searchProduct, sorter.get(searchOption), "gmarket");
        ArrayList<Element> searchProductList = gmarketParser.getSearchProduct(document);

        for (int i = 0; i < PRODUCT_NEEDS; i++)
            extractedProductInfo.put(i + 1, gmarketParser.getProductInfo(searchProductList.get(i)));
    }

    @Override
    public void print() {
        System.out.println("(GmarketBehavior finished) processing time: " + time + " second");
        System.out.println("Extracted product infomation list");
        System.out.printf("URL: https://browse.gmarket.co.kr/search?keyword=%s%s\n", searchProduct, sorter.get(searchOption));
        System.out.print("-------------------------------------------------------------------------------");
        System.out.println("-------------------------------------------------------------------------------");

        for (int i = 1; i < PRODUCT_NEEDS + 1; i++) {
            System.out.println("(" + i + ") Product name: " + extractedProductInfo.get(i).get("productName"));
            System.out.println("Product link: " + extractedProductInfo.get(i).get("productHref"));
            System.out.println("Product price: " + extractedProductInfo.get(i).get("productPrice") + "원");
            System.out.println("Product rating: " + extractedProductInfo.get(i).get("productRatingStar"));
            System.out.println("Product rating count: " + extractedProductInfo.get(i).get("productRatingCount"));
            System.out.println("Product image source: " + extractedProductInfo.get(i).get("productImageSrc"));
            System.out.print("-------------------------------------------------------------------------------");
            System.out.println("-------------------------------------------------------------------------------");
        }
    }
}
