package Servlet;

import dao.QuizDao;
import dao.ResultDao;
import Model.Quiz;
import Model.Result;
import service.QuizService;
import service.ResultService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "/SaveResultServlet", urlPatterns = "/api/save-results")
public class SaveResultServlet extends HttpServlet {
    ResultService resultService = new ResultService(new ResultDao());
    QuizService quizService = new QuizService(new QuizDao());

    public SaveResultServlet() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String quizStrId = request.getParameter("id");

        if (quizStrId != null) {
            String name = request.getParameter("name");
            String lastname = request.getParameter("lastname");
            String email = request.getParameter("email");
            String score = request.getParameter("score");
            Quiz quizOne = quizService.getQuizById(Integer.parseInt(quizStrId));
            Result playerOneResult = new Result();
            playerOneResult.setUserSurname(lastname);
            playerOneResult.setUserName(name);
            playerOneResult.setUserEmail(email);
            playerOneResult.setScore(Integer.parseInt(score));
            playerOneResult.setQuiz(quizOne);
            resultService.saveResultToDB(playerOneResult);
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    }
}
