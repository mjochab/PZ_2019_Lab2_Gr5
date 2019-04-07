package ur.inf.lab2.pz.servicemanmanagement.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ur.inf.lab2.pz.servicemanmanagement.view.ViewManager;

import java.io.IOException;

@Controller
public class ManagerDataController {
    private ViewManager viewManager;

//    public void submitData(ActionEvent event) throws IOException {
//        System.out.println("ManagerDataController - zatwierdzono\n" +
//                "formularz danych kierownika");
//        viewManager.show(ViewType.MAIN);
//    }

    @FXML
    public void submitManagerData() {

    }


    @Autowired
    public void setViewManager(ViewManager viewManager) {
        this.viewManager = viewManager;
    }
}