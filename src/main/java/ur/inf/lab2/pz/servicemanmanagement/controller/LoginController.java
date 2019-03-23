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
    private ExampleService exampleService;

    @FXML
    private JFXTextField emailTextField;
    @FXML
    private JFXButton login;

    public void changeView(ActionEvent event) {
        exampleService.log("Zmiana widoku na main.fxml");

        if (emailTextField.getText().equals("kierownik@test.pl"))
            exampleService.log("Zalogowany jako kierownik");
        else if (emailTextField.getText().equals("uberserwisant@test.pl"))
            exampleService.log("Zalogowany jako serwisant");
        viewManager.show(ViewType.MAIN);
    }

    @Autowired
    public void setExampleService(ExampleService exampleService) {
        this.exampleService = exampleService;
    }

    @Autowired
    public void setViewManager(ViewManager viewManager) {
        this.viewManager = viewManager;
    }
}
