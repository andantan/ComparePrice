package com.dev.VO._11_st;

import com.dev.VO.Exception.InvalidFlagValueException;
import com.dev.VO.Parser;
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
            System.out.println("[com.dev.VO._11_st.StreetParser::getDocument]: " + ERO.getMessage());
            ERO.printStackTrace();
        }

        return document;
    }

    // TODO: 랭킹순fix
    protected ArrayList<Element> getSearchProduct(Document document) {
        Elements sections = document.getElementsByClass("search_section");
        Elements elements;
        ArrayList<Element> products = new ArrayList<>();

        for (Element section: sections) {
            if (section.attr("data-log-actionid-area").equals("common")) {
                elements = section.getElementsByClass("c_listing c_listing_view_type_list").get(0).getElementsByTag("li");

                for (int i = 0; i < PRODUCT_NEEDS; i++)
                    products.add(elements.get(i));

                break;
            }
        }

        return products;
    }

    protected HashMap<String, String> getProductInfo(Element element) {
        HashMap<String, String> productInfo = new HashMap<>();
        Elements meta = element.getElementsByClass("c_prd_meta");
        Elements metaS1;
        Elements metaS2;
        String[] tmp;
        String productRatingStar = "null%";
        String productRatingCount = "(0)";

        if (meta.size() != 0) {
            metaS1 = meta.get(0).getElementsByClass("c_seller_grade_size_1");
            metaS2 = meta.get(0).getElementsByClass("c_review");

            if (metaS1.size() != 0) {
                tmp = metaS1.get(0).getElementsByTag("span").get(0).text().split(" ");

                productRatingStar = tmp[tmp.length - 1];
                productRatingStar = Integer.toString((int) (Double.parseDouble(productRatingStar.substring(0, productRatingStar.length() - 1)) * 20));
                productRatingStar += "%";
            }

            if (metaS2.size() != 0)
                productRatingCount = "(" + metaS2.get(0).getElementsByTag("em").get(0).text() + ")";
        }

        productInfo.put("productName", element.getElementsByClass("c_prd_name c_prd_name_row_1").get(0).text());
        productInfo.put("productHref", element.getElementsByClass("c_prd_name c_prd_name_row_1").get(0).getElementsByTag("a").get(0).attr("href"));
        productInfo.put("productPrice", element.getElementsByClass("c_prd_price").get(0).getElementsByClass("value").get(0).text());
        productInfo.put("productImageSrc", element.getElementsByClass("c_prd_thumb").get(0).getElementsByTag("img").attr("src"));
        productInfo.put("productRatingStar", productRatingStar);
        productInfo.put("productRatingCount", productRatingCount);

        return productInfo;
    }
}
