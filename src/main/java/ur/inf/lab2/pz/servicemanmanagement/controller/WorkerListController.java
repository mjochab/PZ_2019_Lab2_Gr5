package ur.inf.lab2.pz.servicemanmanagement.controller;
import javafx.event.ActionEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ur.inf.lab2.pz.servicemanmanagement.ExampleService;
import ur.inf.lab2.pz.servicemanmanagement.view.ViewManager;
import ur.inf.lab2.pz.servicemanmanagement.view.ViewType;

import java.io.IOException;

@Controller
public class WorkerListController {

    private ViewManager viewManager;
    private ExampleService exampleService;

    @Autowired
    public void setExampleService(ExampleService exampleService) {
        this.exampleService = exampleService;
    }

    public void add(ActionEvent actionEvent) throws IOException {
        exampleService.log("Zmiana widoku na main.fxml");
        viewManager.show(ViewType.MAIN);
    }

    @Autowired
    public void setViewManager(ViewManager viewManager) {
        this.viewManager = viewManager;
    }




}
