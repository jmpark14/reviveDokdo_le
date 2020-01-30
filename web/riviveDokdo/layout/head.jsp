<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<% request.setCharacterEncoding("UTF-8"); %>

<style>
    #header{
        background-position: top center;
        background-repeat: no-repeat;
        background-size: cover;
    }
</style>
<%
String sessionID = null;
try {
    sessionID = (String)session.getAttribute("sessionID");
} catch (Exception ex) {

}
%>

<%
    String link = request.getRequestURL().toString();
    String[] secondLink = link.split("/");
%>

<header id="header">
    <div class="container">
        <div class="row">
            <div id="title" class="col-6" style="margin-top: 2%">
            </div>
            <div class="col-6 text-right" style="margin:10px 0">
                <%if (sessionID == null) {%>
                <button class="btn btn-danger" type="button" data-toggle="modal" data-target="#loginmodal">로그인</button>
                <button class="btn btn-primary" type="button" id="joinbtn">회원가입</button>
                <%} else {%>
                <button type="button" class="btn btn-outline-secondary" onclick="memberInfo()">
                    <b style="color: white; margin-right: 3%">
                        <i class="fas fa-user-alt">
                            &nbsp;&nbsp;<%=sessionID%>
                        </i>
                    </b>
                </button>
                <button class="btn btn-danger" type="button" onclick="logoutbtn()">로그아웃</button>
                <%}%>
            </div>
        </div>
        <div class="row">
            <div class="col-5">
                <div class="row">
                    <div class="col-7 text-left m-3" style="color: whitesmoke" id="showTime">
                    </div>
                </div>
            </div>
        </div>
        <div class="container">
            <div class="modal fade" id="loginmodal" role="dialog">
                <div class="modal-dialog">
                    <div class="modal-content">
                        <div class="modal-body text-center">
                            <div class="row" style="margin-top: 30px">
                                <div class="col-1"></div>
                                <div class="col-3">
                                    <p>아이디</p>
                                </div>
                                <div class="col-5">
                                    <input class="form-control form-control" type="text" id="loginuid" name="loginuid">
                                </div>
                            </div>
                            <div class="row">
                                <div class="col-1"></div>
                                <div class="col-3">
                                    <p>비밀번호</p>
                                </div>
                                <div class="col-5">
                                    <input class="form-control form-control" type="password" id="pwd" name="pwd">
                                </div>
                            </div>
                            <div class="row text-center">
                                <div class="col">
                                    <br>
                                    <p id="msg">&nbsp;</p>
                                </div>
                            </div>
                        </div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-primary" data-dissmiss="modal" onclick="loginbtn()">로그인</button>
                            <button type="button" class="btn btn-danger" data-dismiss="modal" onclick="closebtn()">닫기</button>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <%if (secondLink[4].equals("join")  || secondLink[4].equals("list")) {//다른 PC에서는 secondLink 배열 값을 바꿔야 함%>
        <div class="w100" style="padding-right:10px">&nbsp;</div>
        <div class="w300" style="padding-right:10px">&nbsp;</div>
        <%} else {%>
        <form class="form-group row justify-content-center" name="searchFrm" method="post" action="../list/list.jsp?search" style="padding-top: 10%"><%--게시물 검색--%>
            <div class="w100" style="padding-right:10px">
                <select class="form-control form-control" name="searchType" id="searchType">
                    <option value="title">제목</option>
                    <option value="contents">본문</option>
                    <option value="userid">작성자</option>
                </select>
            </div>
            <div class="w300" style="padding-right:10px">
                <input type="text" class="form-control form-control" name="keyword" id="keyword">
            </div>
            <div>
                <button class="btn btn-primary" name="btnSearch" id="btnSearch" onclick="check()">검색</button>
            </div>
            <input type="hidden" name="nowPage" value="1">
        </form>
        <form name="readFrm" method="get">
            <input type="hidden" name="brdno">
            <input type="hidden" name="nowPage" value="">
            <input type="hidden" name="searchType" value="">
            <input type="hidden" name="keyword" value="">
        </form>
        <%}%>
        <form name="readFrm" method="get">
            <input type="hidden" name="brdno">
            <input type="hidden" name="nowPage" value="">
            <input type="hidden" name="searchType" value="">
            <input type="hidden" name="keyword" value="">
        </form>
        <nav class="nav navbar-expand-md navbar-dark bg-success" id="navbar">
            <ul class="nav navbar-nav nav-pills nav-fill w-100">
                <li class="nav-item">
                    <a id="home" class="nav-link" href="../index/index.jsp">
                        <i class="fas fa-home"> HOME</i>
                    </a>
                </li>
                <li class="nav-item">
                    <a id="about" class="nav-link" href="../about/about.jsp"> ABOUT</a>
                </li>
                <li class="nav-item">
                    <a id="hist1" class="nav-link" href="../hist1/dkdhist1.jsp">독도의 역사 1</a>
                </li>
                <li class="nav-item">
                    <a id="hist2" class="nav-link" href="../dkdhist2/dkdhist2.jsp">독도의 역사 2</a>
                </li>
                <li class="nav-item">
                    <a id="gallery" class="nav-link" href="../gallery/gallery.jsp">갤러리</a>
                </li>
                <li class="nav-item">
                    <a id="list" class="nav-link" href="../list/list.jsp">게시판</a>
                </li>
                <li class="nav-item">
                    <a id="intro" class="nav-link" href="../intro/intro.jsp">소개 </a>
                </li>
            </ul>
        </nav>
    </div>
<script type="text/javascript" src="../../js/jquery-3.4.1.js"></script>
<script type="text/javascript" src="../../js/head.js"></script>
