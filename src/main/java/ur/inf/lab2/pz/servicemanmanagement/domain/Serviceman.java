package ur.inf.lab2.pz.servicemanmanagement.domain;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


/**
 * Encja reprezentująca użytkownika zalogowanego jako serwisant
 */

@Entity
public class Serviceman extends User {

    @ManyToOne
    @JoinColumn(name = "manager_id")
    private User manager;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "leader")
    private List<Task> tasks;

    public Serviceman(Manager manager) {
        this.manager = manager;
    }

    public Serviceman(String email, String password, String groupName, User manager) {
        super(email, password, groupName);
        this.manager = manager;
    }

    public Serviceman() {}

    public User getManager() {
        return manager;
    }

    public void setManager(Manager manager) {
        this.manager = manager;
    }

    public List<Task> getTasks() {
        if (tasks == null)
            tasks = new ArrayList<>();

        return tasks;
    }

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }
}
