package dao;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.transaction.Transactional;

import Model.Answer;
import Model.Question;

final public class AnswerDao extends AbstractDao {
    public AnswerDao() {
    }

    @Transactional
    public void saveAnswerToDB(Answer answer) {
        EntityManager em = getEMF().createEntityManager();
        em.getTransaction().begin();
        em.persist(answer);
        em.getTransaction().commit();
        em.close();
    }

    public Long getNumberOfAnswers() {
        EntityManager em = getEMF().createEntityManager();
        Query q = em.createQuery("SELECT COUNT(v) FROM Answer v");
        Long numberOfAnswers = (Long) q.getSingleResult();
        em.close();
        return numberOfAnswers;
    }
    
    @Transactional
    public void removeAnswerById(int answerId) {
        EntityManager em = getEMF().createEntityManager();
        Query q = em.createQuery("DELETE FROM Answer u WHERE u.id = :id");
        q.setParameter("id", answerId);
        em.getTransaction().begin();
        q.executeUpdate();
        em.getTransaction().commit();
        em.close();
    }

    @Transactional
    public void updateAnswer(String answerTitle, boolean status, int answerId) {
        EntityManager em = getEMF().createEntityManager();
        Query q = em.createQuery("UPDATE Answer u SET u.title = :answerTitle, u.correct = :status WHERE u.id = :answerId");
        q.setParameter("answerTitle", answerTitle);
        q.setParameter("status", status);
        q.setParameter("answerId", answerId);
//        q.setParameter("questionId", questionId);
        em.getTransaction().begin();
        q.executeUpdate();
        em.getTransaction().commit();
        em.close();
    }
    
    @Transactional
    public void moveAnswerToQuestion(int answerId,Question newQuestion, int oldQuestionId) {
        EntityManager em = getEMF().createEntityManager();
        Query q = em.createQuery("UPDATE Answer u SET u.question = :newQuestion WHERE u.id = :answerId AND u.question.id = :oldQuestionId");
        q.setParameter("newQuestion", newQuestion);
        q.setParameter("oldQuestionId", oldQuestionId);
        q.setParameter("answerId", answerId);
        em.getTransaction().begin();
        q.executeUpdate();
        em.getTransaction().commit();
        em.close();
    }
}
