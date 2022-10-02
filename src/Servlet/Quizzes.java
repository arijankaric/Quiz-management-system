package Servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import Model.Quiz;
import dao.QuizDao;
import service.QuizService;

/**
 * Servlet implementation class Quizzes
 */
@WebServlet(name = "/Quizzes", urlPatterns = "/admin/quizzes")
public class Quizzes extends HttpServlet {
	 QuizService quizService = new QuizService(new QuizDao());
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Quizzes() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
//		response.getWriter().append("Served at: ").append(request.getContextPath());
		System.out.println("doGet quizzes");
//		System.out.println(request.getParameter("page"));
//		Quiz quiz2 = quizService.getQuizById(2);
//		quizService.createQuiz(quiz2);
//		System.out.println(quizArray);
		
		List<Quiz> quizzes = quizService.getQuizzes();
		for(int i = 0; i < quizzes.size(); ++i)
		{
			System.out.println(quizzes.get(i));
		}
		System.out.println("poslije doGet quizzes");

		request.getRequestDispatcher("adminQuizzes.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
