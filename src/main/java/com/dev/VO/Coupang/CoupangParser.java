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

    // TODO: image src
    @Override
    protected Document getDocument(String keyword, String sorter, String webPage) {
        Document document = null;

        try {
            document = super.getDocument(keyword, sorter, webPage);
        } catch (InvalidFlagValueException ERO) {
            System.out.println("[com.dev.VO.Coupang.CoupangParser::getDocument]: " + ERO.getMessage());
            ERO.printStackTrace();
        }

        return document;
    }

    // TODO: I HAVE NO IDEA THAT WHY IGNORE SOME PRODUCTS....
    protected ArrayList<Element> getSearchProduct(Document document) {
        Elements elements = document.getElementById("productList").getElementsByClass("search-product");
        ArrayList<Element> products = new ArrayList<>();
        Element element;
        int size = PRODUCT_NEEDS;

        for (int i = 0; i < size; i++) {
            element = elements.get(i);

            if (element.attr("class").equals("search-product ")) products.add(element);
            else size++;
        }

        return products;
    }

    protected HashMap<String, String> getProductInfo(Element element, int size) {
        HashMap<String, String> productInfo = new HashMap<>();
        Elements meta = element.getElementsByClass("other-info");
        Elements metaS1;
        Elements metaS2;
        String productRatingStar = "null%";
        String productRatingCount = "(0)";

        if (meta.size() != 0) {
            metaS1 = meta.get(0).getElementsByClass("rating-star");
            metaS2 = meta.get(0).getElementsByClass("rating-total-count");

            if (metaS1.size() != 0) {
                productRatingStar = Integer.toString((int) (Double.parseDouble(metaS1.get(0).getElementsByTag("em").get(0).text()) * 20));
                productRatingStar += "%";
            }

            if (metaS2.size() != 0)
                productRatingCount = metaS2.get(0).getElementsByClass("rating-total-count").get(0).text();
        }

        productInfo.put("productName", element.getElementsByClass("name").get(0).text());
        productInfo.put("productHref", String.format("https://www.coupang.com%s", element.getElementsByTag("a").get(0).attr("href")));
        productInfo.put("productPrice", element.getElementsByClass("price-value").get(0).text());
        productInfo.put("productRatingStar", productRatingStar);
        productInfo.put("productRatingCount", productRatingCount);

        if (size < 8)
            productInfo.put("productImageSrc", element.getElementsByClass("search-product-wrap-img").get(0).attr("src"));
        else
            productInfo.put("productImageSrc", element.getElementsByClass("search-product-wrap-img").get(0).attr("data-img-src"));

        return productInfo;
    }
}
