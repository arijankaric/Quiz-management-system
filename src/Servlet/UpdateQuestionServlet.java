package Servlet;

import dao.AnswerDao;
import dao.QuestionDao;
import Model.Answer;
import Model.Question;
import service.AnswerService;
import service.QuestionService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet(name = "/UpdateQuestionServlet", urlPatterns = "/admin/updateQuestion")
public class UpdateQuestionServlet extends HttpServlet {
    QuestionService questionService = new QuestionService(new QuestionDao());
    AnswerService answerService = new AnswerService(new AnswerDao());

    public UpdateQuestionServlet() {
        super();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int quizId = Integer.parseInt(request.getParameter("quizId"));
        int questionId = Integer.parseInt(request.getParameter("questionId"));
        String questionTitle = request.getParameter("questionTitle");
        int questionTimeToAnswer = Integer.parseInt(request.getParameter("questionTimeToAnswer"));
        int questionScore = Integer.parseInt(request.getParameter("questionScore"));
        List<String> newAnswers = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            if (request.getParameter("answer" + (i + 1)) != null) {
                newAnswers.add(request.getParameter("answer" + (i + 1)));
            }
        }
        List<Boolean> newAnswerStatuses = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            System.out.println("answer" + (i + 1) + "Correct");
            if (request.getParameter("answer" + (i + 1) + "Correct") != null) {
                newAnswerStatuses.add(true);
            } else {
                newAnswerStatuses.add(false);
            }
        }

        Map<String, String> message = new HashMap<>();
        System.out.println(newAnswers);
        System.out.println(newAnswerStatuses);

        if (newAnswers.get(0).isBlank() && newAnswers.get(1).isBlank()
                && newAnswers.get(2).isBlank() && newAnswers.get(3).isBlank()) {
            questionService.removeQuestionById(questionId);
            message.put("successEdit", "The question was deleted because you deleted all answers");
        } else {
            questionService.updateQuestion(questionId, questionTitle, questionTimeToAnswer, questionScore);
            Question question = questionService.getQuestionById(questionId);
            List<Answer> answers = question.getAnswers();
            int i = 0;
            while (i < answers.size()) {
                answerService.shouldUpdateOrRemove(newAnswers.get(i), newAnswerStatuses.get(i), answers.get(i).getId(), questionId);
                ++i;
            }
            if (i < 4) {
                if (!newAnswers.get(i).isBlank()) {
                    Answer answer = new Answer();
                    answer.setQuestion(question);
                    answer.setTitle(newAnswers.get(i));
                    answer.setCorrect(newAnswerStatuses.get(i));
                    answerService.createAnswer(answer);
                }
                ++i;
            }
            message.put("successEdit", "The question was updated");
        }

        request.setAttribute("userUpdateMessage", message);
        request.getRequestDispatcher("/admin/admin.jsp").forward(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    }
}
