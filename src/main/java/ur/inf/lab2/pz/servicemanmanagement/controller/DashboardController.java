package ur.inf.lab2.pz.servicemanmanagement.controller;

import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.util.Duration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ur.inf.lab2.pz.servicemanmanagement.ExampleService;
import ur.inf.lab2.pz.servicemanmanagement.view.ViewManager;
import ur.inf.lab2.pz.servicemanmanagement.view.ViewType;

import javax.swing.text.View;
import java.io.IOException;

@Controller
public class DashboardController{

    @FXML
    private Button notificationsButton;

    @FXML
    private VBox drawer;

    private ViewManager viewManager;
    private ExampleService exampleService;

    @Autowired
    public void setExampleService(ExampleService exampleService) {
        this.exampleService = exampleService;
    }

    @Autowired
    public void setViewManager(ViewManager viewManager) {
        this.viewManager = viewManager;
    }





    @FXML
    public void showTimetable(javafx.event.ActionEvent event) {
        exampleService.log("Zmiana widoku na HARMONOGRAM");
    }

    @FXML
    public void showEmployees(javafx.event.ActionEvent event) {
        exampleService.log("Zmiana widoku na PRACOWNICY");
    }

    @FXML
    public void logout(javafx.event.ActionEvent event) {
        exampleService.log("Wylogowanie z systemu");
    }

    @FXML
    public void showManagerData(ActionEvent event) {
        viewManager.show(ViewType.MANAGER_DATA);
    }

    @FXML
    public void submitManagerData(ActionEvent event) {
        viewManager.show(ViewType.DASHBOARD);
    }

    @FXML
    public void showDashboard(ActionEvent event) {
        viewManager.show(ViewType.DASHBOARD);
    }

    @FXML
    private void drawerAction() throws IOException {

        TranslateTransition openNav = new TranslateTransition(new Duration(350), drawer);
        openNav.setToX(0);
        TranslateTransition closeNav = new TranslateTransition(new Duration(350), drawer);
        notificationsButton.setOnAction(evt -> {
            if (drawer.getTranslateX() != 0) {
                openNav.play();
            } else {
                closeNav.setToX(-(drawer.getWidth()));
                closeNav.play();
            }
        });
    }



}


