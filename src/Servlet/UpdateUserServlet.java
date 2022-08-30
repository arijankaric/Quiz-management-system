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

@WebServlet(name = "/UpdateUserServlet", urlPatterns = "/admin/updateUser")
public class UpdateUserServlet extends HttpServlet {
    UserService userService = new UserService(new UserDao());

    public UpdateUserServlet() {
        super();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String oldUserName = request.getParameter("inputoldusername");
        String username = request.getParameter("inputusername");
        String password = request.getParameter("inputpassword");
        String role = request.getParameter("inputrole");

        Map<String, String> message = new HashMap<>();

        if (username == null || username.isEmpty()) {
            message.put("username", "Please enter a username");
        }

        if (password == null || password.isEmpty()) {
            message.put("password", "Please enter a password");
        }

        User existingUser = userService.findByUsername(username);

        if (existingUser != null && !username.equals(oldUserName)) {
            message.put("userExists", "User already exists try another userName");
        }

        if (message.isEmpty()) {
            userService.updateUser(oldUserName, username, Util.SecurityUtil.hashPassword(password), Integer.parseInt(role));
            message.put("successEdit", "Update was successful!");
        }
        request.setAttribute("userUpdateMessage", message);
        request.getRequestDispatcher("/admin/admin.jsp").forward(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    }
}
