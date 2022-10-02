package Servlet;

import java.io.BufferedReader;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;
import org.json.JSONObject;

import com.google.gson.Gson;

import Model.Question;
import Model.User;
import dao.QuestionDao;
import dao.UserDao;
import service.QuestionService;
import service.UserService;

/**
 * Servlet implementation class UpdateUser
 */
@WebServlet(name = "/UpdateUser", urlPatterns = "/admin/updateUser")
public class UpdateUser extends HttpServlet {
	private static final long serialVersionUID = 1L;
    UserService userService = new UserService(new UserDao());
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UpdateUser() {
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
		// TODO Auto-generated method stub
//		doGet(request, response);
		System.out.println("doPost UpdateUser start");
//		StringBuilder sb = new StringBuilder();
//	    BufferedReader br = request.getReader();
//	    String str;
//	    while( (str = br.readLine()) != null ){
//	        sb.append(str);
//	    }  
		int id = Integer.valueOf(request.getParameter("id")); 
	    int role = Integer.valueOf(request.getParameter("role")); 
	    String username = request.getParameter("username");
	    String password = request.getParameter("password");
	    
//	    System.out.println(sb.toString());
//	    JSONObject jsonObject;
//		try {
//			jsonObject = new JSONObject(sb.toString());
//			oldUserName = jsonObject.getString("oldUserName");
//		    newName = jsonObject.getString("name");
//		    roleStr = jsonObject.getString("role");
//		    pass = jsonObject.getString("pass");
//		} catch (JSONException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
	    
//	    int roleInt;
//	    if(roleStr.equals("Super-admin"))
//	    {
//	    	roleInt = 1;
//	    }
//	    else if(roleStr.equals("admin"))
//	    {
//	    	roleInt = 2;
//	    }
//	    else 
//	    {
//	    	roleInt = 3;
//	    }
	    User user = userService.findByUserId(id);
	    if(password == null) {
	    	password = user.getPassword();
	    }
	    userService.updateUser(id, username, password, role);

//	    User user = gson.fromJson(sb.toString(), User.class);
//	    System.out.println("User: " + user);
	    
	    String resp = "1";

		response.setContentType("text/plain");
		response.getWriter().write(resp);
		System.out.println("doPost UpdateUser end");
	}

}
