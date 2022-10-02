package Servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class clientQuiz
 */
//@WebServlet(asyncSupported = true, urlPatterns = { "/clientQuiz" })
@WebServlet(name = "/ClientQuiz", urlPatterns = "/clientQuiz")
public class ClientQuiz extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ClientQuiz() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
//		response.getWriter().append("Served at: ").append(request.getContextPath());
		System.out.println("doGet ClientQuiz being");
		HttpSession session=request.getSession(false);
		
		if(session == null) {
			session=request.getSession(true);
			String quizCode = request.getParameter("quizCode"); // get
			String nickname = request.getParameter("nickname");
			session.setAttribute("quizCode", quizCode);
			session.setAttribute("nickname", nickname);
		}		
		
		request.getRequestDispatcher("clientQuiz.jsp").include(request, response);
		System.out.println("doGet ClientQuiz end");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
