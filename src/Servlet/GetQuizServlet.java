package Servlet;

import dao.QuizDao;
import Model.Quiz;
import service.QuizService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "/GetQuizServlet", urlPatterns = "/api/quiz")
public class GetQuizServlet extends HttpServlet {
    QuizService quizService = new QuizService(new QuizDao());

    public GetQuizServlet() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String quizIDString = request.getParameter("quiz_id");
        if (quizIDString != null) {
            Quiz quiz = quizService.getQuizById(Integer.parseInt(quizIDString));
            request.getSession().setAttribute("quiz", quiz.toString());
        }
        response.sendRedirect("/quiz.jsp");
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    }
}
