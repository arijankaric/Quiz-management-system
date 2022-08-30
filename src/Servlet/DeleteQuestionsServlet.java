package Servlet;

import dao.QuestionDao;
import service.QuestionService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "/DeleteQuestionsServlet", urlPatterns = "/admin/deleteQuestion")
public class DeleteQuestionsServlet extends HttpServlet {
    QuestionService questionService = new QuestionService(new QuestionDao());

    public DeleteQuestionsServlet() {
        super();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        questionService.removeQuestionById(Integer.parseInt(request.getParameter("questionId")));
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
