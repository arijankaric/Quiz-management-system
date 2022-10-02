package Servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import Model.User;
import dao.UserDao;
import service.UserService;

/**
 * Servlet implementation class UserSignUp
 */
@WebServlet(name = "/UserSignUp", urlPatterns = "/userSignUp")
public class UserSignUp extends HttpServlet {
	private static final long serialVersionUID = 1L;
	UserService userService = new UserService(new UserDao());
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UserSignUp() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("SignUp being");
		String username = request.getParameter("username");
		System.out.println("Username: " + username);
		String password = request.getParameter("password");
		System.out.println("Password: " + password);
		int role = Integer.valueOf(request.getParameter("role"));
		System.out.println("role: " + role);

		User user = new User(username, password, 3);
		userService.create(user);

		String resp = "1";

		response.setContentType("text/plain");
		response.getWriter().write(resp);
		System.out.println("SignUp end");
	}

}
