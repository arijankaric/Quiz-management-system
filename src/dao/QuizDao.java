package dao;


import Model.Quiz;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

final public class QuizDao extends AbstractDao {
    public QuizDao() {
    }

    public void saveQuizToDB(Quiz quiz) {
        EntityManager em = getEMF().createEntityManager();
        em.getTransaction().begin();
        em.persist(quiz);
        em.getTransaction().commit();
        em.close();
    }
    
    public List<Quiz> getQuizzesDefault() {
        EntityManager em = getEMF().createEntityManager();
//        em.clear();
//      em.flush();

        Query q = em.createQuery("SELECT v FROM Quiz v ORDER BY v.id", Quiz.class);
//        q.setMaxResults(getNumberOfQuizzes().intValue()-1);
        List<Quiz> quizList = q.getResultList();;
        em.close();
        return quizList;
    }

    public List<Quiz> getQuizzes() {
        EntityManager em = getEMF().createEntityManager();
//        em.clear();
//      em.flush();

        Query q = em.createQuery("SELECT v FROM Quiz v ORDER BY v.id", Quiz.class);
        q.setMaxResults(getNumberOfQuizzes().intValue()-1);
        List<Quiz> quizList = q.getResultList();;
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
//		if(getNumberOfQuizzes() == 0)
//			return new ArrayList<Quiz>();
//		System.out.println("numberOfQuizzes: " + numberOfQuizzes);
//		System.out.println("offset: " + offset);
        EntityManager em = getEMF().createEntityManager();
        Query q = em.createQuery("SELECT u FROM Quiz u", Quiz.class);
        Integer end = (int) (offset + numberOfQuizzes);
        if(end >= getNumberOfQuizzes())
        	numberOfQuizzes = Math.toIntExact(getNumberOfQuizzes()) - offset;
        System.out.println("prije " + end + " " + offset + " " + numberOfQuizzes);
      @SuppressWarnings("unchecked")
        List<Quiz> quizzes = q.setMaxResults(numberOfQuizzes).setFirstResult(offset).getResultList();
        System.out.println("poslije " + end);
        em.close();
        return quizzes;
    }

    public Quiz getQuizById(int id) {
    	if(id == getNumberOfQuizzes())
    		return null;
        EntityManager em = getEMF().createEntityManager();
        Query q = em.createQuery("SELECT v FROM Quiz v WHERE v.id = :id").setParameter("id", id);
        if (q.getResultList().size() == 0) return null;
        Quiz quiz = (Quiz) q.getSingleResult();
        em.close();
        return quiz;
    }

    // This must remain true so others can work properly
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

    public void removeQuizById(int quizId) {
        EntityManager em = getEMF().createEntityManager();
        Query q = em.createQuery("DELETE FROM Quiz u WHERE u.id = :id");
        q.setParameter("id", quizId);
        em.getTransaction().begin();
        q.executeUpdate();
        em.getTransaction().commit();
        em.close();
    }

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
