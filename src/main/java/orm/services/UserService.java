package orm.services;

import entities.User;
import orm.dao.UserDAO;

public class UserService {
    private final UserDAO userDAO = new UserDAO();

    public User findUserById(long id) {
        return userDAO.findUserById(id);
    }

    public void save(User user) {
        userDAO.save(user);
    }
}
