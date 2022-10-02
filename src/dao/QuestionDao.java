package dao;

import Model.Question;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.transaction.Transactional;

import java.util.List;

final public class QuestionDao extends AbstractDao {
    public QuestionDao() {
    }

//    @Transactional
    public void saveQuestionToDB(Question question) {
        EntityManager em = getEMF().createEntityManager();
        em.getTransaction().begin();
        em.persist(question);
        em.getTransaction().commit();
        em.close();
    }

    public Long getNumberOfQuestions() {
        EntityManager em = getEMF().createEntityManager();
        Query q = em.createQuery("SELECT COUNT(v) FROM Question v");
        Long numberOfQuestions = (Long) q.getSingleResult();
        em.close();
        return numberOfQuestions;
    }

    public List<Question> getQuestions() {
        EntityManager em = getEMF().createEntityManager();
        Query q = em.createQuery("SELECT v FROM Question v order by v.id");
        @SuppressWarnings("unchecked")
        List<Question> questionList = q.getResultList();
        em.close();
        return questionList;
    }
    
    public List<Question> getQuestionsByQuizId(int quizId) {
    	EntityManager em = getEMF().createEntityManager();
        Query q = em.createQuery("SELECT v FROM Question v WHERE v.quiz_id = :quizId order by v.ordinal_number");
        q.setParameter("quizId", quizId);
        @SuppressWarnings("unchecked")
        List<Question> questionList = q.getResultList();
        em.close();
        return questionList;
    }
    
//    @Transactional
    public void updateQuestion(int questionId, String questionTitle, int questionTimeToAnswer, int questionScore) {
    	System.out.println(questionId + " " + questionTitle + " " + questionTimeToAnswer + " " + questionScore);
        EntityManager em = getEMF().createEntityManager();
        Query q = em.createQuery("UPDATE Question u SET u.title = :questionTitle, u.score = :questionScore, u.timeToAnswer = :questionTimeToAnswer WHERE u.id = :questionId");
        q.setParameter("questionTitle", questionTitle);
        q.setParameter("questionTimeToAnswer", questionTimeToAnswer);
        q.setParameter("questionScore", questionScore);
        q.setParameter("questionId", questionId);
        em.getTransaction().begin();
        q.executeUpdate();
        em.getTransaction().commit();
        em.close();
    }
    
//    @Transactional
    public void updateQuestion(int questionId, String questionTitle, int questionTimeToAnswer, int questionScore, int ordinalNumber) {
    	System.out.println(questionId + " " + questionTitle + " " + questionTimeToAnswer + " " + questionScore);
        EntityManager em = getEMF().createEntityManager();
        Query q = em.createQuery("UPDATE Question u SET u.title = :questionTitle, u.score = :questionScore, u.timeToAnswer = :questionTimeToAnswer, u.ordinalNumber = :ordinalNumber WHERE u.id = :questionId");
        q.setParameter("questionTitle", questionTitle);
        q.setParameter("questionTimeToAnswer", questionTimeToAnswer);
        q.setParameter("questionScore", questionScore);
        q.setParameter("questionId", questionId);
        q.setParameter("ordinalNumber", ordinalNumber);
        em.getTransaction().begin();
        q.executeUpdate();
        em.getTransaction().commit();
        em.close();
    }

    public Question getQuestionById(int id) {
        EntityManager em = getEMF().createEntityManager();
        Query q = em.createQuery("SELECT v FROM Question v WHERE v.id = :id").setParameter("id", id);
        Question question = (Question) q.getSingleResult();
        em.refresh(question);
        em.close();
        return question;
    }
    
//    @Transactional
    public void removeQuestionById(int questionId) {
        EntityManager em = getEMF().createEntityManager();
        Query q = em.createQuery("DELETE FROM Question u WHERE u.id = :id");
        q.setParameter("id", questionId);
        em.getTransaction().begin();
        q.executeUpdate();
        em.getTransaction().commit();
        em.close();
    }
}
