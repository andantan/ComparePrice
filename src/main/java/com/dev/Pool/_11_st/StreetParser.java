package com.dev.Pool._11_st;

import com.dev.Pool.Exception.InvalidFlagValueException;
import com.dev.Pool.Parser;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.HashMap;

public class StreetParser extends Parser {
    private static final StreetParser STREET_PARSER = new StreetParser();
    private static final int PRODUCT_NEEDS = 10;

    private StreetParser() { }

    public static StreetParser getStreetParser() { return STREET_PARSER; }

    @Override
    protected Document getDocument(String keyword, String sorter, String webPage) {
        Document document = null;

        try {
            document = super.getDocument(keyword, sorter, webPage);
        } catch (InvalidFlagValueException ERO) {
            System.out.println("[TermProjectPrototype.Crawler._11_st.StreetParser::getDocument]: " + ERO.getMessage());
            ERO.printStackTrace();
        }

        return document;
    }

    protected ArrayList<Element> getSearchProduct(Document document) {
        Elements elements = document.getElementsByClass("c_listing c_listing_view_type_list").get(0).getElementsByTag("li");
        ArrayList<Element> products = new ArrayList<>();

        for (int i = 0; i < PRODUCT_NEEDS; i++) {
            products.add(elements.get(i));
        }

        return products;
    }

    protected HashMap<String, String> getProductInfo(Element element) {
        HashMap<String, String> productInfo = new HashMap<>();
        String productRatingStar = "null";
        String productRatingCount = "0";

        if (element.getElementsByClass("c_prd_meta").size() != 0) {
            productRatingStar = element.getElementsByClass("c_prd_meta").get(0).getElementsByTag("span").get(0).text().split(" ")[4];
            productRatingStar = Integer.toString((int) (Double.parseDouble(productRatingStar.substring(0, productRatingStar.length() - 1)) * 20));
            productRatingCount = element.getElementsByClass("c_prd_meta").get(0).getElementsByTag("em").get(0).text();
        }

        productInfo.put("productName", element.getElementsByClass("c_prd_name c_prd_name_row_1").get(0).text());
        productInfo.put("productHref", element.getElementsByClass("c_prd_name c_prd_name_row_1").get(0).getElementsByTag("a").get(0).attr("href"));
        productInfo.put("productPrice", element.getElementsByClass("c_prd_price").get(0).getElementsByClass("value").get(0).text());
        productInfo.put("productImageSrc", element.getElementsByClass("c_prd_thumb").get(0).getElementsByTag("img").attr("src"));
        productInfo.put("productRatingStar", String.format("%s%%", productRatingStar));
        productInfo.put("productRatingCount", String.format("(%s)", productRatingCount));

        return productInfo;
    }
}
