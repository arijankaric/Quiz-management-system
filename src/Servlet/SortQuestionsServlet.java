package Servlet;

import dao.AnswerDao;
import dao.QuestionDao;
import dao.QuizDao;
import Model.Answer;
import Model.Question;
import Model.Quiz;
import service.AnswerService;
import service.QuestionService;
import service.QuizService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "/SortQuestionsServlet", urlPatterns = "/admin/sortQuestions")
public class SortQuestionsServlet extends HttpServlet {
    QuizService quizService = new QuizService(new QuizDao());
    QuestionService questionService = new QuestionService(new QuestionDao());
    AnswerService answerService = new AnswerService(new AnswerDao());

    public SortQuestionsServlet() {
        super();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int quizId = Integer.parseInt(request.getParameter("quizId"));
        System.out.println(quizId);
        String indexes = request.getParameter("indexes");
        System.out.println(indexes);
        String[] myNumbers = indexes.split(",");
        Quiz quiz = quizService.getQuizById(quizId);
        List<Question> list = quiz.getQuestions();
        for (int i = 0; i < myNumbers.length; ++i) {
            if (i != Integer.parseInt(myNumbers[i])) {
                questionService.updateQuestion(
                        list.get(i).getId(),
                        list.get(Integer.parseInt(myNumbers[i])).getTitle(),
                        list.get(Integer.parseInt(myNumbers[i])).getTimeToAnswer(),
                        list.get(Integer.parseInt(myNumbers[i])).getScore());
                Question oldQuestion = questionService.getQuestionById(list.get(i).getId());
                List<Answer> oldAnswers = list.get(i).getAnswers();
                List<Answer> newAnswers = list.get(Integer.parseInt(myNumbers[i])).getAnswers();

                int j = 0;
                if (oldAnswers.size() == newAnswers.size()){
                    while (j < oldAnswers.size()){
                        answerService.updateAnswer(
                                newAnswers.get(j).getTitle(),
                                newAnswers.get(j).isCorrect(),
                                oldAnswers.get(j).getId(),
                                oldQuestion.getId());
                        ++j;
                    }
                } else if (oldAnswers.size() < newAnswers.size()) {
                    while (j < oldAnswers.size()) {
                        answerService.updateAnswer(
                                newAnswers.get(j).getTitle(),
                                newAnswers.get(j).isCorrect(),
                                oldAnswers.get(j).getId(),
                                oldQuestion.getId());
                        ++j;
                    }
                    while (j < newAnswers.size()) {
                        Answer answer = new Answer();
                        answer.setTitle(newAnswers.get(j).getTitle());
                        answer.setCorrect(newAnswers.get(j).isCorrect());
                        answer.setQuestion(oldQuestion);
                        answerService.createAnswer(answer);
                        ++j;
                    }
                } else {
                    while (j < newAnswers.size()) {
                        answerService.updateAnswer(
                                newAnswers.get(j).getTitle(),
                                newAnswers.get(j).isCorrect(),
                                oldAnswers.get(j).getId(),
                                oldQuestion.getId());
                        ++j;
                    }
                    while (j < oldAnswers.size()) {
                        answerService.removeAnswerById(oldAnswers.get(j).getId());
                        ++j;
                    }
                }
            }
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
