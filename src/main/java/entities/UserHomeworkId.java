package entities;

import java.io.Serializable;

public class UserHomeworkId implements Serializable {

    private long user;
    private long homework;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserHomeworkId)) return false;

        UserHomeworkId that = (UserHomeworkId) o;

        if (user != that.user) return false;
        return homework == that.homework;
    }

    @Override
    public int hashCode() {
        int result = (int) (user ^ (user >>> 32));
        result = 31 * result + (int) (homework ^ (homework >>> 32));
        return result;
    }

    public long getHomework() {
        return homework;
    }

    public void setHomework(long homework) {
        this.homework = homework;
    }

    public long getUser() {
        return user;
    }

    public void setUser(long user) {
        this.user = user;
    }
}
