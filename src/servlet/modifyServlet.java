package servlet;

import service.rvdkdMgr;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/modifyServlet")
public class modifyServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        rvdkdMgr mgr = new rvdkdMgr();

        try {
            mgr.modifyBoard(request, response);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
