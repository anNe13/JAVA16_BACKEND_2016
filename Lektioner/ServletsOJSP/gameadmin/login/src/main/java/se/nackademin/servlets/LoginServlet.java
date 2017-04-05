/**
 * Created by carl on 04/04/17.
 */
package se.nackademin.servlets;
import se.nackademin.backend.FakeDB;
import se.nackademin.domain.User;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class LoginServlet extends HttpServlet
{
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException
    {
        response.setContentType("text/html");
        String username=request.getParameter("username");
        String password=request.getParameter("password");
        PrintWriter writer = response.getWriter();
        writer.println("Från GET :");
        String message="Username is : "+ username + "<br/> Password is :" + password ;
        writer.println(message);
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException
    {
        HttpSession session = request.getSession();
        response.setContentType("text/html");
        String username=request.getParameter("username");
        String password=request.getParameter("password");
        User user = (User) session.getAttribute(username);
        PrintWriter writer = response.getWriter();
        if (user!=null && !user.isSessionTimedOut()){
            writer.println("<p style=\"color:red;\">Du är redan inloggad !!!</p>");
            return;
        }

        user = FakeDB.login(username, password);
        if (user!=null&& user.getPassword().equals(password)){
            writer.println("Välkommen du är nu inloggad :");
            writer.println("Användarnamn : "+ username + "<br/>");
            session.setAttribute(username, user);
        }else {
            writer.println("Fel användarnamn / lösenord");
        }
        // response.sendRedirect("/loggedin");
    }
}