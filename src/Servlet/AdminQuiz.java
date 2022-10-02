package Servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class adminQuiz
 */
@WebServlet(name = "/AdminQuiz", urlPatterns = "/admin/quiz")
public class AdminQuiz extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AdminQuiz() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
//		response.getWriter().append("Served at: ").append(request.getContextPath());
		System.out.println("doGet AdminQuiz being");
		HttpSession session = request.getSession(false);
//		Integer quizId = (Integer) request.getAttribute("id"); // post
		Integer quizId = Integer.parseInt(request.getParameter("id")); // get
		System.out.println("quizId: " + quizId);
		session.setAttribute("quizId", quizId);
		session.setAttribute("moderatingQuiz", true);
		request.getRequestDispatcher("adminQuiz.jsp").include(request, response);
		System.out.println("doGet AdminQuiz end");

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
