package servlet;

import org.json.simple.JSONObject;
import service.rvdkdMgr;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;

@WebServlet("/inputCommentSevlet")
public class inputCommentSevlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int brdno = Integer.parseInt(request.getParameter("brdno"));
        String comment = request.getParameter("comment");
        String sessionID = request.getParameter("sessionID");
        rvdkdMgr mgr = new rvdkdMgr();

        try {
            mgr.inputComments(brdno,comment,sessionID);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
