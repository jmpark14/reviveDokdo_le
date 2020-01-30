package servlet;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/checkCapServlet")
public class checkCapServlet extends HttpServlet {
    protected void doPost (HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession();
        String mycap = request.getParameter("cap");
        String cap = (String)session.getAttribute("cta");
        PrintWriter out = response.getWriter();

        if (mycap.equals(cap)) {
            session.removeAttribute("cta");
            out.print("success");
        } else {
            out.print("fail");
        }
    }

}
