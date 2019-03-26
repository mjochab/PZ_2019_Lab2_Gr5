package ur.inf.lab2.pz.servicemanmanagement.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ur.inf.lab2.pz.servicemanmanagement.view.ViewManager;
import ur.inf.lab2.pz.servicemanmanagement.view.ViewType;

import javafx.event.ActionEvent;

import java.io.IOException;

@Controller
public class ServicemanRegisterController {
    private ViewManager viewManager;

    public void register(ActionEvent event) throws IOException {
        System.out.println("Zarejestrowano nowego uber serwisanta");
        viewManager.show(ViewType.SERVICEMAN_TIMETABLE);
    }

    @Autowired
    public void setViewManager(ViewManager viewManager) {
        this.viewManager = viewManager;
    }
}
