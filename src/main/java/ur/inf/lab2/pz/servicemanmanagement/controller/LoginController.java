package ur.inf.lab2.pz.servicemanmanagement.controller;

import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.scene.text.Text;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ur.inf.lab2.pz.servicemanmanagement.services.UserService;
import ur.inf.lab2.pz.servicemanmanagement.view.ViewManager;
import ur.inf.lab2.pz.servicemanmanagement.view.ViewType;

import java.io.IOException;

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


    public void login(ActionEvent event) throws NullPointerException, IOException {
        userService.userLogin(emailTextField.getText(), passwordTextField.getText(), alertLabel);

    }

    public void changeView(ActionEvent event) throws IOException {
        viewManager.show(ViewType.MANAGER_REGISTER);
    }


    @Autowired
    public void setViewManager(ViewManager viewManager) {
        this.viewManager = viewManager;
    }
}
