package ur.inf.lab2.pz.servicemanmanagement.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ur.inf.lab2.pz.servicemanmanagement.ExampleService;
import ur.inf.lab2.pz.servicemanmanagement.view.ViewManager;
import ur.inf.lab2.pz.servicemanmanagement.view.ViewType;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import java.io.IOException;

@Controller
public class LoginController {
    private ViewManager viewManager;

    @FXML
    private JFXTextField emailTextField;


    public void login(ActionEvent event) throws IOException {


        if (emailTextField.getText().equals("kierownik@test.pl")) {
            viewManager.show(ViewType.DASHBOARD);
        }

        else if (emailTextField.getText().equals("uberserwisant@test.pl")){
            viewManager.show(ViewType.SERVICEMAN_REGISTER);

        }
    }

    public void changeView(ActionEvent event) throws IOException {
        viewManager.show(ViewType.MANAGER_REGISTER);
    }


    @Autowired
    public void setViewManager(ViewManager viewManager) {
        this.viewManager = viewManager;
    }
}
