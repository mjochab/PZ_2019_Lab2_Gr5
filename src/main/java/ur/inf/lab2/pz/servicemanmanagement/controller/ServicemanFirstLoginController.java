package ur.inf.lab2.pz.servicemanmanagement.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ur.inf.lab2.pz.servicemanmanagement.domain.dto.ServicemanFirstLoginDTO;
import ur.inf.lab2.pz.servicemanmanagement.repository.UserRepository;
import ur.inf.lab2.pz.servicemanmanagement.services.ServicemanFirstLoginService;

import java.io.IOException;

@Controller
public class ServicemanFirstLoginController {


    @Autowired
    UserRepository userRepository;
    @Autowired
    ServicemanFirstLoginService servicemanFirstLoginService;

    @FXML
    private JFXButton loginButton;
    @FXML
    private JFXPasswordField newPasswordField;
    @FXML
    private JFXPasswordField repeatPasswordField;
    @FXML
    private JFXTextField firstNameField;
    @FXML
    private JFXTextField lastNameField;

    @FXML
    private Label alert;

    @FXML
    public void servicemanLogin(ActionEvent event) throws IOException {
        ServicemanFirstLoginDTO servicemanFirstLoginDTO = new ServicemanFirstLoginDTO(
                firstNameField.getText(),
                lastNameField.getText(),
                newPasswordField.getText(),
                repeatPasswordField.getText());
        try {
            servicemanFirstLoginService.servicemanLogin(servicemanFirstLoginDTO);
        } catch (IOException e) {
            alert.setVisible(true);
        }
    }

}
