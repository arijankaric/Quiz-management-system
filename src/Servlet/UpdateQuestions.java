package Servlet;

import java.io.BufferedReader;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import Model.Question;
import dao.AnswerDao;
import dao.QuestionDao;
import service.AnswerService;
import service.QuestionService;

/**
 * Servlet implementation class UpdateQuestions
 */
@WebServlet(name = "/UpdateQuestions", urlPatterns = "/admin/updateQuestions")
public class UpdateQuestions extends HttpServlet {
	private static final long serialVersionUID = 1L;
    QuestionService questionService = new QuestionService(new QuestionDao());
    AnswerService answerService = new AnswerService(new AnswerDao());
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UpdateQuestions() {
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
		// TODO Auto-generated method stub
//		String jsonData =  request.getParameter("data");
		StringBuilder sb = new StringBuilder();
	    BufferedReader br = request.getReader();
	    String str;
	    while( (str = br.readLine()) != null ){
	        sb.append(str);
	    }    
	    Gson gson = new Gson(); 
	    System.out.println(sb.toString());
	    Question[] questionArray = gson.fromJson(sb.toString(), Question[].class);  
		System.out.println("jsonData: " + questionArray.toString());
		for (int i = 0; i < questionArray.length; i++) {
			  System.out.println(questionArray[i]);
			  questionService.updateQuestion(questionArray[i].getId(), questionArray[i].getTitle(), questionArray[i].getTimeToAnswer(), questionArray[i].getScore());
		}

//		doGet(request, response);
	}

}
