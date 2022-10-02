package service;

import dao.AnswerDao;
import Model.Answer;
import Model.Question;

public class AnswerService {

    private final AnswerDao answerDao;

    public AnswerService(AnswerDao answerDao) {
        this.answerDao = answerDao;
    }

    public void createAnswer(Answer answer) {
        answerDao.saveAnswerToDB(answer);
    }

    public Long getNumberOfAnswers() {
        return answerDao.getNumberOfAnswers();
    }

    public void shouldUpdateOrRemove(String newTitle, boolean newStatus, int answerId, int questionId) {
        if (newTitle.isBlank()) {
            answerDao.removeAnswerById(answerId);
        } else {
            answerDao.updateAnswer(newTitle, newStatus, answerId);
        }
    }
    
    public void updateAnswer(String newTitle, boolean newStatus, int answerId) {
        answerDao.updateAnswer(newTitle, newStatus, answerId);
    }

    public void removeAnswerById(int answerId) {
        answerDao.removeAnswerById(answerId);
    }
}