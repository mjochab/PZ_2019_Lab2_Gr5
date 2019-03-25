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
    private ExampleService exampleService;

    public void changeView(ActionEvent event) {
        exampleService.log("Zmiana widoku na secondary.fxml");
        viewManager.show(ViewType.DASHBOARD);
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
