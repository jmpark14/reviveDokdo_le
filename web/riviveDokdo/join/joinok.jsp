<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="utf-8" %>
<% request.setCharacterEncoding("UTF-8"); %>

<!DOCTYPE html>
<html>
<head lang="ko">
    <title>독도의 역사 독도의 진실</title>
    <link rel="stylesheet" href="../../css/bootstrap.css">

    <style>
        .container {color: white}
        .my-4{background-color: silver}
    </style>
</head>
<body>
    <%@include file="../layout/head.jsp"%>
    <div class="container">
        <div id="main">
            <div style="padding: 30px 0;"><i class="fa fa-users fa-2x">회원가입 완료</i></div>

            <nav aria-label="breadcrumb">
                <ul class="breadcrumb bg-dark">
                    <li class="breadcrumb-item active">
                        <span class="btn btn-outline-light disabled" style="padding: 5px">이용약관</span>
                    </li>
                    <li class="breadcrumb-item">
                        <span class="btn btn-outline-light disabled" style="padding: 5px">회원정보입력</span>
                    </li>
                    <li class="breadcrumb-item">
                        <span class="btn btn-light active" style="padding: 5px">가입완료</span>
                    </li>
                </ul>
            </nav>
        </div>
        <div style="margin: 55px 0 20px 0">
            <h2>회원가입 완료</h2>
            <small class="text-muted">
                안전하고 편리한 홈페이지 이용을 위해 회원님의 아이디와 비밀번호 관리에 주의해주시기 바랍니다.
            </small>
        </div>

        <div class="jumbotron bg-dark text-center" style="margin: 10px 30px 20px 50px">
            <h3 class="display-2" >회원가입을 <br>축하합니다</h3>
            <p class="lead">
            </p>
            <hr class="my-4">
            게시판 글 작성은 로그인 이후 가능합니다.
        </div>

        <div class="text-center" style="margin-bottom: 100px;">
            <a href="../index/index.jsp"><button class="btn btn-outline-light" id="go2index">메인으로 이동</button></a>
        </div>
    </div>
    <%@include file="../layout/footer.jsp"%>
    <script type="text/javascript" src="../../js/jquery-3.4.1.js"></script>
    <script type="text/javascript" src="../../js/bootstrap.js"></script>
    <script src="https://kit.fontawesome.com/3197007a76.js" crossorigin="anonymous"></script>
</body>
</html>
