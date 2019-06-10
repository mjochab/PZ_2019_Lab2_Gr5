package ur.inf.lab2.pz.servicemanmanagement.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ur.inf.lab2.pz.servicemanmanagement.controller.PanelLayoutController;

/**
 * Kalsa wykorzystywana podczas generowania danych do panelu użytkownika
 */
@Service
public class PanelLayoutService {

    @Autowired
    PanelLayoutController panelLayoutController;

    public void changeName(String firstName, String lastName){
        panelLayoutController.newName(firstName, lastName);
    }
}
