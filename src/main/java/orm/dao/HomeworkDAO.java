package orm.dao;

import callbacks.SubjectType;
import entities.Homework;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;


import java.util.List;

public class HomeworkDAO {
    @SuppressWarnings("unchecked")
    public List<Homework> getHomeworkList(SubjectType type) {
        Session session = Utils.getSessionFactory().openSession();
        Query<Homework> query = session.createQuery("from Homework hw where hw.type = :TYPE");
        query.setParameter("TYPE", type);
        List<Homework> list = query.list();
        session.close();
        return list;
    }

    public void save(Homework homework) {
        Session session = Utils.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        session.saveOrUpdate(homework);
        transaction.commit();
        session.close();
    }

    public void remove(Homework homework) {
        Session session = Utils.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        session.delete(homework);
        transaction.commit();
        session.close();
    }

    public Homework getById(int id) {
        Session session = Utils.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        Homework homework = session.get(Homework.class, (long)id);
        transaction.commit();
        session.close();
        return homework;
    }

}
