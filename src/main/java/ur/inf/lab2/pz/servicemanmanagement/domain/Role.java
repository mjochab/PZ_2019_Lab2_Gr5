package ur.inf.lab2.pz.servicemanmanagement.domain;

import javax.persistence.*;

/**
 * Encja reprezentująca Role użytkownika potrzebna do spring security
 */

@Entity
@Table(name="user_role")
public class Role {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int id;

    private String role;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
