<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<html>
<head>
    <meta charset="utf-8">
    <title>Hub</title>
    <style media="screen">
        * {
            margin: 0;
            padding: 0;
            font-family: sans-serif;
            box-sizing: border-box;
        }

        body {
            height: 100vh;
            display: flex;
            justify-content: center;
            align-items: center;
            background: linear-gradient(-25deg, #7797D0 50%, #F8C6BD 50%);
        }

        .logo {
            position: absolute;
            left: 37.5%;
            top: 23%;
            font-size: 50px;
            color: white;
        }

        footer > .description {
            padding-left: 510px;
            position: absolute;
            top: 90%;
            font-size: 20px;
            font-weight: bold;
        }

        .box {
            position: absolute;
            left: 35%;
            top: 36%;
            width: 500px;
            height: 40px;
            background-color: white;
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
            margin-top: 20px;
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
            width: 105px;
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
            height: 30px;
            width: 400px;
            border: none;
            outline: none;
            font-size: 15px;
        }
    </style>
    <link rel="stylesheet" href="https://pro.fontawesome.com/releases/v5.10.0/css/all.css"
          integrity="sha384-AYmEC3Yw5cVb3ZcuHtOA93w35dYTsvhLPVnYs9eStHfGJvOvKxVfELGroGkvsg+p"
          crossorigin="anonymous"/>
</head>
<body>
<main class="contents">
    <section class="main-content-logo">
        <div class="logo">
            <h3>Compare Price</h3>
        </div>
    </section>
    <section class="main-content-box">
        <div class="box">
            <form class="action-box" action="performance.do" method="post">
                <i class="fas fa-search" id="search-icon"></i>
                <label><input type="text" id="search-product" name="product" placeholder="상품 검색"></label>
                <i class="fas fa-eraser" id="search-clear" onclick="document.getElementById('search-product').value = ''"></i>
                <input type="submit" id="submit-product" style="visibility: hidden;">
                <div class="search-option">
                    <input class="search-option-radio" type="radio" value="판매량순" name="option" id="option1" checked="checked"><label class="search-option-label" for="option1">판매량순</label>
                    <input class="search-option-radio" type="radio" value="최신순" name="option" id="option2"><label class="search-option-label" for="option2">최신순</label>
                    <input class="search-option-radio" type="radio" value="높은 가격순" name="option" id="option3"><label class="search-option-label" for="option3">높은 가격순</label>
                    <input class="search-option-radio" type="radio" value="낮은 가격순" name="option" id="option4"><label class="search-option-label" for="option4">낮은 가격순</label>
                </div>
            </form>
        </div>
    </section>
</main>

<footer class="hub-description">
    <section class="description">
        <p>Powered by Selenium & Jsoup</p>
        <p>Optimized by Jsoup Parser</p>
    </section>
</footer>
</body>
</html>