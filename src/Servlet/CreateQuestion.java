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
 * Servlet implementation class CreateQuestion
 */
@WebServlet(name = "/CreateQuestion", urlPatterns = "/admin/createQuestion")
public class CreateQuestion extends HttpServlet {
	private static final long serialVersionUID = 1L;
	QuizService quizService = new QuizService(new QuizDao());
    QuestionService questionService = new QuestionService(new QuestionDao());
	AnswerService answerService = new AnswerService(new AnswerDao());

    private void createDummyAnswers(Question question) {
    	Answer answer;
    	final String titleStr = "answer";
    	String tmpStr;
    	boolean tmpBool = false;
    	for(int i = 1; i < 5; ++i) {
    		tmpStr = titleStr + i;
    		answer = new Answer(tmpStr, tmpBool, question);
    		answerService.createAnswer(answer);
    		tmpBool = !tmpBool;
    	}
    }
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CreateQuestion() {
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
		System.out.println("Add start");
//		String title, int score, int timeToAnswer, Quiz quiz
//      action: "add",
//              content: content,
//              score: score,
//              time: time,
//              quizId: quizId

      String content = request.getParameter("content");
      System.out.println("Content: " + content);
      int score = Integer.parseInt(request.getParameter("score"));
      System.out.println("Score: " + score);
      int time = Integer.parseInt(request.getParameter("time"));
      System.out.println("Time: " + time);
      int id = Integer.parseInt(request.getParameter("quizId"));
      System.out.println("quizId: " + id);

      Quiz quiz = quizService.getQuizById(id);
      System.out.println("numOfQuestions: " + quiz.getQuestions().size());
      Question question = new Question(content, score, time, quiz, quiz.getQuestions().size() + 1);
      questionService.createQuestion(question);
      createDummyAnswers(question);

      String resp = "1";
      
      response.setContentType("text/plain");
      response.getWriter().write(resp);
      System.out.println("Add end");
	}

}
