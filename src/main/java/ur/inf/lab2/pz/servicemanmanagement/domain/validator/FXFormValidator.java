package ur.inf.lab2.pz.servicemanmanagement.domain.validator;

import javafx.scene.text.Text;
import org.thymeleaf.util.NumberUtils;
import org.thymeleaf.util.Validate;
import ur.inf.lab2.pz.servicemanmanagement.utils.StringUtils;


public class FXFormValidator {

    private boolean hasErrors = false;

    public void validateEmail(String email, Text errorLabel) {
        if (StringUtils.isEmptyOrWhitespaceOnly(email) || !email.contains("@") || email.length() < 3) {
            errorLabel.setText(ValidateMessage.INCORRECT_EMAIL_FORMAT);
            errorLabel.setVisible(true);
            hasErrors = true;
        }
    }

    public boolean isClean() {
        return !hasErrors;
    }

    public void validateEmptyOrWhitespaceOnly(String text, Text errorLabel) {
        if (StringUtils.isEmptyOrWhitespaceOnly(text)) {
            errorLabel.setText(ValidateMessage.INCORRECT_TEXT_VALUE);
            errorLabel.setVisible(true);
            hasErrors = true;
        }
    }

    public void validatePasswordEquality(String pass1, String pass2, Text errorLabel) {

        if (pass1==null || pass2==null ||!pass1.equals(pass2)) {
            errorLabel.setText(ValidateMessage.PASSWORDS_NOT_MATCH);
            errorLabel.setVisible(true);
            hasErrors = true;
        }
    }

    public void validatePasswordLength(String password, Text errorLabel) {

        if (password==null || password.length() < 6 || password.length() > 16) {
            errorLabel.setText(ValidateMessage.INCORRECT_PASSWORD_LENGTH);
            errorLabel.setVisible(true);
            hasErrors = true;
        }
    }

    public void  isNumber(String number, Text errorLabel){

        try{
            Integer.parseInt(number); }
        catch (Exception E){
            errorLabel.setText(ValidateMessage.INCORRECT_NUMBER_FORMAT);
            errorLabel.setVisible(true);
            hasErrors = true;
        }
    }

    public void validatePhoneLength(String phoneNumber, Text errorLabel){
        if(phoneNumber.length() < 9 || phoneNumber.length() > 9){
            errorLabel.setText(ValidateMessage.INCORRECT_NUMBER_LENGTH);
            errorLabel.setVisible(true);
            hasErrors = true;
        }
    }
}