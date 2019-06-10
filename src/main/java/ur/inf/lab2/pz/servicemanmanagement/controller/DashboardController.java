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


    /**
     * Metoda odpowiedzialna za akcje bocznego panelu powiadomień
     */
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


