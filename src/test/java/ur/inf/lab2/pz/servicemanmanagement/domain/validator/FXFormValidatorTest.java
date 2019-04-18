package ur.inf.lab2.pz.servicemanmanagement.domain.validator;

import javafx.scene.text.Text;
import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FXFormValidatorTest {

    private FXFormValidator validator;

    @BeforeEach
    public void setUp() {
        validator = new FXFormValidator();
    }

    @Test
    public void validateEmail_null_hasErrors() {
        String incorrectEmail = null;
        Text errorLabel = new Text("Wiadomość walidacyjna");
        errorLabel.setVisible(false); // Ponieważ domyślnie wiadomość walidacyjna jest schowana

        validator.validateEmail(incorrectEmail, errorLabel);

        assertTrue(errorLabel.isVisible());
        assertFalse(validator.isClean());
    }

    @Test
    public void validateEmail_empty_hasErrors() {
        String incorrectEmail = "";
        Text errorLabel = new Text("Wiadomość walidacyjna");
        errorLabel.setVisible(false); // Ponieważ domyślnie wiadomość walidacyjna jest schowana

        validator.validateEmail(incorrectEmail, errorLabel);

        assertTrue(errorLabel.isVisible());
        assertFalse(validator.isClean());
    }

    @Test
    public void validateEmail_correctEmail_noErrors() {
        String correctEmail = "balois@balois.pl";
        Text errorLabel = new Text("Wiadomość walidacyjna");
        errorLabel.setVisible(false); // Ponieważ domyślnie wiadomość walidacyjna jest schowana

        validator.validateEmail(correctEmail, errorLabel);

        assertFalse(errorLabel.isVisible());
        assertTrue(validator.isClean());
    }

    @Test
    public void validateEmail_emailWithoutMonkey_hasError() {
        String incorrectEmail = "baloisBalois.pl";
        Text errorlabel = new Text("wiadomość walidacyjna");
        errorlabel.setVisible(false);

        validator.validateEmail(incorrectEmail, errorlabel);

        assertTrue(errorlabel.isVisible());
        assertFalse(validator.isClean());
    }

    @Test
    public void validateEmail_emailIsToShortButHasMonkey_hasError() {
        String incorrectEmail = "a@";
        Text errorLabel = new Text("wiadomość walidacyjna");
        errorLabel.setVisible(false);

        validator.validateEmail(incorrectEmail, errorLabel);

        assertTrue(errorLabel.isVisible());
        assertFalse(validator.isClean());
    }
}