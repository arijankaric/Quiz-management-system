package Servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import Model.Question;
import dao.AnswerDao;
import dao.QuestionDao;
import dao.QuizDao;
import service.AnswerService;
import service.QuestionService;
import service.QuizService;

/**
 * Servlet implementation class DeleteQuestion
 */
@WebServlet(name = "/DeleteQuestion", urlPatterns = "/admin/deleteQuestion")
public class DeleteQuestion extends HttpServlet {
	private static final long serialVersionUID = 1L;
	QuizService quizService = new QuizService(new QuizDao());
    QuestionService questionService = new QuestionService(new QuestionDao());
    AnswerService answerService = new AnswerService(new AnswerDao());
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DeleteQuestion() {
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
        Question question = questionService.getQuestionById(id);
        for(int i = 0; i < question.getAnswers().size(); ++i) {
        	int answerId  = question.getAnswers().get(i).getId();
        	answerService.removeAnswerById(answerId);
        }
        questionService.removeQuestionById(id);

        String resp = "1";

        response.setContentType("text/plain");
        response.getWriter().write(resp);
        System.out.println("Delete end");
	}

}
