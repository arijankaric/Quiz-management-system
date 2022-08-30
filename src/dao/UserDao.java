package dao;

import Model.User;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.transaction.Transactional;
import java.util.List;

final public class UserDao extends AbstractDao {
    public UserDao() {
    }

    @Transactional
    public void insert(User user) {
        EntityManager em = getEMF().createEntityManager();
        em.getTransaction().begin();
        em.persist(user);
        em.getTransaction().commit();
        em.close();
    }

    public List<User> getUsers(int n, int offset) {
        EntityManager em = getEMF().createEntityManager();
        Query q = em.createQuery("SELECT v FROM User v ORDER BY v.username ASC");
        @SuppressWarnings("unchecked")
        List<User> userList = q.setMaxResults(n).setFirstResult(offset).getResultList();
        em.close();
        return userList;
    }

    public long usersCount() {
        EntityManager em = getEMF().createEntityManager();
        Query q = em.createQuery("SELECT COUNT(v) FROM User v");
        return (long) q.getSingleResult();
    }

    public User findByUsername(String username) {
        EntityManager em = getEMF().createEntityManager();
        Query q = em.createQuery("SELECT v FROM User v WHERE v.username = :username").setParameter("username", username);
        if (q.getResultList().size() == 0) return null;
        User user = (User) q.setMaxResults(1).getSingleResult();
        em.close();
        return user;
    }

    public User findByUserId(int userId) {
        EntityManager em = getEMF().createEntityManager();
        Query q = em.createQuery("SELECT v FROM User v WHERE v.id = :userId").setParameter("userId", userId);
        if (q.getResultList().size() == 0) return null;
        User user = (User) q.setMaxResults(1).getSingleResult();
        em.close();
        return user;
    }

    public List<User> getAllUsers() {
        EntityManager em = getEMF().createEntityManager();
        Query q = em.createQuery("SELECT v FROM User v ORDER BY v.username ASC");
        @SuppressWarnings("unchecked")
        List<User> userList = q.getResultList();
        em.close();
        return userList;
    }

    @Transactional
    public void updateUser(String oldUserName, String username, String password, int role) {
        EntityManager em = getEMF().createEntityManager();
        Query q = em.createQuery("UPDATE User u SET u.username = :username, u.password = :password, u.role = :role WHERE u.username = :oldUserName");
        q.setParameter("username", username);
        q.setParameter("password", password);
        q.setParameter("role", role);
        q.setParameter("oldUserName", oldUserName);
        em.getTransaction().begin();
        q.executeUpdate();
        em.getTransaction().commit();
        em.close();
    }

    @Transactional
    public void removeUser(String username) {
        EntityManager em = getEMF().createEntityManager();
        Query q = em.createQuery("DELETE FROM User u WHERE u.username = :username");
        q.setParameter("username", username);
        em.getTransaction().begin();
        q.executeUpdate();
        em.getTransaction().commit();
        em.close();
    }
}
