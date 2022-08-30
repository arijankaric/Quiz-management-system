package console;

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

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

public class BootstrapApp {

    public static void main(String[] args) {

        UserService userService = new UserService(new UserDao());
        QuizService quizService = new QuizService(new QuizDao());
        QuestionService questionService = new QuestionService(new QuestionDao());
        AnswerService answerService = new AnswerService(new AnswerDao());

        // Inserting users to DB
        try {
            FileReader fileReader = new FileReader("users.txt");
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String s;
            while (bufferedReader.read() != -1) {
                User user = new User();
                s = bufferedReader.readLine();
                System.out.println(s);
                String[] parts = s.split(";");
                user.setUsername(parts[0]);
                user.setPassword(parts[1]);
                user.setRole(Integer.parseInt(parts[2]));
                System.out.println(user.getUsername());
                userService.create(user);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("All users added");

        // Inserting quizzes to DB
        List<User> users = userService.getAllUsers();
        try {
            FileReader fileReader = new FileReader("quiz.txt");
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String s;
            while (bufferedReader.read() != -1) {
                Quiz quiz = new Quiz();
                s = bufferedReader.readLine();
                System.out.println(s);
                String[] parts = s.split(";");
                User user = users.stream().filter(user1 -> parts[4].equals(user1.getUsername())).findFirst().orElse(null);
                if (user != null) {
                    quiz.setTitle(parts[0]);
                    quiz.setDescription(parts[1]);
                    quiz.setActive(Boolean.parseBoolean(parts[2]));
                    quiz.setImageUrl(parts[3]);
                    quiz.setOwner(user);
                    quizService.createQuiz(quiz);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("All quizzes added");

        // Inserting questions to DB
        List<Quiz> quizzes = quizService.getQuizzes();
        try {
            FileReader fileReader = new FileReader("questions.txt");
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String s;
            while (bufferedReader.read() != -1) {
                s = bufferedReader.readLine();
                System.out.println(s);
                String[] parts = s.split(";");
                Quiz quiz = quizzes.stream().filter(quiz1 -> parts[0].equals(quiz1.getTitle())).findFirst().orElse(null);
                if (quiz != null) {
                    Question question = new Question();
                    question.setTitle(parts[1]);
                    question.setTimeToAnswer(Integer.parseInt(parts[2]));
                    question.setScore(Integer.parseInt(parts[3]));
                    question.setQuiz(quiz);
                    questionService.createQuestion(question);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("All questions for the quizzes added");

        // Inserting answers to DB
        List<Question> questions = questionService.getQuestions();
        try {
            FileReader fileReader = new FileReader("answers.txt");
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String s;
            while (bufferedReader.read() != -1) {
                s = bufferedReader.readLine();
                System.out.println(s);
                String[] parts = s.split(";");
                Question question = questions.stream().filter(question1 -> parts[0].equals(question1.getTitle())).findFirst().orElse(null);
                if (question != null) {
                    Answer answer = new Answer();
                    answer.setTitle(parts[1]);
                    answer.setCorrect(Boolean.parseBoolean(parts[2]));
                    answer.setQuestion(question);
                    answerService.createAnswer(answer);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("All answers for the questions added");
        System.out.println("Have a nice day :)");
    }
}
