package service;

import dao.QuizDao;
import Model.Quiz;

import java.util.List;

public class QuizService {

    private final QuizDao quizDao;

    public QuizService(QuizDao quizDao) {
        this.quizDao = quizDao;
    }

    public Quiz getQuizByTitle(String title) {
        return quizDao.getQuizByTitle(title);
    }

    public Long getNumberOfQuizzes() {
        return quizDao.getNumberOfQuizzes();
    }

    public Quiz getQuizById(int id) {
        return quizDao.getQuizById(id);
    }

    public void removeQuizById(int id) {
        quizDao.removeQuizById(id);
    }

    public void createQuiz(Quiz quiz) {
        quizDao.saveQuizToDB(quiz);
//        Quiz lastQuiz = quizDao.getQuizById(quizDao.getNumberOfQuizzes().intValue()-1);
//        quizDao.updateQuiz(quiz.getTitle(), quiz.getDescription(), quiz.getImageUrl(), quiz.isActive(), Math.toIntExact(quizDao.getNumberOfQuizzes())-1);
    }
    
    public void createQuizModified(Quiz quiz) {
        quizDao.saveQuizToDB(quiz);
        quizDao.updateQuiz(quiz.getTitle(), quiz.getDescription(), quiz.getImageUrl(), Math.toIntExact(quizDao.getNumberOfQuizzes())-1);
    }

    public List<Quiz> getQuizzes() {
        return quizDao.getQuizzes();
    }

    public List<Quiz> getRandomQuizzes() {
        return quizDao.getRandomQuizzes();
    }

    public List<Quiz> getSomeQuizzes(int numberOfQuizzes, int offset) {
        return quizDao.getSomeQuizzes(numberOfQuizzes, offset);
    }

    public void updateQuiz(String quizTitle,
                           String quizDesc,
                           String imageUrl,
                           int quizId) {
        quizDao.updateQuiz(quizTitle, quizDesc, imageUrl, quizId);
    }
}
