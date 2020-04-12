package entities;

import javax.persistence.*;

@Entity
@Table(name = "user_homework")
@IdClass(UserHomeworkId.class)
public class UserHomework {

    @Id
    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    private User user;

    @Id
    @ManyToOne
    @JoinColumn(name = "homework_id", referencedColumnName = "task_id")
    private Homework homework;

    @Column(name = "is_done")
    private boolean isDone = false;

    public boolean isDone() {
        return isDone;
    }

    public void setDone(boolean done) {
        isDone = done;
    }


    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Homework getHomework() {
        return homework;
    }

    public void setHomework(Homework homework) {
        this.homework = homework;
    }
}
