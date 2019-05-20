package ur.inf.lab2.pz.servicemanmanagement.controller;

import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ur.inf.lab2.pz.servicemanmanagement.domain.dto.ManagerDataDTO;
import ur.inf.lab2.pz.servicemanmanagement.services.ManagerDataService;
import ur.inf.lab2.pz.servicemanmanagement.view.ViewManager;

import java.io.IOException;

@Controller
public class ManagerDataController {

    @Autowired
    private ManagerDataService managerDataService;

    @FXML
    private JFXPasswordField passwordField, confirmPassField;

    @FXML
    private JFXTextField nameField, lastNameField, companyNameField;

    private ViewManager viewManager;

//    public void submitData(ActionEvent event) throws IOException {
//        System.out.println("ManagerDataController - zatwierdzono\n" +
//                "formularz danych kierownika");
//        viewManager.show(ViewType.MAIN);
//    }

    @FXML
    public void submitManagerData() {
        ManagerDataDTO dto = new ManagerDataDTO(nameField.getText(), lastNameField.getText(), companyNameField.getText(),
                passwordField.getText(), confirmPassField.getText());
        managerDataService.submitManagerData(dto);
    }


    @Autowired
    public void setViewManager(ViewManager viewManager) {
        this.viewManager = viewManager;
    }
}