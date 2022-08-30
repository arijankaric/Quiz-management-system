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
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@WebServlet(name = "/GetTwoQuizzesServlet", urlPatterns = "/api/random")
public class GetTwoQuizzesServlet extends HttpServlet {
    QuizService quizService = new QuizService(new QuizDao());

    public GetTwoQuizzesServlet() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Quiz> quizzes = quizService.getRandomQuizzes();
        @SuppressWarnings("unchecked")
        ArrayList<Integer> ids = (ArrayList<Integer>) request.getSession().getAttribute("ids");
        if (ids == null || ids.size() + 1 >= quizzes.size()) {
            ids = new ArrayList<>();
        }

        Random random = new Random();
        Quiz quizOne = quizzes.get(random.nextInt(quizzes.size()));
        Quiz quizTwo = quizOne;

        int counter = 0;
        while (quizTwo.equals(quizOne) && counter < 100) {
            quizTwo = quizzes.get(random.nextInt(quizzes.size()));
            ++counter;
        }

        if (counter == 100) {
            response.setStatus(500);
            return;
        } else {
            response.setStatus(200);
        }

        if (ids.isEmpty()) {
            ids.add(quizOne.getId());
        } else {
            while (ids.contains(quizOne.getId())) {
                quizOne = quizzes.get(random.nextInt(quizzes.size()));
            }
            ids.add(quizOne.getId());
            while (ids.contains(quizTwo.getId())) {
                quizTwo = quizzes.get(random.nextInt(quizzes.size()));
            }
        }
        ids.add(quizTwo.getId());
        request.getSession().setAttribute("quiz1", quizOne.toString());
        request.getSession().setAttribute("quiz2", quizTwo.toString());
        request.getSession().setAttribute("ids", ids);
        System.out.println(ids);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    }
}
