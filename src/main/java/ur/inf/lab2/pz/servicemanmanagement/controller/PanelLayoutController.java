package ur.inf.lab2.pz.servicemanmanagement.controller;

import com.jfoenix.controls.JFXButton;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.*;
import javafx.util.Duration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ur.inf.lab2.pz.servicemanmanagement.domain.Notification;
import ur.inf.lab2.pz.servicemanmanagement.domain.SecurityContext;
import ur.inf.lab2.pz.servicemanmanagement.domain.enums.Roles;
import ur.inf.lab2.pz.servicemanmanagement.notifications.NotificationService;
import ur.inf.lab2.pz.servicemanmanagement.service.MockSecurityContext;
import ur.inf.lab2.pz.servicemanmanagement.view.Layout;
import ur.inf.lab2.pz.servicemanmanagement.view.ViewComponent;
import ur.inf.lab2.pz.servicemanmanagement.view.ViewManager;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Controller
public class PanelLayoutController {

    @Autowired
    private ViewManager viewManager;

    @Autowired
    private NotificationService notificationService;

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
    private VBox vboxnotifications;


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
                vboxnotifications.getChildren().clear();
                openNav.play();



                List<Notification> notifications =
                        notificationService.getUserNotifications(
                                SecurityContext.getLoggedUser().getId());

                notifications.forEach((notification) -> {
                    VBox vBoxNotification = new VBox();
                    vBoxNotification.setMinWidth(230);
                    vBoxNotification.setMaxWidth(230);
                    vBoxNotification.setBackground(new Background(new BackgroundFill(Color.ROYALBLUE, CornerRadii.EMPTY, Insets.EMPTY)));
                    vBoxNotification.setPadding(new Insets(10, 10, 10,10));
                    vBoxNotification.setSpacing(6);


                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");

                    Text dateAndTime = new Text(notification.getCreationDate().format(formatter));
                    dateAndTime.setFont(Font.font("Verdana", FontWeight.EXTRA_BOLD,12));
                    FlowPane flowPane1 = new FlowPane();
                    flowPane1.setAlignment(Pos.TOP_CENTER);
                    flowPane1.setMinHeight(12);
                    flowPane1.getChildren().addAll(dateAndTime);

                    Text notificationTitle = new Text(notification.getTitle());
                    notificationTitle.setFont(Font.font("Verdana", FontWeight.EXTRA_BOLD,12));
                    TextFlow textFlowTitle = new TextFlow(notificationTitle);

                    Text notificationsDescription = new Text(notification.getDescription());
                    TextFlow textFlowDescription = new TextFlow(notificationsDescription);

                    vBoxNotification.getChildren().addAll(flowPane1, textFlowTitle, textFlowDescription);
                    Button buttonDeleteNotification = new Button("Usuń powiadomienie");
                    buttonDeleteNotification.setStyle("-fx-background-color: linear-gradient(#ff5400, #be1d00);\n" +
                            "    -fx-background-radius: 30;\n" +
                            "    -fx-background-insets: 0;\n" +
                            "    -fx-text-fill: white;");


                    VBox notificationAndButtonComponent = new VBox();
                    notificationAndButtonComponent.setSpacing(5);
                    notificationAndButtonComponent.setAlignment(Pos.TOP_CENTER);
                    notificationAndButtonComponent.setId(String.valueOf(notification.getId()));
                    notificationAndButtonComponent.getChildren().addAll(vBoxNotification, buttonDeleteNotification);

                    vboxnotifications.getChildren().addAll(notificationAndButtonComponent);
                });

                Label end = new Label();

                vboxnotifications.getChildren().addAll(end);

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

}
