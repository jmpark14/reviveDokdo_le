package servlet;

import VO.boardComment;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import service.rvdkdMgr;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

@WebServlet("/readCommentServlet")
public class readCommentServlet extends HttpServlet {

    protected void doGet (HttpServletRequest request, HttpServletResponse response) throws IOException {
        int brdno = Integer.parseInt(request.getParameter("brdno"));
        rvdkdMgr mgr = new rvdkdMgr();
        response.setContentType("application/x-json; charset=UTF-8");
        request.setCharacterEncoding("utf-8");
        ArrayList<boardComment> commentsLists = null;
        PrintWriter pw = response.getWriter();

        try {
            commentsLists = mgr.readComment(brdno);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        JSONObject obj = new JSONObject();

        try {
            JSONArray jsonArray = new JSONArray();
            for (int i = 0; i < commentsLists.size(); i++) {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("userID",commentsLists.get(i).getCommentUid());
                jsonObject.put("comment", commentsLists.get(i).getCommentContents());
                jsonObject.put("regdate", commentsLists.get(i).getCommentRegDate());
                jsonObject.put("cmtno", commentsLists.get(i).getCommentNo());
                jsonArray.add(jsonObject);
            }
            obj.put("comments", jsonArray);

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        response.getWriter().print(obj);
    }
}
