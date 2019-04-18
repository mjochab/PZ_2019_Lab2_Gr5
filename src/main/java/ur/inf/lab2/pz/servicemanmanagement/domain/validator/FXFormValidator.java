package ur.inf.lab2.pz.servicemanmanagement.domain.validator;

import javafx.scene.text.Text;
import ur.inf.lab2.pz.servicemanmanagement.utils.StringUtils;

public class FXFormValidator {

    private boolean hasErrors = false;

    public void validateEmail(String email, Text errorLabel) {
        if (StringUtils.isEmptyOrWhitespaceOnly(email) || !email.contains("@") || email.length() < 3) {
            errorLabel.setVisible(true);
            hasErrors = true;
        }
    }

    public boolean isClean() {
        return !hasErrors;
    }

}
