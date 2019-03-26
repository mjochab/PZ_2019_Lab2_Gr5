package ur.inf.lab2.pz.servicemanmanagement.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ur.inf.lab2.pz.servicemanmanagement.ExampleService;
import ur.inf.lab2.pz.servicemanmanagement.view.ViewManager;
import ur.inf.lab2.pz.servicemanmanagement.view.ViewType;
import javafx.fxml.FXML;

import javafx.event.ActionEvent;

@Controller
public class ManagerRegisterController {
    private ViewManager viewManager;

    public void changeView(ActionEvent event) {
        viewManager.show(ViewType.LOGIN);
    }

    public void register(ActionEvent event) {
        System.out.println("Pomyślna rejestracja");
        viewManager.show(ViewType.MAIN);
    }

    @Autowired
    public void setViewManager(ViewManager viewManager) {
        this.viewManager = viewManager;
    }
}
