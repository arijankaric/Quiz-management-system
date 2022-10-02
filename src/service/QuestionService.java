package service;

import dao.QuestionDao;
import Model.Question;

import java.util.List;

public class QuestionService {

    private final QuestionDao questionDao;

    public QuestionService(QuestionDao questionDao) {
        this.questionDao = questionDao;
    }

    public void createQuestion(Question question) {
        questionDao.saveQuestionToDB(question);
    }

    public Long getNumberOfQuestions() {
        return questionDao.getNumberOfQuestions();
    }

    public List<Question> getQuestions() {
        return questionDao.getQuestions();
    }

    public void updateQuestion(int questionId,
                               String questionTitle,
                               int questionTimeToAnswer,
                               int questionScore) {
        questionDao.updateQuestion(questionId, questionTitle, questionTimeToAnswer, questionScore);
    }
    
    public void updateQuestion(int id, String title, int timeToAnswer, int score, int ordinalNumber) {
    	questionDao.updateQuestion(id, title, timeToAnswer, score, ordinalNumber);
    }

    public Question getQuestionById(int id) {
        return questionDao.getQuestionById(id);
    }

    public void removeQuestionById(int questionId) {
        questionDao.removeQuestionById(questionId);
    }
    
    public List<Question> getQuestionsByQuizId(int quizId) {
    	return questionDao.getQuestionsByQuizId(quizId);
    }
}
