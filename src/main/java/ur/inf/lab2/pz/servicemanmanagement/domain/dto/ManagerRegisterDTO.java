package ur.inf.lab2.pz.servicemanmanagement.domain.dto;

/**
 * Klasa data transfer object używana do przesyłania danych z formularza podczas rejestracji menadżera
 */

public class ManagerRegisterDTO {

    private String firstName;
    private String lastName;
    private String companyName;
    private String email;
    private String password;
    private String role;


    public ManagerRegisterDTO(String firstName, String lastName, String companyName, String email, String password, String role) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.companyName = companyName;
        this.email = email;
        this.password = password;
        this.role = role;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getCompanyName() {
        return companyName;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getRole() {
        return role;
    }
}
