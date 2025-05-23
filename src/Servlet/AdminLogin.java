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

import com.google.gson.Gson;

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
		

		
		Boolean logout = Boolean.valueOf(request.getParameter("logout"));
		if (logout != null && logout.equals(true)) 
		{
            request.getSession().invalidate();
        }
		
		HttpSession session = request.getSession(false);
		
		if(session != null)
		{
			Boolean loggedIn = (Boolean) session.getAttribute("loggedIn");
			if(loggedIn != null && loggedIn.equals(true))
			{
                response.sendRedirect("./admin/index");
			}
		}
		request.setAttribute("typeOfForm", "admin");
		request.getRequestDispatcher("doubleLogin.jsp").include(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
//		doGet(request, response);
		System.out.println("doPost AdminLogin Start");
		String username = request.getParameter("username");
        String password = request.getParameter("password");
        Map<String, String> messages = new HashMap<>();
//        String text;
//        boolean flag = false;  

        if (username == null || username.isEmpty()) {
        	System.out.println("Username is null/empty");
            messages.put("username", "Please enter a username");
    		messages.put("success", "false");
        }

        if (password == null || password.isEmpty()) {
        	System.out.println("Password is null/empty");
            messages.put("password", "Please enter a password");
    		messages.put("success", "false");
        }
        
        if (messages.isEmpty()) {
            User user = userService.authenticate(username, password);
            if (user != null) {
                if (user.getRole() == 1 || user.getRole() == 2) {
                	System.out.println("Successful login");
            		HttpSession session = request.getSession(true);

                    session.setAttribute("user", user);
                    session.setAttribute("username", user.getUsername());
            		session.setAttribute("loggedIn", true);
            		session.setAttribute("role", user.getRole());
//                    response.sendRedirect("./admin/index");
//            		flag = true;
            		messages.put("success", "true");
                } else {
                	System.out.println("Pending admin can't login, yet");
            		messages.put("success", "false");
                    messages.put("login", "You are not admin. Only admin can login to admin panel.");
                }
            } else {
            	System.out.println("User wasn't found in db");
        		messages.put("success", "false");
                messages.put("login", "Unknown user, please try again");
            }
        }
        request.setAttribute("messages", messages);
//        request.getRequestDispatcher("/doubleLogin.jsp").include(request, response);
        String json = new Gson().toJson(messages);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(json);
		
//        String json = new Gson().toJson(messages);
        
//        response.setContentType("application/json");
//        response.setCharacterEncoding("UTF-8");
//        response.getWriter().write(json);
		
//		request.getParameter("userToRemove");
		System.out.println("doPost AdminLogin End");
	}

}