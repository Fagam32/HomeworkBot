package orm.services;

import callbacks.SubjectType;
import entities.Homework;
import orm.dao.HomeworkDAO;

import java.util.List;

public class HomeworkService {
    private final HomeworkDAO dao = new HomeworkDAO();

    public List<Homework> findByType(SubjectType type) {
        return dao.getHomeworkList(type);
    }

    public Homework findById(int id){
        return dao.getById(id);
    }

    public void save(Homework homework) {
        dao.save(homework);
    }

    public void remove(Homework homework) {
        dao.remove(homework);
    }

}
