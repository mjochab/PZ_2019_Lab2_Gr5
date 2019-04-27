package ur.inf.lab2.pz.servicemanmanagement.domain.validator;

import javafx.scene.text.Text;
import ur.inf.lab2.pz.servicemanmanagement.utils.StringUtils;


public class FXFormValidator {

    private boolean hasErrors = false;

    public void validateEmail(String email, Text errorLabel) {
        if (StringUtils.isEmptyOrWhitespaceOnly(email) || !email.contains("@") || email.length() < 3) {
            errorLabel.setText("Email jest nieprawidłowy");
            errorLabel.setVisible(true);
            hasErrors = true;
        }
    }

    public boolean isClean() {
        return !hasErrors;
    }

    public void validateEmptyOrWhitespaceOnly(String text, Text errorLabel) {
        if (StringUtils.isEmptyOrWhitespaceOnly(text)) {
            errorLabel.setText("Nieprawidłowa wartość");
            errorLabel.setVisible(true);
            hasErrors = true;
        }
    }

    public void validatePasswordEquality(String pass1, String pass2, Text errorLabel) {
        try {
            if (!pass1.equals(pass2)) {
                errorLabel.setText("Hasła nie zgadzaja sie");
                errorLabel.setVisible(true);
                hasErrors = true;
            }
        } catch (NullPointerException ex) {
            errorLabel.setText("Hasła nie zgadzaja sie");
            errorLabel.setVisible(true);
            hasErrors = true;
        }
    }

    public void validatePasswordLength(String password, Text errorLabel) {
        try {
            if (password.length() < 6 || password.length() > 16) {
                errorLabel.setText("Prawidłowa długość hasła to 6-16 znaków");
                errorLabel.setVisible(true);
                hasErrors = true;
            }
        } catch (NullPointerException ex) {
            errorLabel.setText("Prawidłowa długość hasła to 6-16 znaków");
            errorLabel.setVisible(true);
            hasErrors = true;
        }
    }


}


