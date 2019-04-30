package ur.inf.lab2.pz.servicemanmanagement.controller;

import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.text.Text;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ur.inf.lab2.pz.servicemanmanagement.domain.User;
import ur.inf.lab2.pz.servicemanmanagement.domain.dto.ManagerRegisterDTO;
import ur.inf.lab2.pz.servicemanmanagement.domain.validator.UserRegisterFormValidator;
import ur.inf.lab2.pz.servicemanmanagement.view.ViewComponent;
import ur.inf.lab2.pz.servicemanmanagement.domain.enums.Roles;
import ur.inf.lab2.pz.servicemanmanagement.services.UserService;
import ur.inf.lab2.pz.servicemanmanagement.view.ViewManager;


import java.io.IOException;

@Controller
public class ManagerRegisterController {

    private UserService userService;
    private ViewManager viewManager;

    @FXML
    private JFXPasswordField password, confirmPassword;

    @FXML
    private JFXTextField companyNameTextField, emailTextField,firstNameTextField, lastNameTextField;



    @FXML
    private Text companyNameAlert, emailAlert, existingUserAlert, firstNameAlert,lastNameAlert,privacyAlert;



    @FXML
    public void initialize() {
        firstNameAlert.setVisible(false);
        lastNameAlert.setVisible(false);
        emailAlert.setVisible(false);
        companyNameAlert.setVisible(false);
        privacyAlert.setVisible(false);
        existingUserAlert.setVisible(false);

    }

    @Autowired
    public void setViewManager(ViewManager viewManager) {
        this.viewManager = viewManager;
    }

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    public void register(ActionEvent event) throws IOException {

        if (validate()) {
            ManagerRegisterDTO dto = new ManagerRegisterDTO(firstNameTextField.getText(),
                    lastNameTextField.getText(),
                    companyNameTextField.getText(),
                    emailTextField.getText(),
                    password.getText(),
                    Roles.ROLE_MANAGER.toString());

            userService.createUser(dto,existingUserAlert);
        }

    }

    private boolean validate() {
        UserRegisterFormValidator validator = new UserRegisterFormValidator(firstNameTextField.getText(),
                lastNameTextField.getText(),
                companyNameTextField.getText(),
                emailTextField.getText(),
                password.getText(),
                confirmPassword.getText(),
                firstNameAlert,
                lastNameAlert,
                companyNameAlert,
                emailAlert,
                privacyAlert,
                existingUserAlert);
        validator.validate();
        return validator.getValidator().isClean();

    }

    public void navigateToLogin(ActionEvent event) throws IOException {
        viewManager.loadComponent(ViewComponent.LOGIN);
    }
}
