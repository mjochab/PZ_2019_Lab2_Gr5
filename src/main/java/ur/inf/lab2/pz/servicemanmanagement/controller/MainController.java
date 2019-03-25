package ur.inf.lab2.pz.servicemanmanagement.controller;

import javafx.event.ActionEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ur.inf.lab2.pz.servicemanmanagement.ExampleService;
import ur.inf.lab2.pz.servicemanmanagement.view.ViewManager;
import ur.inf.lab2.pz.servicemanmanagement.view.ViewType;

@Controller
public class MainController {

    private ViewManager viewManager;

    public void changeView(ActionEvent event) {
        System.out.println("Zmiana widoku na secondary.fxml");
        viewManager.show(ViewType.SECONDARY);
    }

    public void changeViewToLoginForm(ActionEvent event) {
        System.out.println("Zmiana widoku na login.fxml");
        viewManager.show(ViewType.LOGIN);
    }

    public void changeViewToServicemanRegistration(ActionEvent event) {
        System.out.println("Zmiana widoku na servicemanRegister.fxml");
        viewManager.show(ViewType.SERVICEMAN_REGISTER);
    }
    @Autowired
    public void setViewManager(ViewManager viewManager) {
        this.viewManager = viewManager;
    }
}
