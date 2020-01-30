package servlet;

import service.rvdkdMgr;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/DeleteCommentServlet")
public class DeleteCommentServlet extends HttpServlet {

    protected void doPost (HttpServletRequest request, HttpServletResponse response) {
        int cmdno = Integer.parseInt(request.getParameter("cmdno"));
        rvdkdMgr mgr = new rvdkdMgr();

        try {
            mgr.deleteComment(cmdno);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
