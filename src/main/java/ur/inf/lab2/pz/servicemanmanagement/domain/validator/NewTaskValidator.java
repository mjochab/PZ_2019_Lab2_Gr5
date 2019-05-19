package ur.inf.lab2.pz.servicemanmanagement.domain.validator;

import javafx.scene.text.Text;

public class NewTaskValidator {
    private String titleTextField;
    private String detailsTextArea;
    Text titleAlert;
    Text detailsAlert;

    private FXFormValidator validator;

    public NewTaskValidator(String titleTextField, String detailsTextArea, Text titleAlert,
            Text detailsAlert) {
        this.titleTextField = titleTextField;
        this.detailsTextArea = detailsTextArea;
        this.titleAlert = titleAlert;
        this.detailsAlert = detailsAlert;
    }

    public void validate() {

        clearAlerts();
        validator = new FXFormValidator();
        validator.validateEmpty(titleTextField, titleAlert);
        validator.validateEmpty(detailsTextArea, detailsAlert);
    }

    private void clearAlerts() {

        titleAlert.setVisible(false);
        detailsAlert.setVisible(false);
    }

    public String getTitleTextField() {
        return titleTextField;
    }

    public String getDetailsTextArea() { return  detailsTextArea; }

    public Text getTitleAlert() { return  titleAlert; }

    public Text getDetailsAlert() {return detailsAlert;
    }
    public FXFormValidator getValidator() {return validator;}
}
