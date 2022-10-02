package service;

import dao.UserDao;
import Model.User;
import Util.SecurityUtil;

import java.util.List;

public class UserService {

    private final UserDao userDao;

    public UserService(UserDao userDao) {
        this.userDao = userDao;
    }

    public User findByUsername(String username) {
        return userDao.findByUsername(username);
    }

    public List<User> findAll(int n, int offset) {
        return userDao.getUsers(n, offset);
    }

    public User findByUserId(int userId) {
        return userDao.findByUserId(userId);
    }

    public void removeUser(String username) {
        userDao.removeUser(username);
    }

    public List<User> getAllUsers() {
        return userDao.getAllUsers();
    }

    public long usersCount() {
        return userDao.usersCount();
    }

    public void create(User user) {
        user.setPassword(SecurityUtil.hashPassword(user.getPassword()));
        userDao.insert(user);
    }

    public void updateUser(String oldUserName, String username, String password, int role) {
        userDao.updateUser(oldUserName, username, password, role);
    }
    
    public void updateUser(int id, String username, String password, int role) {
        userDao.updateUser(id, username, password, role);
    }

    public User authenticate(String username, String password) {

        User user = findByUsername(username);
        if (user == null) {
            return null;
        }

        if (SecurityUtil.checkPassword(password, user.getPassword())) {
            user.setPassword("");
            return user;
        }
        return null;
    }
}