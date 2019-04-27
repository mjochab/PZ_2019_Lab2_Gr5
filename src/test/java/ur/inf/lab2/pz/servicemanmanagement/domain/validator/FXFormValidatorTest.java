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

    @Test
    public void validateEmptyOrWhiteSpaceOnly_null_hasError() {
        String nullString = null;
        Text errorLabel = new Text("wiadomość walidacyjna");
        errorLabel.setVisible(false);
        validator.validateEmptyOrWhitespaceOnly(nullString,errorLabel);

        assertNull(nullString);
        assertTrue(errorLabel.isVisible());
        assertFalse(validator.isClean());
    }

    @Test
    public void validateEmptyOrWhiteSpaceOnly_isEmpty_hasError() {
        String emptyString = "";
        Text errorLabel = new Text("wiadomość walidacyjna");
        errorLabel.setVisible(false);
        validator.validateEmptyOrWhitespaceOnly(emptyString,errorLabel);

        assertTrue(errorLabel.isVisible());
        assertFalse(validator.isClean());
    }

    @Test
    public void validateEmptyOrWhiteSpaceOnly_hasWhitespace_hasError() {
        String withWhitespace = "bal ois";
        Text errorLabel = new Text("wiadomość walidacyjna");
        errorLabel.setVisible(false);
        validator.validateEmptyOrWhitespaceOnly(withWhitespace,errorLabel);

        assertTrue(errorLabel.isVisible());
        assertFalse(validator.isClean());
    }

    @Test
    public void validateEmptyOrWhiteSpaceOnly_isCorrect_noErrors() {
        String correctString = "aaa";
        Text errorLabel = new Text("wiadomość walidacyjna");
        errorLabel.setVisible(false);
        validator.validateEmptyOrWhitespaceOnly(correctString,errorLabel);

        assertFalse(errorLabel.isVisible());
        assertTrue(validator.isClean());
    }

    @Test
    public void validatePasswordEquality_passwordsNotMatching_hasError() {
        String pass1 = "pass1";
        String pass2 = "pass2";

        Text errorLabel = new Text("wiadomość walidacyjna");
        errorLabel.setVisible(false);
        validator.validatePasswordEquality(pass1,pass2,errorLabel);

        assertNotEquals(pass1,pass2);
        assertTrue(errorLabel.isVisible());
        assertFalse(validator.isClean());
    }

    @Test
    public void validatePasswordEquality_firstOfThemIsNull_hasError() {
        String pass1 = null;
        String pass2 = "pass2";

        Text errorLabel = new Text("wiadomość walidacyjna");
        errorLabel.setVisible(false);
        validator.validatePasswordEquality(pass1,pass2,errorLabel);

        assertNotEquals(pass1,pass2);
        assertTrue(errorLabel.isVisible());
        assertFalse(validator.isClean());
    }

    @Test
    public void validatePasswordEquality_secondOfThemIsNull_hasError() {
        String pass1 = "pass1";
        String pass2 = null;

        Text errorLabel = new Text("wiadomość walidacyjna");
        errorLabel.setVisible(false);
        validator.validatePasswordEquality(pass1,pass2,errorLabel);

        assertNotEquals(pass1,pass2);
        assertTrue(errorLabel.isVisible());
        assertFalse(validator.isClean());
    }

    @Test
    public void validatePasswordEquality_bothOfThemAreNull_hasError() {
        String pass1 = null;
        String pass2 = null;

        Text errorLabel = new Text("wiadomość walidacyjna");
        errorLabel.setVisible(false);
        validator.validatePasswordEquality(pass1,pass2,errorLabel);
        assertNull(pass1);
        assertNull(pass2);
        assertTrue(errorLabel.isVisible());
        assertFalse(validator.isClean());
    }

    @Test
    public void validatePasswordEquality_passwordsMatching_noErrors() {
        String pass1 = "pass";
        String pass2 = "pass";

        Text errorLabel = new Text("wiadomość walidacyjna");
        errorLabel.setVisible(false);
        validator.validatePasswordEquality(pass1,pass2,errorLabel);

        assertEquals(pass1,pass2);
        assertFalse(errorLabel.isVisible());
        assertTrue(validator.isClean());
    }

    @Test
    public void validatePasswordLength_passwordIsNull_hasError() {
        String pass = null;

        Text errorLabel = new Text("wiadomość walidacyjna");
        errorLabel.setVisible(false);
        validator.validatePasswordLength(pass,errorLabel);
        assertNull(pass);
        assertTrue(errorLabel.isVisible());
        assertFalse(validator.isClean());
    }

    @Test
    public void validatePasswordLength_passwordTooShort_hasError() {
        String pass = "pass1";

        Text errorLabel = new Text("wiadomość walidacyjna");
        errorLabel.setVisible(false);
        validator.validatePasswordLength(pass,errorLabel);

        assertTrue(errorLabel.isVisible());
        assertFalse(validator.isClean());
    }

    @Test
    public void validatePasswordLength_passwordTooLong_hasError() {
        String pass = "123456789123456789";

        Text errorLabel = new Text("wiadomość walidacyjna");
        errorLabel.setVisible(false);
        validator.validatePasswordLength(pass,errorLabel);

        assertTrue(errorLabel.isVisible());
        assertFalse(validator.isClean());
    }

    @Test
    public void validatePasswordLength_passwordCorrect_noErrors() {
        String pass = "tajnehaslo";

        Text errorLabel = new Text("wiadomość walidacyjna");
        errorLabel.setVisible(false);
        validator.validatePasswordLength(pass,errorLabel);

        assertFalse(errorLabel.isVisible());
        assertTrue(validator.isClean());
    }

}