package ur.inf.lab2.pz.servicemanmanagement.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ur.inf.lab2.pz.servicemanmanagement.view.Layout;
import ur.inf.lab2.pz.servicemanmanagement.view.ViewComponent;
import ur.inf.lab2.pz.servicemanmanagement.view.ViewManager;

import java.io.IOException;
/**
 * klasa kontrolera panelu rejestracji serwisanta
 */
@Controller //TODO FONT TO STYLES
public class ServicemanRegisterController {

    @Autowired private ViewManager viewManager;

    public void register() throws IOException {

        viewManager.switchLayout(Layout.PANEL, ViewComponent.TIMETABLE); }
}
