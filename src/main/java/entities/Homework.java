package entities;

import callbacks.SubjectType;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "homework")
public class Homework {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "task_id")
    private long id;

    @Enumerated(EnumType.STRING)
    private SubjectType type;

    private String task;

    @OneToMany(mappedBy = "homework",
            cascade = CascadeType.ALL,
            fetch = FetchType.EAGER,
            orphanRemoval = true)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private final List<UserHomework> userTasks = new ArrayList<>();

    public Homework() {
    }

    public Homework(long id) {
        this.id = id;
    }

    public Homework(SubjectType type, String task) {
        this.type = type;
        this.task = task;
    }

    public SubjectType getType() {
        return type;
    }

    public void setType(SubjectType type) {
        this.type = type;
    }

    public String getTask() {
        return task;
    }

    public void setTask(String task) {
        this.task = task;
    }

    public List<UserHomework> getUserTasks() {
        return userTasks;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Homework)) return false;

        Homework homework = (Homework) o;

        if (id == homework.id) return true;
        if (type != homework.type) return false;
        return Objects.equals(task, homework.task);
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (type != null ? type.hashCode() : 0);
        result = 31 * result + (task != null ? task.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "" + id + ". " + task;
    }

}
