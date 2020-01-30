package servlet;

import service.rvdkdMgr;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/loginServlet")
public class loginServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String uid = request.getParameter("uid");
        String pwd = request.getParameter("pwd");
        PrintWriter out = response.getWriter();
        HttpSession session = request.getSession();
        System.out.println("doPost" + uid + "," + "pwd");
        int isLogin = -1;

        rvdkdMgr mgr = new rvdkdMgr();

        try {
            isLogin = mgr.login(uid, pwd);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        if (isLogin == 1) {
            out.print("success");
            session.setAttribute("sessionID", uid);
        }
        else {
            out.print("fail");
        }
    }
}
