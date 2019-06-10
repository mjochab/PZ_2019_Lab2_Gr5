package ur.inf.lab2.pz.servicemanmanagement.domain;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import java.util.List;

/**
 * Encja reprezentująca Użytkownika zalogowanego jako menadżer
 */

@Entity
public class Manager extends User {


    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "manager")
    private List<Serviceman> employees;

    public List<Serviceman> getEmployees() {
        return employees;
    }

    public void setEmployees(List<Serviceman> employees) {
        this.employees = employees;
    }

}
