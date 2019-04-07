package ur.inf.lab2.pz.servicemanmanagement.controller;

import com.jfoenix.controls.JFXTextField;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ur.inf.lab2.pz.servicemanmanagement.view.Layout;
import ur.inf.lab2.pz.servicemanmanagement.view.ViewComponent;
import ur.inf.lab2.pz.servicemanmanagement.view.ViewManager;

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
            viewManager.switchLayout(Layout.PANEL, ViewComponent.DASHBOARD);
        }

        else if (emailTextField.getText().equals("uberserwisant@test.pl")){
//            viewManager.show(ViewType.SERVICEMAN_REGISTER);

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
