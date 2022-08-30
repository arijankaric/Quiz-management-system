package Servlet;

import dao.UserDao;
import service.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "/DeleteUserServlet", urlPatterns = "/admin/deleteUser")
public class DeleteUserServlet extends HttpServlet {
    UserService userService = new UserService(new UserDao());

    public DeleteUserServlet() {
        super();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        userService.removeUser(request.getParameter("userToRemove"));
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    }
}
