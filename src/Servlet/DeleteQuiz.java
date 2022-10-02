package Servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import Model.Answer;
import Model.Question;
import Model.Quiz;
import dao.AnswerDao;
import dao.QuestionDao;
import dao.QuizDao;
import service.AnswerService;
import service.QuestionService;
import service.QuizService;

/**
 * Servlet implementation class DeleteQuiz
 */
@WebServlet(name = "/DeleteQuiz", urlPatterns = "/admin/deleteQuiz")
public class DeleteQuiz extends HttpServlet {
	private static final long serialVersionUID = 1L;
	QuizService quizService = new QuizService(new QuizDao());
	QuestionService questionService = new QuestionService(new QuestionDao());
	AnswerService answerService = new AnswerService(new AnswerDao());

    /**
     * @see HttpServlet#HttpServlet()
     */
    public DeleteQuiz() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
//		response.getWriter().append("Served at: ").append(request.getContextPath());
		int quizId = Integer.getInteger(request.getParameter("id")); // get
		
		response.sendRedirect("quizzes");

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("Delete start");

        int quizId = Integer.parseInt(request.getParameter("id"));
        Quiz quiz = quizService.getQuizById(quizId);
        System.out.println("quiz.getQuestions().size():" + quiz.getQuestions().size());
		for(int i = 0; i < quiz.getQuestions().size(); ++i) {
			Question question = quiz.getQuestions().get(i);
			System.out.println("question.getAnswers().size():" + question.getAnswers().size());
			for(int j = 0; j < question.getAnswers().size(); ++j) {
				Answer answer = question.getAnswers().get(j);
				answerService.removeAnswerById(answer.getId());
			}
			questionService.removeQuestionById(question.getId());
		}
		quizService.removeQuizById(quizId);

        String resp = "1";

        response.setContentType("text/plain");
        response.getWriter().write(resp);
        System.out.println("Delete end");
	}

}
