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

@WebServlet(name = "/AddUserServlet", urlPatterns = "/admin/addUser")
public class AddUserServlet extends HttpServlet {

    UserService userService = new UserService(new UserDao());

    public AddUserServlet() {
        super();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        User user = new User();
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

        if (existingUser != null) {
            message.put("userExists", "User already exists");
        }

        if (message.isEmpty()) {
            user.setUsername(username);
            user.setPassword(Util.SecurityUtil.hashPassword(password));
            user.setRole(Integer.parseInt(role));
            userService.create(user);
            response.addHeader("Access-Control-Allow-Origin", "*");
            response.addHeader("Access-Control-Allow-Methods", "GET,POST");
            message.put("successEdit", "Add was successful!");
        }
        request.setAttribute("userAddMessage", message);
        request.getRequestDispatcher("/admin/admin.jsp").forward(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    }
}
