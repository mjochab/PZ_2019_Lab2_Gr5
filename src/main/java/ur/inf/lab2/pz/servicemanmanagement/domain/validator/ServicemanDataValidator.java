package ur.inf.lab2.pz.servicemanmanagement.domain.validator;

import javafx.scene.text.Text;

public class ServicemanDataValidator {
    String firstName, lastName, password, confirmPassword;
    Text firstNameAlert, lastNameAlert, passwordAlert, confirmPasswordAlert;

    private FXFormValidator validator;

    public ServicemanDataValidator(String firstName, String lastName, String password, String confirmPass, Text firstNameAlert,
                                Text lastNameAlert, Text passwordAlert,
                                Text confirmPasswordAlert) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
        this.confirmPassword = confirmPass;
        this.firstNameAlert = firstNameAlert;
        this.lastNameAlert = lastNameAlert;
        this.passwordAlert = passwordAlert;
        this.confirmPasswordAlert = confirmPasswordAlert;
    }

    public void validate() {

        clearAlerts();
        validator = new FXFormValidator();
        validator.validateEmptyOrWhitespaceOnly(firstName, firstNameAlert);
        validator.validateEmptyOrWhitespaceOnly(lastName, lastNameAlert);
        validator.validateEmptyOrWhitespaceOnly(password,passwordAlert);
        validator.validateEmptyOrWhitespaceOnly(confirmPassword,passwordAlert);
        validator.validatePasswordEquality(password, confirmPassword, confirmPasswordAlert);
        validator.validatePasswordLength(password, confirmPasswordAlert);
    }

    private void clearAlerts() {

        firstNameAlert.setVisible(false);
        lastNameAlert.setVisible(false);
        passwordAlert.setVisible(false);
        confirmPasswordAlert.setVisible(false);
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
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
