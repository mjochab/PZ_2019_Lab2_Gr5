package ur.inf.lab2.pz.servicemanmanagement.controller;

import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.scene.text.Text;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ur.inf.lab2.pz.servicemanmanagement.services.UserService;
import ur.inf.lab2.pz.servicemanmanagement.view.ViewComponent;
import ur.inf.lab2.pz.servicemanmanagement.view.ViewManager;

import java.io.IOException;

/**
 * klasa kontrolera panelu logowania */
@Controller
public class LoginController {

    @Autowired
    private UserService userService;

    private ViewManager viewManager;

    @FXML
    private JFXTextField emailTextField;

    @FXML
    private JFXPasswordField passwordTextField;

    @FXML
    private  Text alertLabel;


    @FXML
    public void initialize() {
        alertLabel.setVisible(false);
    }

    public void login(ActionEvent event) {
        try {
            userService.userLogin(emailTextField.getText(), passwordTextField.getText());
        } catch (IOException e) {
            alertLabel.setVisible(true);
        }
    }

    @FXML
    public void navigateToRegister(ActionEvent event) throws IOException {
        viewManager.loadComponent(ViewComponent.MANAGER_REGISTER);
    }

    @Autowired
    public void setViewManager(ViewManager viewManager) {
        this.viewManager = viewManager;
    }
}
