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
import Model.User;
import dao.AnswerDao;
import dao.QuestionDao;
import dao.QuizDao;
import dao.UserDao;
import service.AnswerService;
import service.QuestionService;
import service.QuizService;
import service.UserService;

/**
 * Servlet implementation class DeleteUser
 */
@WebServlet(name = "/DeleteUser", urlPatterns = "/admin/deleteUser")
public class DeleteUser extends HttpServlet {
	private static final long serialVersionUID = 1L;
	QuizService quizService = new QuizService(new QuizDao());
	QuestionService questionService = new QuestionService(new QuestionDao());
	AnswerService answerService = new AnswerService(new AnswerDao());
	UserService userService = new UserService(new UserDao());
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DeleteUser() {
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

        int userId = Integer.parseInt(request.getParameter("id"));
        User user = userService.findByUserId(userId);
        
        for (int k = 0; k < user.getQuizzes().size(); ++k) {
        	Quiz quiz = user.getQuizzes().get(k);
        	for(int i = 0; i < quiz.getQuestions().size(); ++i) {
    			Question question = quiz.getQuestions().get(i);
    			for(int j = 0; j < question.getAnswers().size(); ++j) {
    				Answer answer = question.getAnswers().get(j);
    				answerService.removeAnswerById(answer.getId());
    			}
    			questionService.removeQuestionById(question.getId());
    		}
    		quizService.removeQuizById(quiz.getId());
        }
        userService.removeUser(user.getUsername());
		

        String resp = "1";

        response.setContentType("text/plain");
        response.getWriter().write(resp);

        System.out.println("Delete end");
	}

}
