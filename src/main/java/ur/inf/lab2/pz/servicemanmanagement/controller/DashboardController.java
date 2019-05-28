package ur.inf.lab2.pz.servicemanmanagement.controller;

import com.jfoenix.controls.JFXTextField;
import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.util.Duration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ur.inf.lab2.pz.servicemanmanagement.ExampleService;
import ur.inf.lab2.pz.servicemanmanagement.repository.RoleRepository;
import ur.inf.lab2.pz.servicemanmanagement.services.EmailSenderImpl;
import ur.inf.lab2.pz.servicemanmanagement.services.EncryptionService;
import ur.inf.lab2.pz.servicemanmanagement.services.UserService;
import ur.inf.lab2.pz.servicemanmanagement.view.ViewManager;

import java.io.IOException;
/**
 * klasa kontrolera głównego dashboardu
 */
@Controller
public class DashboardController {

    @Autowired
    private EmailSenderImpl emailSender;

    @Autowired
    private UserService userService;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private EncryptionService encryptionService;

    @FXML
    private JFXTextField emailInput;

    @FXML
    private JFXTextField groupNameInput;

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

//    @FXML
//    public void addWorker(ActionEvent event) {
//
//        Role role = roleRepository.findByRole("ROLE_SERVICEMAN");
//        System.out.println(emailInput.getText());
//        System.out.println(groupNameInput.getText());
//        String firstPassword = UUID.randomUUID().toString().substring(0, 6);
//        String groupName = groupNameInput.getText();
//        // User user = new User(emailInput.getText(), firstPassword, groupName);
//        User newUser = new User();
//        newUser.setEmail(emailInput.getText());
//        newUser.setGroupName(groupName);
//        newUser.setPassword(encryptionService.encode(firstPassword));
//        newUser.setRole(role);
//
//        userRepository.save(newUser);
//        emailSender.sendEmail(emailInput.getText(), "Account's First password", firstPassword);
//    }

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


