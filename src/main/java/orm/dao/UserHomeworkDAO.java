package orm.dao;

import entities.UserHomework;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class UserHomeworkDAO {
    public void update(UserHomework userHomework){
        Session session = Utils.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        session.saveOrUpdate(userHomework);
        transaction.commit();
        session.close();
    }

    public void delete(UserHomework userHomework) {
        Session session = Utils.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        session.delete(userHomework);
        transaction.commit();
        session.close();
    }
}
