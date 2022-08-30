package Servlet;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import Model.User;
import dao.UserDao;
import service.UserService;

/**
 * Servlet implementation class AdminLogin
 */
@WebServlet(name = "/AdminLogin", urlPatterns = "/adminLogin")
public class AdminLogin extends HttpServlet {
	private static final long serialVersionUID = 1L;
    UserService userService = new UserService(new UserDao());

       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AdminLogin() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
//		response.getWriter().append("Served at: ").append(request.getContextPath());
		
//		request.setAttribute("name", "Hussein Terek");
		
		
		if (request.getParameter("logout") != null) 
		{
            request.getSession().invalidate();
        }
		
		HttpSession session = request.getSession(false);
		
		
		if(session != null)
		{
			Boolean loggedIn = (Boolean) session.getAttribute("loggedIn");
			if(loggedIn != null && loggedIn == true)
			{
                response.sendRedirect("/admin/index");

//				request.getRequestDispatcher("adminIndex.jsp").forward(request, response);
			}
		}
		
//        response.sendRedirect("/adminLogin");
		request.getRequestDispatcher("adminLogin.jsp").include(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
//		doGet(request, response);
		
		String username = request.getParameter("username");
        String password = request.getParameter("password");
        Map<String, String> messages = new HashMap<>();

        if (username == null || username.isEmpty()) {
            messages.put("username", "Please enter a username");
        }

        if (password == null || password.isEmpty()) {
            messages.put("password", "Please enter a password");
        }

        if (messages.isEmpty()) {
            User user = userService.authenticate(username, password);
            if (user != null) {
                if (user.getRole() == 1 || user.getRole() == 2) {
            		HttpSession session = request.getSession(true);

                    session.setAttribute("user", user);
            		session.setAttribute("loggedIn", true);
                    response.sendRedirect("/admin/adminIndex");
                    return;
                } else {
                    messages.put("login", "You are not admin. Only admin can login to admin panel.");
                }
            } else {
                messages.put("login", "Unknown login, please try again");
            }
        }
        request.setAttribute("messages", messages);
        request.getRequestDispatcher("/adminLogin.jsp").include(request, response);
		
		
		
//		request.getParameter("userToRemove");
	}

}