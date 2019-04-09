package ur.inf.lab2.pz.servicemanmanagement.controller;

import com.jfoenix.controls.JFXButton;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ur.inf.lab2.pz.servicemanmanagement.domain.SecurityContext;
import ur.inf.lab2.pz.servicemanmanagement.service.MockSecurityContext;
import ur.inf.lab2.pz.servicemanmanagement.view.Layout;
import ur.inf.lab2.pz.servicemanmanagement.view.ViewComponent;
import ur.inf.lab2.pz.servicemanmanagement.view.ViewManager;

import java.io.IOException;

@Controller
public class PanelLayoutController {

    @Autowired
    private ViewManager viewManager;

    @FXML
    private Button notificationsButton;

    @FXML
    private AnchorPane drawer;

    @FXML
    private Label fullNameLabel;
    @FXML
    private Label roleLabel;

    @FXML
    private JFXButton dashboardButton;
    @FXML
    private JFXButton timetableButton;
    @FXML
    private JFXButton employeeButton;

    @FXML
    public void initialize() {
        if (SecurityContext.getLoggedUser().role.getRole().equals("ROLE_SERVICEMAN")) {
            dashboardButton.setManaged(false);
            dashboardButton.setVisible(false);
            dashboardButton.getChildrenUnmodifiable().forEach(node -> node.setManaged(false));
            employeeButton.setManaged(false);
            employeeButton.setVisible(false);
            employeeButton.getChildrenUnmodifiable().forEach(node -> node.setManaged(false));
            AnchorPane.setTopAnchor(timetableButton, 0.0);

            fullNameLabel.setText("Andrzej Gołota");
            roleLabel.setText("Głowa serwisantów");
        }
    }

    @FXML
    private void drawerAction() throws IOException { // TODO pierwszy klik nie dziala
        TranslateTransition openNav = new TranslateTransition(new Duration(150), drawer);
        openNav.setToX(0);
        TranslateTransition closeNav = new TranslateTransition(new Duration(150), drawer);
        notificationsButton.setOnAction(evt -> {
            if (drawer.getTranslateX() != 0) {
                openNav.play();
            } else {
                closeNav.setToX(-(drawer.getWidth()));
                closeNav.play();
            }
        });
    }

    @FXML
    public void navigateToDashboard(ActionEvent event) throws IOException {
        viewManager.loadComponent(ViewComponent.DASHBOARD);
    }

    @FXML
    public void navigateToTimetable(ActionEvent event) throws IOException {
        viewManager.loadComponent(ViewComponent.TIMETABLE);
    }

    @FXML
    public void navigateToEmployees(ActionEvent event) throws IOException {
        viewManager.loadComponent(ViewComponent.EMPLOYEES);
    }

    @FXML
    public void navigateToManagerData(ActionEvent event) throws IOException {
        switch (MockSecurityContext.loggedUser) {
            case MANAGER: {
                viewManager.loadComponent(ViewComponent.MANAGER_DATA);
                break;
            }
            case SERVICEMAN: {
                viewManager.loadComponent(ViewComponent.SERVICEMAN_DATA);
            }
        }
    }

    @FXML
    public void logout(ActionEvent event) throws IOException {
        SecurityContext.setLoggedUser(null);
        viewManager.switchLayout(Layout.START, ViewComponent.LOGIN);
    }

}
