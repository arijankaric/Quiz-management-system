package Servlet;

import dao.QuizDao;
import service.QuizService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@WebServlet(name = "/Update", urlPatterns = "/admin/updateQuiz")
public class UpdateQuizServlet extends HttpServlet {
    QuizService quizService = new QuizService(new QuizDao());

    public UpdateQuizServlet() {
        super();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int quizUserIdParam = Integer.parseInt(request.getParameter("quizUserIdParam"));

        String quizTitleParam = request.getParameter("quizTitleParam");
        String quizDescriptionParam = request.getParameter("quizDescriptionParam");
        String quizImageUrlParam = request.getParameter("quizImageUrlParam");
        boolean quizIsActiveParam = true;
        String isActiveParam = request.getParameter("quizIsActiveParam");
        System.out.println("Active" + isActiveParam);
        if (isActiveParam == null) {
            quizIsActiveParam = false;
        }
        int quizIdParam = Integer.parseInt(request.getParameter("quizIdParam"));

        System.out.println("set on " + quizIsActiveParam);
        quizService.updateQuiz(quizTitleParam,
                quizDescriptionParam,
                quizImageUrlParam,
                quizIsActiveParam,
                quizIdParam);

        Map<String, String> message = new HashMap<>();
        message.put("successEdit", "The quiz was updated");
        request.setAttribute("userUpdateMessage", message);
        request.getRequestDispatcher("/admin/admin.jsp").forward(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
