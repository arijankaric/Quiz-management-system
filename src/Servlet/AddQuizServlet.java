package Servlet;


import dao.AnswerDao;
import dao.QuestionDao;
import dao.QuizDao;
import dao.UserDao;
import Model.Answer;
import Model.Question;
import Model.Quiz;
import Model.User;
import service.AnswerService;
import service.QuestionService;
import service.QuizService;
import service.UserService;

import org.eclipse.persistence.exceptions.JSONException;
import org.json.JSONArray;
import org.json.JSONObject;

import com.google.gson.Gson;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "/AddQuizServlet", urlPatterns = "/admin/addQuiz")
public class AddQuizServlet extends HttpServlet {
    QuestionService questionService = new QuestionService(new QuestionDao());
    AnswerService answerService = new AnswerService(new AnswerDao());
    UserService userService = new UserService(new UserDao());
    QuizService quizService = new QuizService(new QuizDao());

    public AddQuizServlet() {
        super();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, JSONException  {
        String data = request.getParameter("data");
        System.out.println(data);

//        Gson obj = new Gson();
//        Quiz quiz = obj.fromJson(data, Quiz.class); //("title");
//        User user = userService.findByUserId(quiz.getOwner());
//        Quiz containsQuiz = user.getQuizzes().stream().filter(quiz -> quizTitle.equals(quiz.getTitle())).findFirst().orElse(null);
//        if (containsQuiz == null) {
//            Quiz quiz = new Quiz();
//            quiz.setTitle(quizTitle);
//            quiz.setDescription(obj.getString("description"));
//            quiz.setActive(obj.getBoolean("active"));
//            quiz.setImageUrl(obj.getString("imageUrl"));
//            quiz.setOwner(user);
//            quizService.createQuiz(quiz);
//
//            JSONArray questions = obj.getJSONArray("questions");
//            for (int i = 0; i < questions.length(); i++) {
//                Question question = new Question();
//                JSONArray answers = questions.getJSONObject(i).getJSONArray("answers");
//                question.setTitle(questions.getJSONObject(i).getString("title"));
//                question.setTimeToAnswer(Integer.parseInt(questions.getJSONObject(i).getString("timeToAnswer")));
//                question.setScore(Integer.parseInt(questions.getJSONObject(i).getString("score")));
//                question.setQuiz(quiz);
//                questionService.createQuestion(question);
//
//                for (int j = 0; j < answers.length(); j++) {
//                    Answer answer = new Answer();
//                    answer.setTitle(answers.getJSONObject(j).getString("title"));
//                    answer.setCorrect(answers.getJSONObject(j).getBoolean("isCorrect"));
//                    answer.setQuestion(question);
//                    answerService.createAnswer(answer);
//                }
//
//            }
//
//            System.out.println("Yey you did it :)");
//            response.setStatus(200);
//        } else {
//            response.sendError(400, "User already has a quiz with that name");
//        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    }
}
