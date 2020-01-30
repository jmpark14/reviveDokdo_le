package servlet;

import service.rvdkdMgr;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/checkIDServlet")
public class checkIDServlet extends HttpServlet {

    protected void doPost (HttpServletRequest request, HttpServletResponse response) throws IOException {
        String id = request.getParameter("uid");
        PrintWriter out = response.getWriter();
        int cnt = 0;
        rvdkdMgr mgr = new rvdkdMgr();
        try {
            cnt = mgr.checkid(id);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        if (cnt == 1) {
            out.print("success");
        }
        else {
            out.print("fail");
        }
    }

}
