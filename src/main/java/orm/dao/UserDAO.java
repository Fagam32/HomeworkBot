package orm.dao;

import entities.User;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class UserDAO{
    public User findUserById(long id){
        Session session = Utils.getSessionFactory().openSession();
        User user = session.get(User.class, id);
        session.close();
        return user;
    }

    public void save(User user){
        Session session = Utils.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        session.saveOrUpdate(user);
        transaction.commit();
        session.close();
    }

    public void delete(User user){
        Session session = Utils.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        session.delete(user);
        transaction.commit();
        session.close();
    }
}
