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

import java.io.IOException;

@Controller
public class ManagerRegisterController {
    private ViewManager viewManager;

    public void changeView(ActionEvent event) throws IOException {
        viewManager.show(ViewType.LOGIN);
    }

    public void register(ActionEvent event) throws IOException {
        System.out.println("Pomyślna rejestracja");
        viewManager.show(ViewType.DASHBOARD);
    }

    @Autowired
    public void setViewManager(ViewManager viewManager) {
        this.viewManager = viewManager;
    }
}
