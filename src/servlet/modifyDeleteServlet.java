package servlet;

import service.rvdkdMgr;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/modifyDeleteServlet")
public class modifyDeleteServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        rvdkdMgr mgr = new rvdkdMgr();
        String select = request.getParameter("mdselect");

        switch (select) {
            case "modify" :  {
                try {
                    mgr.modify(request, response);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
                break;
            }
            case "delete" :  {
                try {
                    mgr.deleteBoard(request, response);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
                break;
            }
        }



    }
}
