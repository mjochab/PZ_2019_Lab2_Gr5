package ur.inf.lab2.pz.servicemanmanagement.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ur.inf.lab2.pz.servicemanmanagement.view.Layout;
import ur.inf.lab2.pz.servicemanmanagement.view.ViewComponent;
import ur.inf.lab2.pz.servicemanmanagement.view.ViewManager;

import javafx.event.ActionEvent;

import java.io.IOException;

@Controller
public class ManagerRegisterController {
    private ViewManager viewManager;

    public void navigateToLogin(ActionEvent event) throws IOException {
        viewManager.loadComponent(ViewComponent.LOGIN);
    }

    public void register(ActionEvent event) throws IOException {
        viewManager.switchLayout(Layout.PANEL, ViewComponent.DASHBOARD);
    }

    @Autowired
    public void setViewManager(ViewManager viewManager) {
        this.viewManager = viewManager;
    }
}
