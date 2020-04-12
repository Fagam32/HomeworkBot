package entities;

import callbacks.RequestType;
import utils.Role;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "people")
public class User {
    @Id
    @Column(name = "user_id")
    private long id;

    private String nickname;

    @Enumerated(EnumType.STRING)
    private Role role;

    @Enumerated(EnumType.STRING)
    private RequestType currentCallback;

    @OneToMany(mappedBy = "user",
            cascade = CascadeType.ALL,
            fetch = FetchType.EAGER,
            orphanRemoval = true
    )
    private Set<UserHomework> userTasks = new HashSet<>();

    public User() {
    }

    public User(long id, String nickname, Role role, RequestType callback) {
        this.id = id;
        this.nickname = nickname;
        this.role = role;
        this.currentCallback = callback;
    }

    public Set<UserHomework> getUserTasks() {
        return userTasks;
    }

    public void setUserTasks(Set<UserHomework> tasks) {
        this.userTasks = tasks;
    }

    public RequestType getCurrentCallback() {
        return currentCallback;
    }

    public void setCurrentCallback(RequestType currentCallback) {
        this.currentCallback = currentCallback;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", nickname='" + nickname + '\'' +
                ", role=" + role +
                ", currentCallback=" + currentCallback +
                ", tasks=" + userTasks +
                '}';
    }
}
