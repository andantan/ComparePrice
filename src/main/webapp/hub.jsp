<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<html>
<head>
    <meta charset="utf-8">
    <title>CompareProducts</title>
    <link rel="stylesheet" href="./css/hubStyle.css">
    <link rel="stylesheet" href="https://pro.fontawesome.com/releases/v5.10.0/css/all.css"
          integrity="sha384-AYmEC3Yw5cVb3ZcuHtOA93w35dYTsvhLPVnYs9eStHfGJvOvKxVfELGroGkvsg+p"
          crossorigin="anonymous"/>
    <script src="https://code.jquery.com/jquery-3.1.0.js"></script>
    <script>
        const loading = function () {
            $('#loader').css({
                borderWidth: 4,
                borderStyle: 'solid',
                borderColor: '#f3f3f3',
                borderTopWidth: 4,
                borderTopStyle: 'solid',
                borderTopColor: '#3498db',
            });

            $('#loader-text').text('로딩 중입니다.');

            setTimeout(function () {
                $('#loader-text').text('데이터를 모으는 중입니다. 몇 초 내에 완료됩니다.');

                setTimeout(function() {
                    $('#loader-text').text('데이터를 정보화하는 중입니다.')
                }, 6500)
            }, 1000)
        };
    </script>
</head>
<body>
<main class="contents">
    <section class="main-content-logo">
        <div class="logo">
            <h3>CompareProducts</h3>
        </div>
    </section>
    <section class="main-content-box">
        <div class="box">
            <form class="action-box" action="performance.do" method="post">
                <i class="fas fa-search" id="search-icon"></i>
                <label><input type="text" id="search-product" name="product" placeholder="상품 검색"></label>
                <i class="fas fa-eraser" id="search-clear" onclick="document.getElementById('search-product').value = ''"></i>
                <input type="submit" id="submit-product" style="visibility: hidden;" onclick="loading()">
                <div class="search-option">
                    <input class="search-option-radio" type="radio" value="판매량순" name="option" id="option1" checked="checked"><label class="search-option-label" for="option1">판매량순</label>
                    <input class="search-option-radio" type="radio" value="최신순" name="option" id="option2"><label class="search-option-label" for="option2">최신순</label>
                    <input class="search-option-radio" type="radio" value="높은 가격순" name="option" id="option3"><label class="search-option-label" for="option3">높은 가격순</label>
                    <input class="search-option-radio" type="radio" value="낮은 가격순" name="option" id="option4"><label class="search-option-label" for="option4">낮은 가격순</label>
                </div>
            </form>
        </div>
        <div id="loader"></div>
        <div id="loader-text" style="padding-top: 10px;"></div>
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