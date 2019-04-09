package ur.inf.lab2.pz.servicemanmanagement.controller;

import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.text.Text;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ur.inf.lab2.pz.servicemanmanagement.view.ViewComponent;
import ur.inf.lab2.pz.servicemanmanagement.domain.enums.Roles;
import ur.inf.lab2.pz.servicemanmanagement.services.UserService;
import ur.inf.lab2.pz.servicemanmanagement.view.ViewManager;


import java.io.IOException;

@Controller
public class ManagerRegisterController {
    private ViewManager viewManager;

    public void navigateToLogin(ActionEvent event) throws IOException {
        viewManager.loadComponent(ViewComponent.LOGIN);
    }

    @FXML
    private JFXTextField firstNameTextField, lastNameTextField, companyNameTextField, emailTextField;

    @FXML
    private JFXPasswordField password, confirmPassword;

    @Autowired
    private UserService userService;

    @FXML
    private Text firstNameAlert,lastNameAlert,emailAlert,companyNameAlert,privacyAlert, existingUserAlert;

    @FXML
    public void initialize() {
        firstNameAlert.setVisible(false);
        lastNameAlert.setVisible(false);
        emailAlert.setVisible(false);
        companyNameAlert.setVisible(false);
        privacyAlert.setVisible(false);
        existingUserAlert.setVisible(false);
    }

    public void register(ActionEvent event) throws IOException {

        userService.createUser(firstNameTextField.getText(), lastNameTextField.getText(),
                companyNameTextField.getText(), emailTextField.getText(),
                password.getText(), confirmPassword.getText(), Roles.ROLE_MANAGER.toString(),firstNameAlert,lastNameAlert,emailAlert,companyNameAlert,privacyAlert, existingUserAlert);
    }

    @Autowired
    public void setViewManager(ViewManager viewManager) {
        this.viewManager = viewManager;
    }
}
