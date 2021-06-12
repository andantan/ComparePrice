package com.dev.VO.Coupang;

import com.dev.VO.Exception.InvalidFlagValueException;
import com.dev.VO.Parser;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.HashMap;

public class CoupangParser extends Parser {
    private static final CoupangParser COUPANG_PARSER = new CoupangParser();
    private static final int PRODUCT_NEEDS = 10;

    private CoupangParser() { }

    public static CoupangParser getInstance() { return COUPANG_PARSER; }

    @Override
    protected Document getDocument(String keyword, String sorter, String webPage) {
        Document document = null;

        try {
            document = super.getDocument(keyword, sorter, webPage);
        } catch (InvalidFlagValueException ERO) {
            System.out.println("[TermProjectPrototype.Crawler._11_st.CoupangParser::getDocument]: " + ERO.getMessage());
            ERO.printStackTrace();
        }

        return document;
    }

    protected ArrayList<Element> getSearchProduct(Document document) {
        Elements elements = document.getElementsByClass("search-product");
        ArrayList<Element> products = new ArrayList<>();

        for (int i = 0; i < PRODUCT_NEEDS; i++)
            products.add(elements.get(i));

        return products;
    }

    protected HashMap<String, String> getProductInfo(Element element, int size) {
        HashMap<String, String> productInfo = new HashMap<>();
        String productRatingStar = "null";
        String productRatingCount = "(0)";

        if (element.getElementsByClass("other-info").size() != 0) {
            productRatingStar = Integer.toString((int) (Double.parseDouble(element.getElementsByClass("rating-star").get(0).getElementsByTag("em").get(0).text()) * 20));
            productRatingCount = element.getElementsByClass("rating-star").get(0).getElementsByClass("rating-total-count").get(0).text();
        }

        productInfo.put("productName", element.getElementsByClass("name").get(0).text());
        productInfo.put("productHref", String.format("https://www.coupang.com%s", element.getElementsByTag("a").get(0).attr("href")));
        productInfo.put("productPrice", element.getElementsByClass("price-value").get(0).text());
        productInfo.put("productRatingStar", String.format("%s%%", productRatingStar));
        productInfo.put("productRatingCount", productRatingCount);

        if (size < 8)
            productInfo.put("productImageSrc", element.getElementsByClass("search-product-wrap-img").get(0).attr("src"));
        else
            productInfo.put("productImageSrc", element.getElementsByClass("search-product-wrap-img").get(0).attr("data-img-src"));

        return productInfo;
    }
}
