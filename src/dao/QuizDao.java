package dao;


import Model.Quiz;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.transaction.Transactional;
import java.util.Collections;
import java.util.List;
import java.util.Random;

final public class QuizDao extends AbstractDao {
    public QuizDao() {
    }

    @Transactional
    public void saveQuizToDB(Quiz quiz) {
        EntityManager em = getEMF().createEntityManager();
        em.getTransaction().begin();
        em.persist(quiz);
        em.getTransaction().commit();
        em.close();
    }

    public List<Quiz> getQuizzes() {
        EntityManager em = getEMF().createEntityManager();
        Query q = em.createQuery("SELECT v FROM Quiz v ORDER BY v.id");
        @SuppressWarnings("unchecked")
        List<Quiz> quizList = q.getResultList();
        em.close();
        return quizList;
    }

    public Quiz getQuizByTitle(String title) {
        EntityManager em = getEMF().createEntityManager();
        Query q = em.createQuery("SELECT v FROM Quiz v WHERE v.title = :title").setParameter("title", title);
        Quiz quiz = (Quiz) q.getSingleResult();
        em.close();
        return quiz;
    }

    public List<Quiz> getSomeQuizzes(int numberOfQuizzes, int offset) {
        EntityManager em = getEMF().createEntityManager();
        Query q = em.createQuery("SELECT v FROM Quiz v ORDER BY v.title DESC");
        @SuppressWarnings("unchecked")
        List<Quiz> quizzes = q.setMaxResults(numberOfQuizzes).setFirstResult(offset).getResultList();
        em.close();
        return quizzes;
    }

    public Quiz getQuizById(int id) {
        EntityManager em = getEMF().createEntityManager();
        Query q = em.createQuery("SELECT v FROM Quiz v WHERE v.id = :id").setParameter("id", id);
        if (q.getResultList().size() == 0) return null;
        Quiz quiz = (Quiz) q.getSingleResult();
        em.close();
        return quiz;
    }

    public Long getNumberOfQuizzes() {
        EntityManager em = getEMF().createEntityManager();
        Query q = em.createQuery("SELECT COUNT(v) FROM Quiz v");
        Long numberOfQuizzes = (Long) q.getSingleResult();
        em.close();
        return numberOfQuizzes;
    }

    public List<Quiz> getRandomQuizzes() {
        EntityManager em = getEMF().createEntityManager();
        Query q = em.createQuery("SELECT v FROM Quiz v WHERE v.active = :status").setParameter("status", true);
        @SuppressWarnings("unchecked")
        List<Quiz> quizzes = q.getResultList();
        Collections.shuffle(quizzes, new Random());
        em.close();
        return quizzes;
    }

    @Transactional
    public void removeQuizById(int quizId) {
        EntityManager em = getEMF().createEntityManager();
        Query q = em.createQuery("DELETE FROM Quiz u WHERE u.id = :id");
        q.setParameter("id", quizId);
        em.getTransaction().begin();
        q.executeUpdate();
        em.getTransaction().commit();
        em.close();
    }

    @Transactional
    public void updateQuiz(String quizTitle, String quizDesc, String imageUrl, boolean isActive, int quizId) {
        EntityManager em = getEMF().createEntityManager();
        Query q = em.createQuery("UPDATE Quiz u SET u.title = :quizTitle, u.description = :quizDesc, u.active = :isActive, u.imageUrl = :imageUrl WHERE u.id = :quizId");
        q.setParameter("quizTitle", quizTitle);
        q.setParameter("quizDesc", quizDesc);
        q.setParameter("isActive", isActive);
        q.setParameter("imageUrl", imageUrl);
        q.setParameter("quizId", quizId);
        em.getTransaction().begin();
        q.executeUpdate();
        em.getTransaction().commit();
        em.close();
    }
}
