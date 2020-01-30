<%@ page import="java.util.List" %>
<%@ page import="VO.MemberVO" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="utf-8" %>
<% request.setCharacterEncoding("UTF-8"); %>
<jsp:useBean id="mgr" class="service.rvdkdMgr"/>

<!DOCTYPE html>
<html>
<head lang="ko">
    <title>독도의 역사 독도의 진실</title>
    <link rel="stylesheet" href="../../css/bootstrap.css">
</head>
<body>
<%@include file="../layout/head.jsp"%>
<div class="container">
    <%
        List<MemberVO> membervos = null;
        System.out.println(sessionID);

        try {
            membervos = mgr.readMember(sessionID);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        String userID = null;
        String name = null;
        String statement = null;
        String addr1 = null;
        String addr2 = null;
        String email = null;
        String tel = null;
        try {
            for (MemberVO memberVO : membervos) {
                userID = memberVO.getUserid();
                name = memberVO.getName();
                statement = memberVO.getStatement();
                addr1 = memberVO.getAddr1();
                addr2 = memberVO.getAddr2();
                email = memberVO.getEmail();
                tel = memberVO.getTel();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    %>
        <div style="color: white; padding: 30px 0;">
            <i class="fas fa-user fa-2x">회원정보</i>
        </div>
        <form method="post">
            <div class="card card-body bg-dark" style="margin: 10px 30px 20px 50px">
                <div class="row" style="margin-top: 30px">
                    <div class="col-3"></div>
                    <div class="col-6" style="color: white">
                        <div class="form-group row col-12">
                            <p class="col-4">아이디</p>
                            <div class="col-8">
                                <p style="font-weight: bold"><%=userID%></p>
                            </div>
                        </div>
                        <div class="form-group row col-12">
                            <p class="col-4">이름</p>
                            <div class="col-8">
                                <p style="font-weight: bold"><%=name%></p>
                            </div>
                        </div>
                        <div class="form-group row col-12">
                            <p class="col-4">이메일 주소</p>
                            <div class="col-8">
                                <p style="font-weight: bold"><%=email%></p>
                            </div>
                        </div>
                        <div class="form-group row col-12">
                            <p class="col-4">주소</p>
                            <div class="col-8">
                                <p class="row-1" style="font-weight: bold"><%=statement%></p>
                                <p class="row-1" style="font-weight: bold"><%=addr1%></p>
                                <p class="row-1" style="font-weight: bold"><%=addr2%></p>
                            </div>
                        </div>
                        <div class="form-group row col-12">
                            <p class="col-4">연락처</p>
                            <div class="col-8">
                                <p style="font-weight: bold"><%=tel%></p>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <hr color="white">
            <div class="text-center" style="margin: 15px 0">
                <button class="btn btn-outline-primary" type="button" onclick="history.go(-1)"><i class="fas fa-check"></i> 확인완료</button>
                <button class="btn btn-outline-warning" type="submit" id="newjoinbtn"><i class="fas fa-edit"></i> 수정하기</button>
            </div>
        </form>
    </div>
</div>
<%@include file="../layout/footer.jsp"%>
<script type="text/javascript" src="../../js/jquery-3.4.1.js"></script>
<script type="text/javascript" src="../../js/bootstrap.js"></script>
<script src="https://kit.fontawesome.com/3197007a76.js" crossorigin="anonymous"></script>
</body>
</html>
