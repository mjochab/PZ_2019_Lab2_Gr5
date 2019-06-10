package ur.inf.lab2.pz.servicemanmanagement.domain.dto;

/**
 * Klasa typu data transfer object zawierająca pola do zmiany danych menadżera
 */
public class ManagerDataDTO {

    String firstName;
    String lastName;
    String companyName;
    String password;
    String confirmPassword;


    public ManagerDataDTO(String firstName, String lastName, String companyName, String password, String confirmPassword) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.companyName = companyName;
        this.password = password;
        this.confirmPassword = confirmPassword;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() { return lastName;}

    public void setLastName(String lastName) { this.lastName = lastName; }

    public  String getCompanyName() { return companyName; }

    public void setCompanyName(String companyName) { this.companyName = companyName; }

    public String getPassword() { return password; }

    public void setPassword(String password) {this.password = password; }

    public String getConfirmPassword() { return confirmPassword; }

    public void setConfirmPassword(String confirmPassword) { this.confirmPassword = confirmPassword; }

}

