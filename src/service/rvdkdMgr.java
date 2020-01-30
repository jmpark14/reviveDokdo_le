package service;

import VO.MemberVO;
import VO.rvdkdBoard;
import VO.boardComment;
import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class rvdkdMgr {
    private static final String SAVEFOLDER = "C:/Users/TJ/Desktop/Team_201/file";
    private static final String ENCTYPE = "utf-8";
    private static int MAXSIZE = 10 * 1024 * 1024; //10MB
    private static String DRV = "oracle.jdbc.OracleDriver";
    private static String URL = "jdbc:oracle:thin:@15.164.233.149:1521:XE";
    private static String USR = "scott";
    private static String PWD = "tiger";

    Connection conn = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;

    rvdkdBoard b = null;

    boardComment comment = null;
    MemberVO member = null;

    //생성자
    public rvdkdMgr() {

    }

    //글쓰기 처리
    public void insertBoard(HttpServletRequest req) throws SQLException {
        MultipartRequest multi = null;
        int filesize = 0;
        String filename = null;
        String insertSQL = "insert into SCOTT.RVDKDBOARD (BRDNO, USERID, TITLE, CONTENTS, TAGS, FILENAME) VALUES (SCOTT.RVDKDSQ.nextval,?,?,?,?,?)";


        try {
            Class.forName(DRV);
            conn = DriverManager.getConnection(URL, USR, PWD);
            pstmt = conn.prepareStatement(insertSQL);
            File file = new File(SAVEFOLDER);
            if (!file.exists()) {
                file.mkdirs();
            }
            multi = new MultipartRequest(req, SAVEFOLDER, MAXSIZE, ENCTYPE, new DefaultFileRenamePolicy());

            if (multi.getFilesystemName("file") != null) {
                filename = multi.getFilesystemName("file");
            }

            pstmt.setString(1,multi.getParameter("userid"));
            pstmt.setString(2, multi.getParameter("listtitle"));
            pstmt.setString(3, multi.getParameter("contents"));
            pstmt.setString(4, multi.getParameter("tags"));
            pstmt.setString(5, filename);
            pstmt.executeUpdate();
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (conn != null) {
                conn.close();
            }
            if (pstmt != null) {
                pstmt.close();
            }
        }
    }

    //추천 처리
    public int thumbsUp(int brdno, String sessionID) throws SQLException, IOException {
        String searchThumbSQL = "select USERID from SCOTT.THUMPSUP where BRDNO = " + brdno;
        String inputThumbSQL = "insert into SCOTT.THUMPSUP (BRDNO, USERID) values (?,?)";
        String thumbsUpSQL = "UPDATE SCOTT.RVDKDBOARD SET THUMBS = THUMBS + 1 WHERE BRDNO = " + brdno;
        String returnThumbsSQL = "select THUMBS from SCOTT.RVDKDBOARD where BRDNO = " + brdno;
        int thumbs = 0;
        String noDuple = null;
        //추천 중복 체크
        try {
            Class.forName(DRV);
            conn = DriverManager.getConnection(URL, USR, PWD);
            pstmt = conn.prepareStatement(searchThumbSQL);
            rs = pstmt.executeQuery();
            //값이 있으면...
            if (rs.next()) {
                noDuple = rs.getString(1);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (conn != null) {
                conn.close();
            }
            if (pstmt != null) {
                pstmt.close();
            }
            if (rs != null) {
                rs.close();
            }
        }
        //중복 방지
        if (noDuple == null) {
            //추천 리스트에 넣기
            try {
                Class.forName(DRV);
                conn = DriverManager.getConnection(URL, USR, PWD);
                pstmt = conn.prepareStatement(inputThumbSQL);
                pstmt.setInt(1,brdno);
                pstmt.setString(2,sessionID);
                pstmt.executeUpdate();
            } catch (Exception ex) {
                ex.printStackTrace();
            } finally {
                if (conn != null) {
                    conn.close();
                }
                if (pstmt != null) {
                    pstmt.close();
                }
            }
            //추천 업데이트
            try {
                Class.forName(DRV);
                conn = DriverManager.getConnection(URL, USR, PWD);
                pstmt = conn.prepareStatement(thumbsUpSQL);
                pstmt.executeUpdate();
            } catch (Exception ex) {
                ex.printStackTrace();
            } finally {
                if (conn != null) {
                    conn.close();
                }
                if (pstmt != null) {
                    pstmt.close();
                }
            }
            try {
                Class.forName(DRV);
                conn = DriverManager.getConnection(URL, USR, PWD);
                pstmt = conn.prepareStatement(returnThumbsSQL);
                rs = pstmt.executeQuery();
                if (rs.next()) {
                    thumbs = rs.getInt(1);
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            } finally {
                if (conn != null) {
                    conn.close();
                }
                if (pstmt != null) {
                    pstmt.close();
                }
                if (rs != null) {
                    rs.close();
                }
            }
        }
        else {
            thumbs = -1;
        }
        return thumbs;
    }

    //글 삭제 처리
    public void deleteBoard (HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
        int brdno = Integer.parseInt(request.getParameter("brdno"));

        String deleteSQL = "DELETE FROM SCOTT.RVDKDBOARD WHERE BRDNO = " + brdno;
        try {
            Class.forName(DRV);
            conn = DriverManager.getConnection(URL, USR, PWD);
            pstmt = conn.prepareStatement(deleteSQL);
            pstmt.executeUpdate();
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (conn != null) {
                conn.close();
            }
            if (pstmt != null) {
                pstmt.close();
            }
        }
        response.sendRedirect("riviveDokdo/list/list.jsp");
    }

    //글 수정 처리, 글 불러오기
    public void modify (HttpServletRequest request, HttpServletResponse response) throws IOException, SQLException {
        int brdno = Integer.parseInt(request.getParameter("brdno"));
        request.setCharacterEncoding("utf-8");
        response.setContentType("text/html; cahrset=utf-8");

        HttpSession session = request.getSession();

        b = (rvdkdBoard) session.getAttribute("b");

        String modifyreadSQL = "SELECT TITLE, CONTENTS, TAGS, FILENAME, BRDNO FROM SCOTT.RVDKDBOARD WHERE BRDNO = " + brdno;

        try {
            Class.forName(DRV);
            conn = DriverManager.getConnection(URL, USR, PWD);
            pstmt = conn.prepareStatement(modifyreadSQL);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                b.setTitle(rs.getString(1));
                b.setContents(rs.getString(2));
                b.setTags(rs.getString(3));
                b.setFileName(rs.getString(4));
                b.setBrdno(rs.getString(5));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (conn != null) {
                conn.close();
            }
            if (pstmt != null) {
                pstmt.close();
            }
            if (rs != null) {
                rs.close();
            }
        }
        response.sendRedirect("riviveDokdo/list/modify.jsp?brdno=" + brdno);
    }

    //글 수정 처리, 글 수정완료
    public void modifyBoard(HttpServletRequest request, HttpServletResponse response) throws IOException, SQLException {
        MultipartRequest multi = null;
        int filesize = 0;
        String filename = null;

        int brdno = Integer.parseInt(request.getParameter("brdno"));
        request.setCharacterEncoding("utf-8");
        response.setContentType("text/html; charset=utf-8");

        String modifyCompleteSQL ="UPDATE SCOTT.RVDKDBOARD SET TITLE = ?, CONTENTS = ?, TAGS = ? WHERE BRDNO = " + brdno;
        try {
            Class.forName(DRV);
            conn = DriverManager.getConnection(URL, USR, PWD);
            pstmt = conn.prepareStatement(modifyCompleteSQL);
            File file = new File(SAVEFOLDER);
            if (!file.exists()) {
                file.mkdirs();
            }
            pstmt.setString(1, request.getParameter("title"));
            pstmt.setString(2, request.getParameter("contents"));
            pstmt.setString(3, request.getParameter("tags"));
            pstmt.executeQuery();

        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (conn != null) {
                conn.close();
            }
            if (pstmt != null) {
                pstmt.close();
            }
        }
        response.sendRedirect("riviveDokdo/list/view.jsp?brdno=" + brdno);
    }

    //이전 게시물
    public void preview (HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
        int brdno = Integer.parseInt(request.getParameter("brdno"));
        String prebrdno = null;
        response.setContentType("text/html; charset=utf-8");
        PrintWriter out = response.getWriter();
        String previewSQL = "SELECT * FROM (SELECT BRDNO, LAG(BRDNO) over (ORDER BY BRDNO) FROM RVDKDBOARD) WHERE BRDNO = " + brdno;
        try {
            Class.forName(DRV);
            conn = DriverManager.getConnection(URL, USR, PWD);
            pstmt = conn.prepareStatement(previewSQL);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                prebrdno = rs.getString(2);
            }
            if(prebrdno == null) {
                out.println("<script>");
                out.println("history.back();");
                out.println("alert('첫 게시물입니다.');");
                out.println("</script>");
            } else {
                response.sendRedirect("riviveDokdo/list/view.jsp?brdno=" + prebrdno);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (pstmt != null) {
                pstmt.close();
            }
            if (conn != null) {
                conn.close();
            }
        }

    }

    //다음 게시물
    public void next (HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
        int brdno = Integer.parseInt(request.getParameter("brdno"));
        String nextbrdno = null;
        response.setContentType("text/html; charset=utf-8");
        PrintWriter out = response.getWriter();
        String nextSQL = "SELECT * FROM (SELECT BRDNO, LEAD(BRDNO) over (ORDER BY BRDNO) FROM RVDKDBOARD) WHERE BRDNO = " + brdno;
        try {
            Class.forName(DRV);
            conn = DriverManager.getConnection(URL, USR, PWD);
            pstmt = conn.prepareStatement(nextSQL);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                nextbrdno = rs.getString(2);
            }
            if(nextbrdno == null) {
                out.println("<script>");
                out.println("history.back();");
                out.println("alert('마지막 게시물입니다.');");
                out.println("</script>");
            } else {
                response.sendRedirect("riviveDokdo/list/view.jsp?brdno=" + nextbrdno);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (pstmt != null) {
                pstmt.close();
            }
            if (conn != null) {
                conn.close();
            }
        }
    }

    //게시물 불러오기
    public List<rvdkdBoard> readList (String keyword, String searchType, int end, int start) throws SQLException {
        String readListSQL = null;
        List<rvdkdBoard> lists = null;

        try {
            Class.forName(DRV);
            conn = DriverManager.getConnection(URL,USR,PWD);

            if (keyword.equals(null) || keyword.equals("")) {
                readListSQL = "select * from (select bd.brdno, bd.title, bd.userid, bd.views, bd.regdate, bd.thumbs, rownum as rnum from (select brdno,title,userid,views,regdate, thumbs from SCOTT.RVDKDBOARD order by brdno desc) bd where rownum <= ?) bd2 where bd2.rnum >= ? ";
                pstmt = conn.prepareStatement(readListSQL);
                pstmt.setInt(1, end);
                pstmt.setInt(2, start);
            }
            else {
                readListSQL = "select * from (select bd.brdno, bd.title, bd.userid, bd.views, bd.regdate, bd.thumbs, rownum as rnum from (select brdno,title,userid,views,regdate, thumbs from SCOTT.RVDKDBOARD where " + searchType + " like ? order by brdno desc) bd where rownum <= ?) bd2 where bd2.rnum >= ?";
                pstmt = conn.prepareStatement(readListSQL);
                pstmt.setString(1,"%" + keyword + "%");
                pstmt.setInt(2, end);
                pstmt.setInt(3, start);
            }
            rs = pstmt.executeQuery();
            lists = new ArrayList<>();
            while (rs.next()) {
                rvdkdBoard b = new rvdkdBoard();
                b.setBrdno(rs.getString(1));
                b.setTitle(rs.getString(2));
                b.setUserid(rs.getString(3));
                b.setViews(rs.getString(4));
                b.setRegdate(rs.getString(5));
                b.setThumbs(rs.getString(6));
                lists.add(b);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (pstmt != null) {
                pstmt.close();
            }
            if (conn != null) {
                conn.close();
            }
        }

        return lists;
    }

    //총 게시물 수 불러오기
    public int getTotalCount (String keyword, String searchType) {

        String countSQL = null;
        int totalCount = 0;
        try {
            Class.forName(DRV);
            conn = DriverManager.getConnection(URL, USR, PWD);
            if (keyword.equals("null") || keyword.equals("")) {
                countSQL = "select count(BRDNO) from SCOTT.RVDKDBOARD";
                pstmt = conn.prepareStatement(countSQL);
            }
            else {
                countSQL = "select count(BRDNO) from SCOTT.RVDKDBOARD where " + searchType + " like ? ";
                pstmt = conn.prepareStatement(countSQL);
                pstmt.setString(1, "%" + keyword + "%");
            }
            rs = pstmt.executeQuery();
            if (rs.next()) {
                totalCount = rs.getInt(1);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return totalCount;
    }

    //게시글 보기
    public List<rvdkdBoard> viewList (int brdno) throws SQLException {
        String viewCountUpSQL = "UPDATE SCOTT.RVDKDBOARD SET VIEWS = VIEWS + 1 WHERE BRDNO = " + brdno;
        String viewSQL = "SELECT USERID, TITLE, CONTENTS, VIEWS, THUMBS, TAGS, REGDATE, FILENAME FROM RVDKDBOARD WHERE BRDNO = " + brdno;

        List<rvdkdBoard> list = null;
        //조회수 올리기
        try {
            Class.forName(DRV);
            conn = DriverManager.getConnection(URL, USR, PWD);
            pstmt = conn.prepareStatement(viewCountUpSQL);

            pstmt.executeUpdate();
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (conn != null) {
                conn.close();
            }
            if (pstmt != null) {
                pstmt.close();
            }
        }

        //글 가져오기
        try {
            Class.forName(DRV);
            conn = DriverManager.getConnection(URL, USR, PWD);
            pstmt = conn.prepareStatement(viewSQL);

            rs = pstmt.executeQuery();
            list = new ArrayList<>();
            if (rs.next()) {
                b = new rvdkdBoard();
                b.setUserid(rs.getString(1));
                b.setTitle(rs.getString(2));
                b.setContents(rs.getString(3));
                b.setViews(rs.getString(4));
                b.setThumbs(rs.getString(5));
                b.setTags(rs.getString(6));
                b.setRegdate(rs.getString(7));
                b.setFileName(rs.getString(8));
                list.add(b);
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (conn != null) {
                conn.close();
            }
            if (pstmt != null) {
                pstmt.close();
            }
            if (rs != null) {
                rs.close();
            }
        }
        return list;

    }

    //회원가입
    public void joinMember(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
        String joinMemberSQL = "insert into SCOTT.RVDODMEMBER (mbrno, userid, passwd, name, COUNTRY, STATEMENT, addr1, addr2, email, TEL1, TEL2) values (SCOTT.MEMBERSQ.nextval,?,?,?,?,?,?,?,?,?,?)";

        try {
            Class.forName(DRV);
            conn = DriverManager.getConnection(URL, USR, PWD);
            pstmt = conn.prepareStatement(joinMemberSQL);
            pstmt.setString(1,request.getParameter("uid"));
            pstmt.setString(2,request.getParameter("pwd2"));
            pstmt.setString(3,request.getParameter("psname"));
            pstmt.setString(4,request.getParameter("counrty"));
            pstmt.setString(5,request.getParameter("statecity"));
            pstmt.setString(6,request.getParameter("address1"));
            pstmt.setString(7,request.getParameter("address2"));
            pstmt.setString(8,request.getParameter("email"));
            pstmt.setInt(9,Integer.parseInt(request.getParameter("contact1")));
            pstmt.setString(10,request.getParameter("contact2"));
            pstmt.executeUpdate();
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (conn != null) {
                conn.close();
            }
            if (pstmt != null) {
                pstmt.close();
            }
        }
    }

    //아이디 중복 체크
    public int checkid(String id) throws SQLException, IOException {
        String checkidSQL = "select USERID from SCOTT.RVDODMEMBER where USERID = ?";
        int isId = 0;
        try {
            Class.forName(DRV);
            conn = DriverManager.getConnection(URL, USR, PWD);
            pstmt = conn.prepareStatement(checkidSQL);
            pstmt.setString(1,id);
            rs = pstmt.executeQuery();

            if (rs.next()) { //결과가 있다면
                //이미 존재하는 아이디
                isId = 0;

            }
            else {
                //가입 가능한 아이디
                isId = 1;
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (conn != null) {
                conn.close();
            }
            if (pstmt != null) {
                pstmt.close();
            }
            if (rs != null) {
                rs.close();
            }
        }
        System.out.println("mgr" + isId);
        return isId;
    }

    //로그인 처리
    public int login(String uid, String pwd) throws SQLException {
        String loginSQL = "select PASSWD from SCOTT.RVDODMEMBER where USERID = ?";
        String pwd2 = "";
        int isLogin = 0;
        System.out.println("login" + uid + "," + "pwd");

        try {
            Class.forName(DRV);
            conn = DriverManager.getConnection(URL, USR, PWD);
            pstmt = conn.prepareStatement(loginSQL);
            pstmt.setString(1,uid);
            rs = pstmt.executeQuery();

            if (rs.next()) { //입력한 아이디에 해당하는 비번이 있는 경우.
                pwd2 = rs.getString("PASSWD");

                if (pwd2.equals(pwd)) {
                    isLogin = 1;
                }
                else {
                    isLogin = -1;
                }
            }
            else {
                isLogin = -1;
            }
        }catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (conn != null) {
                conn.close();
            }
            if (pstmt != null) {
                pstmt.close();
            }
            if (rs != null) {
                rs.close();
            }
        }
        return isLogin;
    }

    //댓글 불러오기
    public ArrayList<boardComment> readComment (int brdno) throws SQLException {
        String readCommentSQL = "select USERID, COMMENT_CONTENT, REGDATE, CMTNO from SCOTT.BOARDCOMMENT where BRDNO = " + brdno;
        ArrayList<boardComment> commentsLists = null;

        try {
            Class.forName(DRV);
            conn = DriverManager.getConnection(URL, USR, PWD);
            pstmt = conn.prepareStatement(readCommentSQL);
            rs = pstmt.executeQuery();
            commentsLists = new ArrayList<>();
            while (rs.next()) {
                comment = new boardComment();
                comment.setCommentUid(rs.getString(1));
                comment.setCommentContents(rs.getString(2));
                comment.setCommentRegDate(rs.getString(3));
                comment.setCommentNo(rs.getInt(4));
                commentsLists.add(comment);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (pstmt != null) {
                pstmt.close();
            }
            if (conn != null) {
                conn.close();
            }
        }
        return commentsLists;

    }

    //총 댓글 불러오기

    //댓글 쓰기 처리
    public void inputComments (int brdno, String comment, String sessionID) throws SQLException {
        String inputCommentSQL = "insert into SCOTT.BOARDCOMMENT (BRDNO, USERID, COMMENT_CONTENT, CMTNO) values (?,?,?, SCOTT.COMMENTSSQ.nextval)";
        try {
            Class.forName(DRV);
            conn = DriverManager.getConnection(URL, USR, PWD);
            pstmt = conn.prepareStatement(inputCommentSQL);
            pstmt.setInt(1,brdno);
            pstmt.setString(2,sessionID);
            pstmt.setString(3,comment);
            int result = pstmt.executeUpdate();

        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (conn != null) {
                conn.close();
            }
            if (pstmt != null) {
                pstmt.close();
            }
        }

    }
    // 2개 이상 태그 불러오기
    public List<rvdkdBoard> getTags(HttpServletRequest request, HttpServletResponse response) throws SQLException {
        String tagSQL = "SELECT tags FROM (SELECT TAGS, ROW_NUMBER() OVER (ORDER BY tags) R FROM RVDKDBOARD GROUP BY TAGS HAVING COUNT(TAGS) >= 3) WHERE R BETWEEN 0 AND 5";
        List<rvdkdBoard> lists = null;
        lists = new ArrayList<>();

        try {
            Class.forName(DRV);
            conn = DriverManager.getConnection(URL, USR, PWD);
            pstmt = conn.prepareStatement(tagSQL);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                rvdkdBoard b = new rvdkdBoard();
                b.setTags(rs.getString(1));
                lists.add(b);
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (pstmt != null) {
                pstmt.close();
            }
            if (conn != null) {
                conn.close();
            }
        }
        return lists;
    }

    // 댓글 삭제
    public void deleteComment (int cmdno) throws SQLException {
        String DeleteCommentSQL = "delete from SCOTT.BOARDCOMMENT where CMTNO = " + cmdno;
        try {
            Class.forName(DRV);
            conn = DriverManager.getConnection(URL, USR, PWD);
            pstmt = conn.prepareStatement(DeleteCommentSQL);
            pstmt.executeUpdate();
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (conn != null) {
                conn.close();
            }
            if (pstmt != null) {
                pstmt.close();
            }
        }
    }

    // 회원정보 보기
    public List<MemberVO> readMember(String sessionID) throws SQLException {
        String readMemberSQL = "select USERID, NAME, EMAIL, STATEMENT, ADDR1, ADDR2, TEL2 from SCOTT.RVDODMEMBER where USERID = ?";
        List<MemberVO> memberList = null;
        try {
            Class.forName(DRV);
            conn = DriverManager.getConnection(URL, USR, PWD);
            pstmt = conn.prepareStatement(readMemberSQL);
            pstmt.setString(1,sessionID);
            rs = pstmt.executeQuery();
            memberList = new ArrayList<>();
            if (rs.next()) {
                member = new MemberVO();
                member.setUserid(rs.getString(1));
                member.setName(rs.getString(2));
                member.setEmail(rs.getString(3));
                member.setStatement(rs.getString(4));
                member.setAddr1(rs.getString(5));
                member.setAddr2(rs.getString(6));
                member.setTel(rs.getString(7));
                memberList.add(member);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (conn != null) {
                conn.close();
            }
            if (pstmt != null) {
                pstmt.close();
            }
            if (rs != null) {
                rs.close();
            }
        }
        return memberList;
    }
}
