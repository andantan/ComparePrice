<%@ page import="java.util.HashMap" %>
<%@ page import="java.util.List" %>
<%@ page import="com.dev.Pool.Behavior" %>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<html>
<head>
    <meta charset="utf-8">
    <title>결과</title>
    <link rel="stylesheet" href="https://pro.fontawesome.com/releases/v5.10.0/css/all.css"
          integrity="sha384-AYmEC3Yw5cVb3ZcuHtOA93w35dYTsvhLPVnYs9eStHfGJvOvKxVfELGroGkvsg+p"
          crossorigin="anonymous"/>
    <style>
        body {
            margin: 0;
            height: 100vh;
            background: white;
        }

        .main-row {
            display: flex;
        }

        header {
            width: 100%;
            height: 200px;
            display: flex;
            justify-content: center;
            align-items: center;
            border-bottom: 3px solid #F8C6BD;
        }

        .box {
            width: 500px;
            height: 40px;
            background-color: #D4A8A0;
            border-radius: 30px;
        }

        .action-box {
            height: 40px;
            position: static;
            align-items: center;
            padding-top: 5px;
            padding-bottom: 5px;
        }

        .search-option {
            display: inline-flex;
            overflow: hidden;
            border-radius: 15px;
            box-shadow: 0 0 5px rgba(0, 0, 0, 0.25);
        }

        .search-option-radio {
            display: none;
        }

        .search-option-label {
            text-align: center;
            width: 80px;
            padding: 8px 14px;
            font-size: 14px;
            font-family: sans-serif;
            background: #D4A8A0;
            cursor: pointer;
            transition: background 0.1s;
        }

        .search-option-label:not(:last-of-type) {
            border-right: 1px solid #006b56;
        }

        .search-option-radio:checked + .search-option-label {
            color: white;
            background: #0E5993;
        }

        #search-icon {
            font-size: 15px;
            padding-left: 17.5px;
            padding-right: 8px;
        }

        #search-clear {
            padding-left: 13px;
            cursor: pointer;
        }

        #search-product {
            color: black;
            font-weight: bold;
            background-color: #D4A8A0;
            height: 30px;
            width: 400px;
            border: none;
            outline: none;
            font-size: 15px;
        }

        #submit-product {
            visibility: hidden;
        }

        #search-product::placeholder {
            color: black;
            font-weight: bold;
            text-align: center;
        }

        .search-io {
            padding-left: 30px;
        }

        main > .main-row {
            width: 100%;
            height: 100%;
            justify-content: center;
        }

        .main-col {
            width: 32%;
        }

        .main-col-coupang .product-item {
            border-left: 2px solid #F8C6BD;
        }

        main > .main-col-names {
            width: 100%;
            display: flex;
            justify-content: center;
        }

        .main-col-name > a {
            text-decoration: none;
            color: blue;
            font-weight: bold;
            font-size: 17px;
        }

        .main-col-names > .main-col-name {
            height: 35px;
            width: 32%;
            display: flex;
            justify-content: center;
            align-items: center;
            border-bottom: 2px solid #F8C6BD;
            border-right: 2px solid #F8C6BD;
        }

        .main-col-name:first-of-type {
            border-left: 2px solid #F8C6BD;
        }

        .product-item-image > img {
            height: 230px;
            width: 230px;
        }

        .main-col > .product-item {
            border-right: 2px solid #F8C6BD;
            border-bottom: 2px solid #F8C6BD;
        }


        .main-col > .product-item {
            height: 250px;
            display: flex;
        }

        .product-item > .product-item-description {
            display: inline-block;
        }

        .product-item-link {
            height: 180px;
            display: flex;
            align-items: center;
        }

        .product-item-image {
            padding-top: 10px;
            padding-left: 10px;
            padding-right: 10px;
        }

        .product-item-price {
            font-weight: bold;
            font-size: 22px;
            color: #A1260F;
            padding-bottom: 7px;
        }

        .product-item-title {
            height: 110px;
            font-weight: bold;
            font-size: 18px;
            display: flex;
            align-items: center;
            margin-top: 18px;
        }

        .product-item-link > a {
            text-decoration: none;
            color: black;
        }

        .star-rating {
            width:205px;
            margin-top: 10px;
        }

        .star-rating, .star-rating span {
            display:inline-block;
            height:39px;
            overflow:hidden;
            background:url("images/star.png")no-repeat;
        }

        .star-rating span {
            background-position:left bottom;
            line-height:0;
            vertical-align:top;
        }

        .product-item-star {
            display: inline-block;
        }

        .star-rating-count {
            font-size: 20px;
            font-weight: bold;
        }
    </style>
</head>
<body>
    <%
        response.setContentType("text/html;charset=UTF-8");

        HashMap<String, String> urls = (HashMap<String, String>) request.getAttribute("urls");
    %>
    <header>
        <div class="box">
            <form class="action-box" action="performance.do" method="post">
                <i class="fas fa-search" id="search-icon"></i>
                <label><input type="text" id="search-product" name="product" placeholder="${productName}"></label>
                <i class="fas fa-eraser" id="search-clear" onclick="document.getElementById('search-product').value = ''"></i>
                <input type="submit" id="submit-product">
                <div class="search-io">
                    <div class="search-option">
                        <input class="search-option-radio" type="radio" value="판매량순" name="option" id="option1" checked="checked"><label class="search-option-label" for="option1">판매량순</label>
                        <input class="search-option-radio" type="radio" value="판매량순" name="option" id="option2"><label class="search-option-label" for="option2">최신순</label>
                        <input class="search-option-radio" type="radio" value="판매량순" name="option" id="option3"><label class="search-option-label" for="option3">높은 가격순</label>
                        <input class="search-option-radio" type="radio" value="판매량순" name="option" id="option4"><label class="search-option-label" for="option4">낮은 가격순</label>
                    </div>
                </div>
            </form>
        </div>
    </header>
    <main class="main">
        <div class="main-col-names">
            <div class="main-col-name">
                <a target="_blank" rel="noopener noreferrer" href=<%= urls.get("coupang") %>>쿠팡 검색 결과 ▼</a>
            </div>
            <div class="main-col-name">
                <a target="_blank" rel="noopener noreferrer" href=<%= urls.get("gmarket") %>>G마켓 검색 결과 ▼</a>
            </div>
            <div class="main-col-name">
                <a target="_blank" rel="noopener noreferrer" href=<%= urls.get("street") %>>11번가 검색 결과 ▼</a>
            </div>
        </div>
        <div class="main-row">
            <%
                List<Behavior> behaviors = (List<Behavior>) request.getAttribute("behaviors");

                for (int i = 0; i < behaviors.size(); i++) {
                    HashMap<Integer, HashMap<String, String>> products = behaviors.get(i).getList();

                    if (i == 0) out.print("<div class=\"main-col main-col-coupang\">");
                    else if (i == 1) out.print("<div class=\"main-col main-col-gmarket\">");
                    else out.print("<div class=\"main-col main-col-street\">");

                    for (int j = 1; j< 11; j++) {
                        HashMap<String, String> product = products.get(j);

                        out.print("<div class=\"product-item\"><div class=\"product-item-image\">");

                        if (i == 0 || i == 1) out.print(String.format("<img src=https://%s></div>", product.get("productImageSrc")));
                        else out.print(String.format("<img src=\"%s\"></div>", product.get("productImageSrc")));

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
