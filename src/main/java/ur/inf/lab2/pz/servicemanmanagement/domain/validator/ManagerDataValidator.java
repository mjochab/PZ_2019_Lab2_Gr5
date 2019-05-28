package ur.inf.lab2.pz.servicemanmanagement.domain.validator;

import javafx.scene.text.Text;

public class ManagerDataValidator {

    String firstName, lastName, companyName, password, confirmPassword;
    Text firstNameAlert, lastNameAlert, companyNameAlert, passwordAlert, confirmPasswordAlert;

    private FXFormValidator validator;

    public ManagerDataValidator(String firstName, String lastName, String companyName,
                                     String password, String confirmPassword, Text firstNameAlert,
                                Text lastNameAlert, Text companyNameAlert, Text passwordAlert,
                                Text confirmPasswordAlert) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.companyName = companyName;
        this.password = password;
        this.confirmPassword = confirmPassword;
        this.firstNameAlert = firstNameAlert;
        this.lastNameAlert = lastNameAlert;
        this.companyNameAlert = companyNameAlert;
        this.passwordAlert = passwordAlert;
        this.confirmPasswordAlert = confirmPasswordAlert;
    }

    public void validate() {

        clearAlerts();
        validator = new FXFormValidator();
        validator.validateEmptyOrWhitespaceOnly(firstName, firstNameAlert);
        validator.validateEmptyOrWhitespaceOnly(lastName, lastNameAlert);
        validator.validateEmptyOrWhitespaceOnly(companyName, companyNameAlert);
        validator.validateEmptyOrWhitespaceOnly(password,passwordAlert);
        validator.validateEmptyOrWhitespaceOnly(confirmPassword,passwordAlert);
        validator.validatePasswordEquality(password, confirmPassword, confirmPasswordAlert);
        validator.validatePasswordLength(password, confirmPasswordAlert);
    }

    private void clearAlerts() {

        firstNameAlert.setVisible(false);
        lastNameAlert.setVisible(false);
        companyNameAlert.setVisible(false);
        passwordAlert.setVisible(false);
        confirmPasswordAlert.setVisible(false);
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

    public String getPassword() {
        return password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public Text getFirstNameAlert() {
        return firstNameAlert;
    }

    public Text getLastNameAlert() {
        return lastNameAlert;
    }

    public Text getCompanyNameAlert() {
        return companyNameAlert;
    }

    public Text getPasswordAlert() {
        return passwordAlert;
    }

    public Text getConfirmPasswordAlert() {
        return confirmPasswordAlert;
    }

    public FXFormValidator getValidator() {
        return validator;
    }

}
