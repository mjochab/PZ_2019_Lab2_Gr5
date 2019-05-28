package ur.inf.lab2.pz.servicemanmanagement.controller;

import com.jfoenix.controls.JFXButton;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
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
import ur.inf.lab2.pz.servicemanmanagement.domain.enums.Roles;
import ur.inf.lab2.pz.servicemanmanagement.view.Layout;
import ur.inf.lab2.pz.servicemanmanagement.view.ViewComponent;
import ur.inf.lab2.pz.servicemanmanagement.view.ViewManager;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

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
    private Label timeLabel;

    @FXML
    private Label dateLabel;

    @FXML
    private Label groupNameLabel;

    @FXML
    private JFXButton dashboardButton;
    @FXML
    private JFXButton timetableButton;
    @FXML
    private JFXButton employeeButton;

    @FXML
    public void initialize() {
        initClock();
        if (SecurityContext.getLoggedUser().role.getRole().equals("ROLE_SERVICEMAN")) {

            dashboardButton.setManaged(false);
            dashboardButton.setVisible(false);
            dashboardButton.getChildrenUnmodifiable().forEach(node -> node.setManaged(false));
            employeeButton.setManaged(false);
            employeeButton.setVisible(false);
            employeeButton.getChildrenUnmodifiable().forEach(node -> node.setManaged(false));
            AnchorPane.setTopAnchor(timetableButton, 0.0);

            fullNameLabel.setText(SecurityContext.getLoggedUser().getFirstName() + " " +
                    SecurityContext.getLoggedUser().getLastName());
            roleLabel.setText("Głowa serwisantów");
            groupNameLabel.setText(SecurityContext.getLoggedUser().getGroupName());


        }

        else{
            fullNameLabel.setText(SecurityContext.getLoggedUser().getFirstName() + " " +
                    SecurityContext.getLoggedUser().getLastName());
            groupNameLabel.setText(SecurityContext.getLoggedUser().getCompanyName());
            roleLabel.setText("Kierownik");

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
        final String roleManager = Roles.ROLE_MANAGER.toString();
        final String roleServiceman = Roles.ROLE_SERVICEMAN.toString();
        String actualRole = SecurityContext.getLoggedUser().role.getRole();

        if (roleManager.equals(actualRole))
            viewManager.loadComponent(ViewComponent.TIMETABLE);
        else if (roleServiceman.equals(actualRole))
            viewManager.loadComponent(ViewComponent.SERVICEMAN_TIMETABLE);

    }

    @FXML
    public void navigateToEmployees(ActionEvent event) throws IOException {
        viewManager.loadComponent(ViewComponent.EMPLOYEES);
    }

    @FXML
    public void navigateToManagerData(ActionEvent event) throws IOException {
        final String roleManager = Roles.ROLE_MANAGER.toString();
        final String roleServiceman = Roles.ROLE_SERVICEMAN.toString();
        String actualRole = SecurityContext.getLoggedUser().role.getRole();

        if (roleManager.equals(actualRole))
            viewManager.loadComponent(ViewComponent.MANAGER_DATA);
        else if (roleServiceman.equals(actualRole))
            viewManager.loadComponent(ViewComponent.SERVICEMAN_DATA);

    }

    @FXML
    public void logout(ActionEvent event) throws IOException {
        SecurityContext.setLoggedUser(null);
        viewManager.switchLayout(Layout.START, ViewComponent.LOGIN);
    }

    @FXML
    public void initClock() {
        Timeline clock = new Timeline(new KeyFrame(Duration.ZERO, e -> {
            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
            DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");
            dateLabel.setText(LocalDateTime.now().format(dateFormatter));
            timeLabel.setText(LocalDateTime.now().format(timeFormatter));
        }), new KeyFrame(Duration.seconds(1)));
        clock.setCycleCount(Animation.INDEFINITE);
        clock.play();
    }

    public void newName(String x, String y) {

    }
}
