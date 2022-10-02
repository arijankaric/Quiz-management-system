package Servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.QuestionDao;
import dao.QuizDao;
import service.QuestionService;
import service.QuizService;

/**
 * Servlet implementation class EditQuestion
 */
@WebServlet(name = "/EditQuestion", urlPatterns = "/admin/editQuestion")
public class EditQuestion extends HttpServlet {
	private static final long serialVersionUID = 1L;
	QuizService quizService = new QuizService(new QuizDao());
    QuestionService questionService = new QuestionService(new QuestionDao());

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public EditQuestion() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
//		response.getWriter().append("Served at: ").append(request.getContextPath());
		System.out.println("editQuestion doGet start");
//		System.out.println(request.getAttribute("id")); // post
		System.out.println(request.getParameter("id")); // get

		request.getRequestDispatcher("editQuestion.jsp").include(request, response);
		System.out.println("editQuestion doGet end");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println("Edit start");

		String content = request.getParameter("content");
		System.out.println("Content: " + content);
		int score = Integer.parseInt(request.getParameter("score"));
		System.out.println("Score: " + score);
		int time = Integer.parseInt(request.getParameter("time"));
		System.out.println("Time: " + time);
		int id = Integer.parseInt(request.getParameter("id"));
		System.out.println("Id: " + id);
		String ordinalNumberObject = request.getParameter("ordinalNumber");
		if(ordinalNumberObject != null) {
			Integer ordinalNumber = Integer.parseInt(ordinalNumberObject);
			questionService.updateQuestion(id, content, time, score, ordinalNumber);
		} else {
			questionService.updateQuestion(id, content, time, score);
		}

		String resp = "1";

		response.setContentType("text/plain");
		response.getWriter().write(resp);
		System.out.println("Edit end");
	}

}
