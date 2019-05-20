package ur.inf.lab2.pz.servicemanmanagement.controller;

import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ur.inf.lab2.pz.servicemanmanagement.domain.dto.ServicemanDataDTO;
import ur.inf.lab2.pz.servicemanmanagement.services.ServicemanDataService;

@Controller
public class ServicemanDataController {

    @Autowired
    private ServicemanDataService servicemanDataService;

    @FXML
    private JFXPasswordField passwordField, confirmPassField;

    @FXML
    private JFXTextField nameField, lastNameField;

    public void submitServicemanData() {
        ServicemanDataDTO dto = new ServicemanDataDTO(nameField.getText(), lastNameField.getText(), passwordField.getText(),
                confirmPassField.getText());
        servicemanDataService.submitServicemanData(dto);

    }

}
