package servlet;

import service.rvdkdMgr;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
@WebServlet("/writeServlet")
public class writeServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        rvdkdMgr mgr = new rvdkdMgr();
        try {
            mgr.insertBoard(request);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        response.sendRedirect("riviveDokdo/list/list.jsp");
    }
}
