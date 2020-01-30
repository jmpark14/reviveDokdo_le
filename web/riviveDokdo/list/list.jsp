<%@ page import="VO.rvdkdBoard" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<% request.setCharacterEncoding("UTF-8"); %>
<%@ include file="../jdbc.jsp"%>
<jsp:useBean id="mgr" class="service.rvdkdMgr"/>
<%@ page import="java.sql.*" %>

<%
    //페이지 처리

    //글번호 초기화
    int bdcnt = 0;

    //게시글이 30개를 넘기면 페이지를 ++함
    int perPage = 30; //페이지 당 레코드 수

    int totalPage = 0; //전체 페이지 수

    int nowPage = 1; //현재 페이지

    try {
        nowPage = Integer.parseInt(request.getParameter("nowPage"));
    } catch (Exception ex) {
        ex.printStackTrace();
    }

    //현재 페이지 표시
    int startPage = ((nowPage -1) / 10) * 10 + 1;
    int endPage = startPage + 10 - 1;

    //게시물 30단위로 보여주기
    // ex) cp=1 : bdno = 1,30
    // ex) cp=2 : bdno = 31,60
    // ex) cp=3 : bdno = 61,90
    int startNum = ((nowPage-1) * perPage) + 1;
    int endNum = startNum + perPage - 1;

    String keyword = "", searchType = "";

    List<rvdkdBoard> lists = null;
    List<rvdkdBoard> lists2 = null;

    if (request.getParameter("keyword") != null) {
        keyword = request.getParameter("keyword");
        searchType = request.getParameter("searchType");
    }

    if (request.getParameter("nowPage") != null) {
        nowPage = Integer.parseInt(request.getParameter("nowPage"));
    }

    bdcnt = mgr.getTotalCount(keyword, searchType);
    totalPage = (int)Math.ceil((double)bdcnt / perPage); //전체 페이지 수

    //글 불러오기
    try {
        lists = mgr.readList(keyword, searchType, endNum, startNum);
    } catch (SQLException e) {
        e.printStackTrace();
    }

    // 글번호 재계산...하나 지워도 맞추도록...갯수
    int brdno = bdcnt - ((nowPage-1) * perPage);

    // 태그 불러오는 코드
    try{
        lists2 = mgr.getTags(request, response);
    } catch (SQLException e) {
        e.printStackTrace();
    }
%>

<!DOCTYPE html>
<html>
<head lang="ko">
    <title>독도의 역사 독도의 진실</title>
    <link rel="stylesheet" href="../../css/bootstrap.css">
</head>
<body>
<%@include file="../layout/head.jsp"%>
<div class="container">
    <div id="main" style=" margin: 20px 0">
        <div style="color: white">
            <i class="fas fa-list fa-2x"> 게시판</i>
        </div>
        <div class="row col-md-12" style="color: white; padding-top: 20px;"> <%-- 태그 나타나는곳 --%>
            <p style="font-size: 20px;font-weight: bold; padding-top: 4px;">인기태그 모아보기 :&nbsp;&nbsp;</p>
            <% try {
                for (rvdkdBoard b : lists2) { %>
            <span class="btn btn-outline-light disabled" style="height: 40px; font-size: 20px">#<%=b.getTags() %></span>&nbsp;&nbsp;
            <% } }catch (Exception ex) {
                ex.printStackTrace();
            } %>
        </div>
        <div class="text-right" style="margin: 3px"><%--글쓰기 아이콘--%>
            <a style="color: skyblue" id="write_icon"><i class="fas fa-edit"></i></a>
        </div>
        <div class="row"><%--게시판--%>
            <div class="col">
                <table class="table table-striped table-dark">
                    <thead style="background-color: grey">
                    <tr>
                        <th class="text-center" style="width: 8%">번호</th>
                        <th>제목</th>
                        <th class="text-center" style="width: 10%">작성자</th>
                        <th class="text-center" style="width: 10%">작성일</th>
                        <th class="text-center" style="width: 7%">추천</th>
                        <th class="text-center" style="width: 7%">조회</th>
                    </tr>
                    </thead>
                    <tbody>
                        <% try {
                            for (rvdkdBoard b : lists) { %>
                        <tr>
                            <td class="text-center"><%=brdno-- %></td>
                            <td><a href="view.jsp?brdno=<%=b.getBrdno()%>"><%=b.getTitle()%></a></td>
                            <td class="text-center"><%=b.getUserid()%></td>
                            <td class="text-center"><%=b.getRegdate().substring(0,10)%></td>
                            <td class="text-center"><%=b.getThumbs()%></td>
                            <td class="text-center"><%=b.getViews()%></td>
                        </tr>
                        <% }
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }%>
                        <%
                            //리스트에 아무것도 없을시 게시물 없다고 출력
                            if(lists == null || lists.isEmpty()) {
                                out.println("<tr><td></td>" +
                                        "<td class=\"text-right\">등록된 게시물이 없습니다.</td>" +
                                        "<td></td>" +
                                        "<td></td>" +
                                        "<td></td>" +
                                        "<td></td><tr>");
                                //없는 페이지로 가면 에러 창 출력
                            }
                        %>
                    </tbody>
                </table>
            </div>
        </div>
        <div class="row" style="padding: 10px 0 5px 0;"><%--글쓰기 버튼--%>
            <div class="col-6 text-left">
                <%
                    if (request.getParameter("keyword")  != null) {
                        out.print("<button type='button' class='btn btn-outline-light' id='return'>처음으로</button>");
                    }
                %>
            </div>
            <div class="col-6 text-right">
                <button type="button" class=" btn btn-outline-light" id="write">글쓰기</button>
            </div>
        </div>
        <div align="center" style="margin: 5px 0 10px 0">
            <%
                if (totalPage > 10) {
                    out.print("<span><a style='color:white' href='list.jsp?nowPage=" + (startPage - 1) + "'>이전</a>&nbsp;</span>");
                }
            %>
            <% for (int i = startPage; i < endPage; ++i) { %>
            <%
                if (i > totalPage) break;
                if (nowPage == i) {
                    out.print("<b style='color:white; font-size: 20px'>" + i +"&nbsp;</b>"); //현재 페이지에는 링크 걸지 않음
                }
                else {
                    out.print("<span><a style='color:white' href='list.jsp?nowPage="+i+"'>" + i +"</a>&nbsp;</span>");
                }
            %>
            <% } %>
            <%
                if (endPage < totalPage) {
                    out.print("<span><a style='color:white' href='list.jsp?nowPage" + (endPage + 1) + "'>다음</a><&nbsp;</sapn>");
                }
            %>
        </div>
        <form class="form-group row justify-content-center" name="searchFrm" method="post" action="list.jsp?search"><%--게시물 검색--%>
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
            <input type="hidden" name="nowPage" value="<%=nowPage%>">
            <input type="hidden" name="searchType" value="<%=searchType%>">
            <input type="hidden" name="keyword" value="<%=keyword%>">
        </form>
    </div>
</div>
<%@include file="../layout/footer.jsp"%>
</body>
<script type="text/javascript" src="../../js/jquery-3.4.1.js"></script>
<script type="text/javascript" src="../../js/bootstrap.js"></script>
<script src="https://kit.fontawesome.com/3197007a76.js" crossorigin="anonymous"></script>

<script>
    $(function() {
        $('#write').on('click', function(e) {
            if (<%=sessionID == null%>) {
                alert('로그인 후 이용하시길 바랍니다.')
            }
            else {
                location.href = 'write.jsp';
            }
        });
        $('#write_icon').on('click', function (e) {
            if (<%=sessionID == null%>) {
                alert('로그인 후 이용하시길 바랍니다.')
            }
            else {
                location.href = 'write.jsp';
            }
        })
    });
    function check() {
        var keyword = document.getElementById("keyword");
        if (keyword.value == "") {
            alert('검색어를 입력하세요.');
            keyword.focus();
        }
    }

    $(function () {
        $('#return').on('click', function (e) {
            location.href = 'list.jsp';
        })
    })
</script>
</html>
