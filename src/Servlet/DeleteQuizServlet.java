package Servlet;

import dao.QuizDao;
import service.QuizService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "/DeleteQuizServlet", urlPatterns = "/admin/deleteQuiz")
public class DeleteQuizServlet extends HttpServlet {
    QuizService quizService = new QuizService(new QuizDao());

    public DeleteQuizServlet() {
        super();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        quizService.removeQuizById(Integer.parseInt(request.getParameter("quizId")));
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    }
}
