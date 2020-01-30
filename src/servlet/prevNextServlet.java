package servlet;

import service.rvdkdMgr;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/prevNextServlet")
public class prevNextServlet extends HttpServlet {
    protected void doPost (HttpServletRequest request ,HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        rvdkdMgr mgr = new rvdkdMgr();
        String select = request.getParameter("pnselect");

        switch (select) {
            case "preview" : {
                try {
                    mgr.preview(request, response);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
                break;
            }
            case "next" : {
                try {
                    mgr.next(request, response);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
                break;
            }
        }
    }
}
