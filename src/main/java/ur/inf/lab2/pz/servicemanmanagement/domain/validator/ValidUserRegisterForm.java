package ur.inf.lab2.pz.servicemanmanagement.domain.validator;

import javafx.fxml.FXML;
import javafx.scene.text.Text;

public class ValidUserRegisterForm {

    String firstName, lastName, companyName, email, password, confirmPassword;

    @FXML
    Text firstNameAlert, lastNameAlert, companyNameAlert, emailAlert, privacyAlert, existingUserAlert;

    private FXFormValidator validator;

    public ValidUserRegisterForm(String firstName, String lastName, String companyName, String email,
                                 String password, String confirmPassword, Text firstNameAlert,
                                 Text lastNameAlert, Text companyNameAlert, Text emailAlert,
                                 Text privacyAlert, Text existingUserAlert) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.companyName = companyName;
        this.email = email;
        this.password = password;
        this.confirmPassword = confirmPassword;
        this.firstNameAlert = firstNameAlert;
        this.lastNameAlert = lastNameAlert;
        this.companyNameAlert = companyNameAlert;
        this.emailAlert = emailAlert;
        this.privacyAlert = privacyAlert;
        this.existingUserAlert = existingUserAlert;
    }

    public void validate() {

        clearAlerts();
        validator = new FXFormValidator();
        validator.validateEmptyOrWhitespaceOnly(firstName, firstNameAlert);
        validator.validateEmptyOrWhitespaceOnly(lastName, lastNameAlert);
        validator.validateEmptyOrWhitespaceOnly(companyName, companyNameAlert);
        validator.validateEmptyOrWhitespaceOnly(password,privacyAlert);
        validator.validateEmptyOrWhitespaceOnly(confirmPassword,privacyAlert);
        validator.validateEmail(email, emailAlert);
        validator.validatePasswordEquality(password, confirmPassword, privacyAlert);
        validator.validatePasswordLength(password, privacyAlert);
    }

    private void clearAlerts() {

        firstNameAlert.setVisible(false);
        lastNameAlert.setVisible(false);
        companyNameAlert.setVisible(false);
        emailAlert.setVisible(false);
        privacyAlert.setVisible(false);
        existingUserAlert.setVisible(false);
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

    public Text getEmailAlert() {
        return emailAlert;
    }

    public Text getPrivacyAlert() {
        return privacyAlert;
    }

    public Text getExistingUserAlert() {
        return existingUserAlert;
    }

    public FXFormValidator getValidator() {
        return validator;
    }
}
