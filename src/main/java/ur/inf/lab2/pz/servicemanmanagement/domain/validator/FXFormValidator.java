package ur.inf.lab2.pz.servicemanmanagement.domain.validator;

import javafx.scene.text.Text;
import org.springframework.beans.factory.annotation.Autowired;
import ur.inf.lab2.pz.servicemanmanagement.repository.UserRepository;
import ur.inf.lab2.pz.servicemanmanagement.utils.StringUtils;


public class FXFormValidator {

    @Autowired
    private UserRepository userRepository;

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
        if (StringUtils.isEmptyOrWhitespaceOnly(text)) errorLabel.setVisible(true); hasErrors=true;
    }

    public void validatePasswordEquality(String pass1, String pass2, Text errorLabel) {
        if (!pass1.equals(pass2)) {
            errorLabel.setText("Hasła nie zgadzaja sie");
            errorLabel.setVisible(true);
            hasErrors=true;
        }
        validatePasswordLength(pass1,errorLabel);
    }

    public void validatePasswordLength(String password, Text errorLabel) {
        if (password.length() < 6 || password.length() > 16)
        {errorLabel.setText("Hasło musi zawierać conajmniej 6 i maksymalnie 16 znaków.");
            errorLabel.setVisible(true);
            hasErrors=true;
        }
    }

    public void validateUserAlreadyExists(String email, Text errorLabel) {

      //  if(userRepository.findAllByEmail(email)==null) {}
       // else if (!userRepository.findAllByEmail(email).isEmpty()){
       //     errorLabel.setVisible(true);
       //     hasErrors=true;
        }

    public void setHasErrors(boolean hasErrors) {
        this.hasErrors = hasErrors;
    }
}

