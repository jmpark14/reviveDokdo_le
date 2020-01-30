package servlet;

import org.json.simple.JSONObject;
import service.rvdkdMgr;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
@WebServlet("/thumbsUpServlet")
public class thumbsUpServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String sessionID = request.getParameter("sessionID");
        int brdno = Integer.parseInt(request.getParameter("brdno"));
        int thumbs = 0;
        response.setContentType("application/x-json; charset=UTF-8");
        request.setCharacterEncoding("utf-8");
        rvdkdMgr mgr = new rvdkdMgr();


        try {
            thumbs = mgr.thumbsUp(brdno, sessionID);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        JSONObject obj = new JSONObject();
        obj.put("thumbs", thumbs);


        response.getWriter().print(obj);


    }


}
