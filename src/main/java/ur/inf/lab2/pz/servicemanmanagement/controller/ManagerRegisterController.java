package ur.inf.lab2.pz.servicemanmanagement.controller;

import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ur.inf.lab2.pz.servicemanmanagement.domain.enums.Roles;
import ur.inf.lab2.pz.servicemanmanagement.services.UserService;
import ur.inf.lab2.pz.servicemanmanagement.view.ViewManager;
import ur.inf.lab2.pz.servicemanmanagement.view.ViewType;

import java.io.IOException;

@Controller
public class ManagerRegisterController {
    private ViewManager viewManager;

    @FXML
    private JFXTextField firstNameTextField, lastNameTextField, companyNameTextField, emailTextField, password, confirmPassword;

    @Autowired
    private UserService userService;


    public void changeView(ActionEvent event) throws IOException {
        viewManager.show(ViewType.LOGIN);
    }

    public void register(ActionEvent event) throws IOException {

        userService.createUser(firstNameTextField.getText(), lastNameTextField.getText(),
                companyNameTextField.getText(), emailTextField.getText(),
                password.getText(), confirmPassword.getText(), Roles.ROLE_MANAGER.toString());
    }

    @Autowired
    public void setViewManager(ViewManager viewManager) {
        this.viewManager = viewManager;
    }
}
