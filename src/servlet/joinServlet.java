package servlet;

import service.rvdkdMgr;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/joinServlet")
public class joinServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        request.setCharacterEncoding("utf-8");
        response.setContentType("text/html; charset=UTF-8");
        rvdkdMgr mgr = new rvdkdMgr();
        try {
            mgr.joinMember(request, response);
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            response.sendRedirect("riviveDokdo/join/joinok.jsp");
        }
    }
}
