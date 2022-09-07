package Servlet;

import dao.UserDao;
import Model.User;
import service.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@WebServlet(name = "/Login", urlPatterns = "/admin/login")
public class Login extends HttpServlet {
    UserService userService = new UserService(new UserDao());

    public Login() {
        super();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        if (request.getParameter("logout") != null) {
            request.getSession().invalidate();
        }
        response.sendRedirect("/admin/login.jsp");
        // bug lol request.getRequestDispatcher("/admin/login.jsp").forward(request, response);
    }
}
