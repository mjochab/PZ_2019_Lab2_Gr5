package ur.inf.lab2.pz.servicemanmanagement.domain;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Serviceman extends User {

    @ManyToOne
    @JoinColumn(name = "manager_id")
    private User manager;

    public Serviceman(Manager manager) {
        this.manager = manager;
    }

    public Serviceman(String email, String password, String groupName, User manager) {
        super(email, password, groupName);
        this.manager = manager;
    }

    public Serviceman() {
    }

    public User getManager() {
        return manager;
    }

    public void setManager(Manager manager) {
        this.manager = manager;
    }

}
