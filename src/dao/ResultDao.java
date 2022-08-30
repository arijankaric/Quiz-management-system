package dao;

import Model.Result;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.transaction.Transactional;
import java.util.List;

final public class ResultDao extends AbstractDao {
    public ResultDao() {
    }

    @Transactional
    public void saveResultToDB(Result result) {
        EntityManager em = getEMF().createEntityManager();
        em.getTransaction().begin();
        em.persist(result);
        em.getTransaction().commit();
        em.close();
    }

    public Result getBestPlayerFromDB() {
        EntityManager em = getEMF().createEntityManager();
        Query q = em.createQuery("SELECT v FROM Result v ORDER BY v.score DESC, v.userName ASC, v.userSurname ASC");
        if (q.getResultList().size() == 0) return null;
        Result result = (Result) q.setMaxResults(1).getSingleResult();
        em.close();
        return result;
    }

    public List<Result> getResultsOfAllQuizzesOfUser(int userId) {
        EntityManager em = getEMF().createEntityManager();
        Query q = em.createQuery("Select r from Result r inner join Quiz q on q.id = r.quiz.id inner join User u on q.owner.id = u.id where u.id = :id");
        q.setParameter("id", userId);
        @SuppressWarnings("unchecked")
        List<Result> results = q.getResultList();
        em.close();
        return results;
    }
}
