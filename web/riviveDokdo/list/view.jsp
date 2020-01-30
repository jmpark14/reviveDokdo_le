<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="../jdbc.jsp"%>
<% request.setCharacterEncoding("UTF-8"); %>
<jsp:useBean id="mgr" class="service.rvdkdMgr"/>
<%@page import="VO.rvdkdBoard" %>
<%@page import="VO.boardComment" %>

<%
    //list에서 brdno 값을 가져옴
    int brdno = Integer.parseInt(request.getParameter("brdno"));

    //게시글 변수
    String title = null;
    String userID = null;
    String regDate = null;
    String view = null;
    String contents = null;
    String tag = null;
    String thumbs = null;

    List <rvdkdBoard> list = null;

    /*List<boardComment> commentList = null;*/

    //게시글 가져오기
    try {
        list = mgr.viewList(brdno);
    } catch (Exception ex) {
        ex.printStackTrace();
    }
    for (rvdkdBoard b : list) {
        title = b.getTitle();
        userID = b.getUserid();
        regDate = b.getRegdate();
        view = b.getViews();
        contents = b.getContents();
        tag = b.getTags();
        thumbs = b.getThumbs();
        session.setAttribute("b", b);
    }

    /*//댓글 가져오기
    try {
        commentList = mgr.readComment(brdno);
    } catch (Exception ex) {
        ex.printStackTrace();
    }*/


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
            <i class="fas fa-book-open fa-2x"> <strong>게시판 - </strong><small> 게시글 보기</small></i>
        </div>
        <div class="text-right" style="margin: 3px">
            <a style="color: skyblue" id="write_icon"><i class="fas fa-edit"></i></a>
        </div>
        <hr color="white">
        <div class="row" style="margin: 10px 30px 20px 30px">

            <form class="col-6" action="${pageContext.request.contextPath}/prevNextServlet" method="post">
                <button type="submit" class="btn btn-outline-light" id="prevbtn">
                    <i class="fa fa-chevron-left">이전게시물</i>
                </button>
                <button type="submit" class="btn btn-outline-light" id="nextbtn">
                    <i class="fa fa-chevron-right">다음게시물</i>
                </button>
                <input value="<%=brdno%>" type="hidden" id="brdno" name="brdno">
                <input value="" type="hidden" name="pnselect" id="pnselect">
            </form>
            <div class="col-6 text-right">
                <button type="button" class="btn btn-outline-light" id="newbtn">
                    <i class="fa fa-plus-circle">새글쓰기</i>
                </button>
                <button type="button" class="btn btn-outline-light" id="listbtn">
                    <i class="fa fa-list">목록으로</i>
                </button>
            </div>
        </div>
        <div class="row" style="margin: 10px 30px 20px 30px"><%--뷰--%>
            <table class="table">
                <tr>
                    <th colspan="2" style="background-color: skyblue; border-bottom: 2px solid #000000" class="text-center">
                        <h3><%=title%></h3>
                    </th>
                </tr>
                <tr style="color: white">
                    <td><%=userID%></td>
                    <td class="text-right"><small><%=regDate.substring(0,10)%> / <%=view%></small></td>
                </tr>
                <tr style="color: white">
                    <td colspan="2">
                        <p><%=contents.replace("\n\r","<br>")%></p>
                        <%-- ~~().replace("\n\r","<br>") : 줄바꿈 기능이 먹히게끔 함--%>
                    </td>
                </tr>
                <tr style="color: white">
                    <td colspan="2" style="border-bottom: 2px solid; color: white">
                        <%
                            if (tag == null) {
                                out.print("<a>TAG : <a>");
                            } else {
                                out.print("<a>TAG : " + tag + "<a>");
                            }
                        %>
                    </td>
                </tr>
            </table>
            <div>
                <button type="button" class="btn btn-outline-warning" style="height: 40px">
                    <p>파일 다운로드</p>
                </button>
            </div>
            <span style="color: #FFFFFF">&nbsp;&nbsp;다운로드 할 파일이 없습니다.</span>
        </div>
        <div class="text-center">
            <button type="button" class="btn btn-outline-primary" onclick="thumbtn()">
                <i class="fas fa-thumbs-up" style="padding-bottom: 5px" id="like_cnt"> 추천 <%=thumbs%></i>
            </button>
        </div>
        <div class="row" style="margin: 10px 30px 20px 30px">
            <% if(sessionID != null && sessionID.equals(userID)) { %>
            <form class="col-6" action="${pageContext.request.contextPath}/modifyDeleteServlet" method="post">
                <button type="submit" class="btn btn-outline-warning" id="updatebtn">
                    <i class="fa fa-pencil"> 수정하기</i>
                </button>
                <button type="submit" class="btn btn-outline-danger" id="deletebtn">
                    <i class="fa fa-trash-o"> 삭제하기</i>
                </button>
                <input value="<%=brdno%>" type="hidden" name="brdno">
                <input value="" type="hidden" id="mdselect" name="mdselect">
            </form>
            <%} else {%>
            <div class="col-6"></div>
            <%}%>
            <div class="col-6 text-right">
                <button type="button" class="btn btn-outline-light" id="listbtn1">
                    <i class="fa fa-list">목록으로</i>
                </button>
            </div>
        </div><!--하단버튼-->
        <hr color="white 2px">
        <div class="row" style="margin: 10px 30px 20px 30px; color:white">
            <h3><i class="fa fa-commenting"></i>댓글</h3>
            <div class="col-12">
                <table class="table" style="border-bottom: 2px solid white;
                                        border-top: 2px solid white; color:white"  id="commentLists">
                </table>
            </div>

        </div> <!-- 댓글 -->

        <div class="row" style="margin: 10px 30px 20px 30px">
            <div class=" card card-body" style="background-color: #001A3F; border-radius: 10px" >
                <div class="form-group row">
                    <%if (sessionID == null) {%>
                    <label class="" style="margin: auto; color:white; font-size: 20px">로그인하세요</label>
                    <textarea rows="3" class="form-control col-7" placeholder="로그인 후 이용 부탁드립니다."
                              style="margin-bottom: -15px; background-color: #DEDEFF;" disabled></textarea>
                    <button type="button" class="btn btn-outline-light" style="margin: auto" disabled>
                        <i class="fa fa-commenting"></i> 댓글쓰기
                    </button>
                    <%} else {%>
                    <label class="" style="margin: auto; color:white; font-size: 20px"><%=sessionID%></label>
                    <textarea rows="3" id="comment" class="form-control col-7" placeholder="댓글을 입력하세요."
                              style="margin-bottom: -15px; background-color: #DEDEFF;"></textarea>
                    <button type="button" class="btn btn-outline-light" style="margin: auto" onclick="cmtbtn()">
                        <i class="fa fa-commenting"></i> 댓글쓰기
                    </button>
                    <%}%>
                </div>
            </div>
        </div> <!-- 댓글쓰기 -->
    </div>
</div>

<%@include file="../layout/footer.jsp"%>

</body>
<script type="text/javascript" src="../../js/jquery-3.4.1.js"></script>
<script type="text/javascript" src="../../js/bootstrap.js"></script>
<script src="https://kit.fontawesome.com/3197007a76.js" crossorigin="anonymous"></script>

<script type="text/javascript">
    $(function() {
        $('#newbtn').on('click', function(e) {
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

    //목록으로 버튼을 클릭시
    $(function () {
        $('#listbtn').on('click', function (e) {
            location.href = 'list.jsp';
        });
        $('#listbtn1').on('click', function (e) {
            location.href = 'list.jsp';
        });
    });

    //추천 버튼을 클릭 시
    function thumbtn() {
        var sessionID = '<%=sessionID%>';
        var userID = '<%=userID%>';
        var brdno = '<%=brdno%>';
        //로그인 여부
        if(sessionID != 'null') {
            if (sessionID == userID) {
                alert('자기 게시물에는 추천할 수 없습니다.')
            } else {
                $.ajax({
                    async:true,
                    type: 'POST',
                    data:{"brdno" : brdno, "sessionID":sessionID},
                    url : "/thumbsUpServlet",
                    success : function (data) {
                        if (data.thumbs == -1) {
                            alert('이미 추천하셨습니다.')
                        }
                        else {
                            $("#like_cnt").html(" 추천 " + data.thumbs);
                        }
                    },
                    error : function () {
                        alert('추천 도중 오류가 발생하였습니다.')
                    }
                })
            }
        } else {
            alert('로그인 후 이용하시길 바랍니다.');
        }
    }


    //삭제 버튼 클릭시
    $(function () {
        $('#deletebtn').on('click', function (e) {
            var select = document.getElementById("mdselect");
            select.value = "delete";
            if(confirm('이 글을 삭제하시겠습니까?')) {
                alert("글을 삭제했습니다.");
                return true;
            }
            else {
                return false;
            }
        });
    });

    //수정 버튼 클릭시
    $(function () {
        $('#updatebtn').on('click', function (e) {
            var select = document.getElementById("mdselect");
            select.value = "modify";
        });
    });

    //이전으로 버튼 클릭시
    $(function () {
        $('#prevbtn').on('click', function (e) {
            var select = document.getElementById("pnselect");
            select.value = "preview";
        });
    });

    //다음으로 버튼 클릭시
    $(function () {
        $('#nextbtn').on('click', function (e) {
            var select = document.getElementById("pnselect");
            select.value = "next";
        })
    });

    //댓글 버튼 클릭시
    function cmtbtn() {
        var comment = document.getElementById('comment');
        var brdno = '<%=brdno%>';
        var sessionID = '<%=sessionID%>';
        if (comment.value == null || comment.value == "") {
            alert('댓글이 입력되지 않았습니다.');
        } else {
            $.ajax({
                async:true,
                type: 'POST',
                data:{"brdno" : brdno, "comment": comment.value, "sessionID":sessionID},
                url : "/inputCommentSevlet",
                success : function () {
                    $('#comment').val('');
                    readComment();
                },
                error : function () {
                    alert('댓글 입력 도중 오류가 발생했습니다.')
                }
            })
        }
    }

    $(function(){
        readComment()
    });

    function readComment() {
        var brdno = '<%=brdno%>';
        var sessionID  = '<%=sessionID%>';

        $.ajax({
            async:true,
            type: 'GET',
            dataType:'json',
            data:{"brdno" : brdno},
            url : "/readCommentServlet",
            success : function (data) {
                var commentHtml = '';

                if (data.comments.length > 0) {
                    $.each(data.comments, function () {
                        commentHtml += '<tr style="border-bottom: 1px solid #e5e5e5;">';
                        commentHtml += '<td style="width: 25%"><h4>' + this.userID + '</h4></td>';
                        commentHtml += '<td style="width: 50%">';
                        commentHtml += '<p>' + this.comment + '</p>';
                        commentHtml += '</td>';
                        commentHtml += '<td style="width: 25%; text-align: right"> ' + this.regdate;
                        if (sessionID == this.userID) {
                            commentHtml += '<a style="color: deepskyblue" id="modifyComment"><i class="fa fa-pencil"><input type="hidden" value="' + this.cmtno + '"></i></a>';
                            commentHtml += '<a style="color: deepskyblue" onclick="deleteComment(' + this.cmtno + ')"><i class="fa fa-trash-o"></i></a>';
                        }
                        else {
                            commentHtml += '<a href="">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</a>';
                        }

                        commentHtml += '</td>';
                        commentHtml += '</tr>';
                    });

                    $('#commentLists').html(commentHtml);
                }
                else {
                    commentHtml += '<tr style="border-bottom: 1px solid #e5e5e5;">';
                    commentHtml += '<td style="width: 25%"><h4></h4></td>';
                    commentHtml += '<td style="width: 50%" class="text-center">';
                    commentHtml += '<p>등록된 댓글이 없습니다.</p>';
                    commentHtml += '</td>';
                    commentHtml += '<td style="width: 25%; text-align: right">';
                    commentHtml += '</td>';
                    commentHtml += '</tr>';

                    $('#commentLists').html(commentHtml);
                }
            },
            error : function () {
                alert('실패')
            }
        });
    }

    //댓글 삭제
    function deleteComment(cmdno) {
        console.log(cmdno);
        if (confirm('댓글을 삭제하시겠습니까?')) {
            $.ajax({
                async: true,
                type: 'POST',
                data: {"cmdno": cmdno},
                url: "/DeleteCommentServlet",
                success : function () {
                    readComment();
                }
            })
        } else {
            return;
        }
    }
</script>
</html>
