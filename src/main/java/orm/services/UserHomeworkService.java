package orm.services;

import entities.UserHomework;
import orm.dao.UserHomeworkDAO;

public class UserHomeworkService {
    final UserHomeworkDAO dao = new UserHomeworkDAO();
    public void save(UserHomework userHomework){
        dao.update(userHomework);
    }
    public void remove(UserHomework userHomework){
        dao.delete(userHomework);
    }
}
