package Servlet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;
import org.json.JSONObject;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import Model.Question;
import Model.Quiz;
import dao.QuizDao;
import service.QuizService;

/**
 * Servlet implementation class FetchQuestions
 */
@WebServlet(name = "/FetchQuestions", urlPatterns = "/fetchQuestions") // and answers
public class FetchQuestions extends HttpServlet {
	private static final long serialVersionUID = 1L;
	 QuizService quizService = new QuizService(new QuizDao());
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public FetchQuestions() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("doGet FetchQuestions start");
		Enumeration<String> parNames = request.getParameterNames();
		while(parNames.hasMoreElements()) {
			System.out.println(parNames.nextElement());
		}
		String str = request.getParameter("id");
		System.out.println(str);
		int id = Integer.valueOf(str);
	    Quiz quiz = quizService.getQuizById(id);
	    
	    String jsonInString = new Gson().toJson(quiz);
	    try {
			JSONObject mJSONObject = new JSONObject(jsonInString);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	    
	    System.out.println("Json quiz: " + jsonInString);    
	    
	    System.out.println("quiz: " + quiz.toString());	    
	    PrintWriter out = response.getWriter();
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        System.out.println("returning jsonInString");
        out.print(jsonInString);
        out.flush();
		System.out.println("doGet FetchQuestions end");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {		
		
	}

}
