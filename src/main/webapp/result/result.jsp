<%@ page import="java.util.HashMap" %>
<%@ page import="java.util.List" %>
<%@ page import="com.dev.VO.Behavior" %>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<html>
<head>
    <meta charset="utf-8">
    <title>CompareProducts</title>
    <link rel="stylesheet" href="https://pro.fontawesome.com/releases/v5.10.0/css/all.css"
          integrity="sha384-AYmEC3Yw5cVb3ZcuHtOA93w35dYTsvhLPVnYs9eStHfGJvOvKxVfELGroGkvsg+p"
          crossorigin="anonymous"/>
    <link rel="stylesheet" href="../css/resultStyle.css">
</head>
<body>

    <%
        response.setContentType("text/html;charset=UTF-8");

        String[] searchOptions = new String[] { "랭킹순", "판매량순", "최신순", "높은 가격순", "낮은 가격순" };
        HashMap<String, String> urls = (HashMap<String, String>) request.getAttribute("urls");
        List<Behavior> behaviors = (List<Behavior>) request.getAttribute("behaviors");
        String searchOption = (String) request.getAttribute("searchOption");
    %>

    <div class="logo">
        <h3><a href="${pageContext.request.contextPath}../hub.jsp">CompareProducts</a></h3>
    </div>
    <header>
        <div class="box">
            <form class="action-box" action="performance.do" method="post">
                <i class="fas fa-search" id="search-icon"></i>
                <label><input type="text" id="search-product" name="product" value="${productName}" style="text-align: center;"></label>
                <i class="fas fa-eraser" id="search-clear" onclick="document.getElementById('search-product').value = ''"></i>
                <input type="submit" id="submit-product">
                <div class="search-io">
                    <div class="search-option">
                        <%
                            for (int i = 0; i < 5; i++) {
                                if (searchOption.equals(searchOptions[i]))
                                    out.print(String.format("<input class=\"search-option-radio\" type=\"radio\" value=\"%s\" id=\"option%d\" name=\"option\" checked=\"checked\">", searchOptions[i], i + 1));
                                else
                                    out.print(String.format("<input class=\"search-option-radio\" type=\"radio\" value=\"%s\" id=\"option%d\" name=\"option\">", searchOptions[i], i + 1));

                                out.print(String.format("<label class=\"search-option-label\" for=\"option%d\">%s</label>", i + 1, searchOptions[i]));
                            }
                        %>
                    </div>
                </div>
            </form>
        </div>
    </header>
    <main class="main">
        <div class="main-col-names">
            <div class="main-col-name">
                <a target="_blank" rel="noopener noreferrer" style="color: #51A4D9;" href=<%= urls.get("coupang") %>>쿠팡 검색 결과 ▼</a>
            </div>
            <div class="main-col-name">
                <a target="_blank" rel="noopener noreferrer" style="color: #00C51E;" href=<%= urls.get("gmarket") %>>G마켓 검색 결과 ▼</a>
            </div>
            <div class="main-col-name">
                <a target="_blank" rel="noopener noreferrer" style="color: #F43142;" href=<%= urls.get("street") %>>11번가 검색 결과 ▼</a>
            </div>
        </div>
        <div class="main-row">
            <%
                for (int i = 0; i < behaviors.size(); i++) {
                    HashMap<Integer, HashMap<String, String>> products = behaviors.get(i).getList();

                    if (i == 0) out.print("<div class=\"main-col main-col-coupang\">");
                    else if (i == 1) out.print("<div class=\"main-col main-col-gmarket\">");
                    else out.print("<div class=\"main-col main-col-street\">");

                    for (int j = 1; j< 11; j++) {
                        HashMap<String, String> product = products.get(j);

                        out.print("<div class=\"product-item\"><div class=\"product-item-image\">");

                        if (i == 2) out.print(String.format("<img src=\"%s\"></div>", product.get("productImageSrc")));
                        else out.print(String.format("<img src=https://%s></div>", product.get("productImageSrc")));

                        out.print("<div class=\"product-item-link\">");
                        out.print(String.format("<a target=\"_blank\" rel=\"noopener noreferrer\" href=\"%s\">", product.get("productHref")));
                        out.print("<div class=\"product-item-description\">");
                        out.print(String.format("<div class=\"product-item-title\">%s</div>", product.get("productName")));
                        out.print(String.format("<div class=\"product-item-price\">%s원</div>", product.get("productPrice")));
                        out.print(String.format("<div class=\"product-item-star\"><span class=\"star-rating\"><span style=\"width: %s;\"></span></span>", product.get("productRatingStar")));
                        out.print(String.format("<span class=\"star-rating-count\">%s</span>", product.get("productRatingCount")));
                        out.print("</div></div></a></div></div>");
                    }

                    out.print("</div>");
                }
            %>
        </div>
    </main>
</body>
</html>
