package ur.inf.lab2.pz.servicemanmanagement.domain.validator;

import javafx.scene.text.Text;
import org.thymeleaf.util.Validate;
import ur.inf.lab2.pz.servicemanmanagement.utils.StringUtils;

public class WorkerAddValidator {
    String email;
    String groupName;
    Text emailAlert;
    Text emptyGroupAlert;
    Text existingUserAlert;

    private FXFormValidator validator;

    public WorkerAddValidator(String email, String groupName, Text emailAlert, Text emptyGroupAlert,
                              Text existingUserAlert) {
        this.email = email;
        this.emailAlert = emailAlert;
        this.groupName = groupName;
        this.emptyGroupAlert = emptyGroupAlert;
        this.existingUserAlert = existingUserAlert;
    }

    public void validate() {

        clearAlerts();
        validator = new FXFormValidator();
        validator.validateEmail(email, emailAlert);
        validator.validateEmptyOrWhitespaceOnly(groupName, emptyGroupAlert);
    }

    private void clearAlerts() {

        emailAlert.setVisible(false);
        emptyGroupAlert.setVisible(false);
        existingUserAlert.setVisible(false);
    }

    public String getEmail() {
        return email;
    }

    public String getGroupName() { return  groupName; }

    public Text getEmptyGroupAlert() { return  emptyGroupAlert; }

    public Text getEmailAlert() {
        return emailAlert;
    }

    public Text getExistingUserAlert() { return existingUserAlert; }

    public FXFormValidator getValidator() {
        return validator;
    }
}
