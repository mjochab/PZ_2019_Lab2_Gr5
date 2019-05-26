package ur.inf.lab2.pz.servicemanmanagement.controller;

import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.scene.text.Text;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ur.inf.lab2.pz.servicemanmanagement.domain.SecurityContext;
import ur.inf.lab2.pz.servicemanmanagement.domain.dto.ServicemanDataDTO;
import ur.inf.lab2.pz.servicemanmanagement.domain.validator.ServicemanDataValidator;
import ur.inf.lab2.pz.servicemanmanagement.services.PanelLayoutService;
import ur.inf.lab2.pz.servicemanmanagement.services.ServicemanDataService;

@Controller
public class ServicemanDataController {

    @Autowired
    private ServicemanDataService servicemanDataService;

    @FXML
    private JFXPasswordField passwordField, confirmPassField;

    @FXML
    private JFXTextField firstNameTextField, lastNameTextField;

    @FXML
    private Text nameAlert, lastNameAlert, passwordAlert, confirmPassAlert;

    @Autowired
    private PanelLayoutService panelLayoutService;

    @FXML
    public void initialize(){
        firstNameTextField.setText(SecurityContext.getLoggedUser().getFirstName());
        lastNameTextField.setText(SecurityContext.getLoggedUser().getLastName());
    }

    public void submitServicemanData() {

        if (validate()) {
            ServicemanDataDTO dto = new ServicemanDataDTO(firstNameTextField.getText(), lastNameTextField.getText(), passwordField.getText(),
                    confirmPassField.getText());
            servicemanDataService.submitServicemanData(dto);
            panelLayoutService.changeName(firstNameTextField.getText(), lastNameTextField.getText());
        }
    }

    private boolean validate() {
        ServicemanDataValidator validator = new ServicemanDataValidator(firstNameTextField.getText(),
                lastNameTextField.getText(),
                passwordField.getText(),
                confirmPassField.getText(),
                nameAlert,
                lastNameAlert,
                passwordAlert,
                confirmPassAlert);
        validator.validate();
        return validator.getValidator().isClean();

    }

}
