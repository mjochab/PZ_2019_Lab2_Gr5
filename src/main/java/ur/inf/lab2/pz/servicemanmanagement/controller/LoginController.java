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

@Controller
public class LoginController {
    private ViewManager viewManager;

    @FXML
    private JFXTextField emailTextField;


    public void login(ActionEvent event) {


        if (emailTextField.getText().equals("kierownik@test.pl")) {
            System.out.println("Zalogowany jako kierownik \nZmiana widoku na main.fxml");
            viewManager.show(ViewType.MAIN);
        }

        else if (emailTextField.getText().equals("uberserwisant@test.pl")){
            System.out.println("Zalogowany jako serwisant\nZmiana widoku na main.fxml");
            viewManager.show(ViewType.MAIN);

        }
    }

    public void changeView(ActionEvent event) {
        viewManager.show(ViewType.MANAGER_REGISTER);
    }


    @Autowired
    public void setViewManager(ViewManager viewManager) {
        this.viewManager = viewManager;
    }
}
