package Servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.AnswerDao;
import dao.QuestionDao;
import service.AnswerService;
import service.QuestionService;

/**
 * Servlet implementation class DeleteAnswer
 */
@WebServlet(name = "/DeleteAnswer", urlPatterns = "/admin/deleteAnswer")
public class DeleteAnswer extends HttpServlet {
	private static final long serialVersionUID = 1L;
	AnswerService answerService = new AnswerService(new AnswerDao());
    QuestionService questionService = new QuestionService(new QuestionDao());
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DeleteAnswer() {
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
		System.out.println("Delete start");

        int id = Integer.parseInt(request.getParameter("id"));
        answerService.removeAnswerById(id);

        String resp = "1";

        response.setContentType("text/plain");
        response.getWriter().write(resp);
        System.out.println("Delete end");
	}

}
