package Servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.Map;

import Socket.WebSocket;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class ClientLogin
 */
@WebServlet(name = "/ClientLogin", urlPatterns = "/clientLogin")
public class ClientLogin extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private HttpServletResponse response;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ClientLogin() {
        super();
        // TODO Auto-generated constructor stub
    }
    
    private void respond(String str) throws IOException {
    	response.setContentType("text/plain");
    	PrintWriter pw = response.getWriter();
        pw.write(str.toString());
        pw.close();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
//		response.getWriter().append("Served at: ").append(request.getContextPath());
//        response.sendRedirect("admin/index");
		this.response = response;
		
		System.out.println("doGet ClientLogin begin");
		
//		Enumeration<String> parNames = request.getParameterNames();
		Map<String, String[]> parMap  = request.getParameterMap();
		Enumeration<String> attrNames  = request.getAttributeNames();
		System.out.println("attrNames.hasMoreElements(): " + attrNames.hasMoreElements());
		System.out.println("parMap: " + parMap);
		System.out.println("size: " + parMap.size());
		if(parMap == null || parMap.size() == 0) {
			System.out.println("parMap == null || parMap == empty");
			request.setAttribute("typeOfForm", "client");
			request.getRequestDispatcher("doubleLogin.jsp").include(request, response);
			return;
		}	
		
		Iterator<Map.Entry<String, String[]>> itr = parMap.entrySet().iterator();
        
        while(itr.hasNext())
        {
             Map.Entry<String, String[]> entry = itr.next();
             System.out.println("Key = " + entry.getKey() + 
                                 ", Value = " + entry.getValue().toString());
        }
		
//		while(parNames.hasMoreElements()) {
//			System.out.println(parNames.nextElement() + ": " + parNames.toString());
//		}

		String quizCode = request.getParameter("quizCode");
		if(quizCode == null) {
			respond("0");
			return;
		}
		boolean quizCodeExists = Socket.WebSocket.isQuizCodeValid(quizCode);
		if(quizCodeExists) {
			String nickname = request.getParameter("nickname");
			System.out.println("nickname: " + nickname);
			System.out.println("quizCode: " + quizCode);
			HttpSession session=request.getSession(true);
			session.setAttribute("quizCode", quizCode);
			session.setAttribute("nickname", nickname);
			respond("1");
			return;
		}
		String nickname = request.getParameter("nickname");
		System.out.println("nickname: " + nickname);
		System.out.println("quizCode: " + quizCode);
		respond("0");
		
		System.out.println("doGet ClientLogin end");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
//		doGet(request, response);
	}

}
