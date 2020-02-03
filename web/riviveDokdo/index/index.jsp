<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<% request.setCharacterEncoding("UTF-8"); %>

<!DOCTYPE html>
<html>
<head lang="ko">
    <title>독도의 역사 독도의 진실</title>
    <link rel="stylesheet" href="../../css/bootstrap.css">
    <link rel="stylesheet" href="../../css/lightbox.css">
    <style>
        .btn-outline-light {
            width: 200px; height: 200px;
        }
    </style>
</head>

<body>
<%@include file="../layout/head.jsp"%>
<div class="container">
    <div class="row" style="margin-top: 3%;">
        <div id="carouselExampleIndicators" class="carousel slide col-md" data-ride="carousel">
            <ol class="carousel-indicators">
                <li data-target="#carouselExampleIndicators" data-slide-to="0" class="active"></li>
                <li data-target="#carouselExampleIndicators" data-slide-to="1"></li>
                <li data-target="#carouselExampleIndicators" data-slide-to="2"></li>
            </ol>
            <div class="carousel-inner">
                <div class="carousel-item active">
                    <a href="https://www.youtube.com/embed/r3_d0pE6WlQ?autoPlay=True" class="html5lightbox" data-width="960" data-height="600">
                        <img src="https://drive.google.com/uc?export=view&id=10aRXcT8Ry0wsnzbW4xw6dwzsbMX3vttz" class="w-100 h-100">
                    </a>
                </div>
                <div class="carousel-item">
                    <a href="https://player.vimeo.com/video/116558062" class="html5lightbox" data-width="960" data-height="600" data-autoplayvideo="True">
                        <img src="https://drive.google.com/uc?export=view&id=1CiGMjwMCneQY2sUIMbZayddSqVBrGwpY" class="w-100 h-100">
                    </a>
                </div>
            </div>
            <a class="carousel-control-prev" href="#carouselExampleIndicators" role="button" data-slide="prev">
                <span class="carousel-control-prev-icon" aria-hidden="true"></span>
                <span class="sr-only">Previous</span>
            </a>
            <a class="carousel-control-next" href="#carouselExampleIndicators" role="button" data-slide="next">
                <span class="carousel-control-next-icon" aria-hidden="true"></span>
                <span class="sr-only">Next</span>
            </a>
        </div>
    </div>
    <div class="row card-body bg-dark h-auto" style="margin-top: 2%; border-radius: 5px; color: white;">
        <div class="col-12">
            <br>
            <div class="row">
                <div class="col-2">
                    <a href="https://drive.google.com/uc?export=view&id=1RXT_6fpruI1deh8XZzSxVFGQvEFUNh3_" data-lightbox="item-1">
                        <img src="https://drive.google.com/uc?export=view&id=1RXT_6fpruI1deh8XZzSxVFGQvEFUNh3_" class="w-100 h-100" style="border-radius: 5px">
                    </a>
                </div>
                <div class="col-10">
                    <p>
                        한국의 주장에서 본 기록으로는 한국영토는 6세기 통일신라시대, 그리고 1900년 대한제국 칙령41호에 의해 공식적으로 이 섬들이 오늘날 울릉군으로 편입되었음을 보여주고 있다.<br>
                        (한국의 1900년 독도관련 칙령 41호.) 일본 측은 17 세기 기록과 “무주지선점론”에 의한 1905년 일본의 점령을 근거로 영유권을 주장하고 있다.<br>
                        (일본의 1905년 불법적 독도 편입) 오늘날,<br>
                        한국은 이 섬들을 경상북도 울릉군의 일부로 보고 있는데 반해 일본은 시마네현, 오키구, 오키섬의 일부로 보고 있다.<br>
                    </p>
                </div>
            </div>
            <div class="row" style="margin-top: 20px">
                <div class="col-2">
                    <a href="https://drive.google.com/uc?export=view&id=1eQrtocWGpWlxBApuylcYFZWaJNCeGJRa" data-lightbox="item-1">
                        <img src="https://drive.google.com/uc?export=view&id=1eQrtocWGpWlxBApuylcYFZWaJNCeGJRa" class="w-100 h-100" style="border-radius: 5px">
                    </a>
                </div>
                <div class="col-10">
                    <p>
                        이 섬 자체는 거주하기가 어렵지만, 주변 배타적 경제수역에는 풍부한 어족자원과 천연가스가 매장되어 있고 예상매장량은 알려지지 않고 있다.<br>
                        다양한 어족들과 해초들, 갈조, 나새류, 그리고 조개들이 독도 주변에 서식하고 있다.<br>
                        주된 어업 어종으로는 오징어, 명태, 대구 그리고 문어가 있다. 해초류는 102종이 있지만 대체로 경제적 가치는 없다.<br>
                        이 지역은 강치(바다사자)의 가장 큰 서식지였으며 메이지시대 말에는 전복도 많이 잡혔다(1868-1912).<br>
                        1950년대까지, 강치는 독도 자위수호대에 의하여 관측되기도 했다.<br>
                    </p>
                </div>
            </div>
        </div>
    </div>
    <div class="row" style="margin: 50px 0 50px 0">
        <div class="col-md text-center">
            <button type="button" class="btn btn-outline-light" style="margin-right: 10px; font-size: 50px" id="goAbout">about</button>
            <button type="button" class="btn btn-outline-light" style="margin-right: 10px; font-size: 50px" id="goHist1">독도의<br>역사1</button>
            <button type="button" class="btn btn-outline-light" style="margin-right: 10px; font-size: 50px" id="goHist2">독도의<br>역사2</button>
            <button type="button" class="btn btn-outline-light" style="margin-right: 10px; font-size: 50px" id="goGal">갤러리</button>
            <button type="button" class="btn btn-outline-light" style="margin-right: 10px; font-size: 50px" id="goIntro">소개</button>
        </div>
    </div>
</div>
<%@include file="../layout/footer.jsp"%>
</header>
</body>
<script type="text/javascript" src="../../js/jquery-3.4.1.js"></script>
<script type="text/javascript" src="../../js/bootstrap.js"></script>
<script src="../../js/lightbox.js"></script>
<script src="../../js/html5lightbox.js"></script>
<script src="https://kit.fontawesome.com/3197007a76.js" crossorigin="anonymous"></script>
<script>
    //하단 버튼 클릭시
    $(function () {
        $('#goAbout').on('click', function (e) {
            location.href = '../about/about.jsp';
        });
    });
    $(function () {
        $('#goHist1').on('click', function (e) {
            location.href = '../hist1/dkdhist1.jsp';
        });
    });
    $(function () {
        $('#goHist2').on('click', function (e) {
            location.href = '../dkdhist2/dkdhist2.jsp';
        });
    });
    $(function () {
        $('#goGal').on('click', function (e) {
            location.href = '../gallery/gallery.jsp';
        });
    });
    $(function () {
        $('#goIntro').on('click', function (e) {
            location.href = '../intro/intro.jsp';
        });
    });
    // git에서 고쳐봄
</script>
</html>
