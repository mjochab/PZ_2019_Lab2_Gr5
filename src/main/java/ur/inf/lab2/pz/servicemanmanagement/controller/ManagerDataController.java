package ur.inf.lab2.pz.servicemanmanagement.controller;

import com.jfoenix.controls.*;
import com.sun.corba.se.impl.encoding.CodeSetComponentInfo;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ur.inf.lab2.pz.servicemanmanagement.domain.SecurityContext;
import ur.inf.lab2.pz.servicemanmanagement.domain.dto.ManagerDataDTO;
import ur.inf.lab2.pz.servicemanmanagement.domain.validator.ManagerDataValidator;
import ur.inf.lab2.pz.servicemanmanagement.services.ManagerDataService;
import ur.inf.lab2.pz.servicemanmanagement.services.PanelLayoutService;
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

    @FXML
    private Text nameAlert, lastNameAlert, companyNameAlert, passwordAlert, confirmPassAlert;

    @FXML
    private StackPane stackPane;

    @Autowired
    private PanelLayoutService panelLayoutService;

    private ViewManager viewManager;

//    public void submitData(ActionEvent event) throws IOException {
//        System.out.println("ManagerDataController - zatwierdzono\n" +
//                "formularz danych kierownika");
//        viewManager.show(ViewType.MAIN);
//    }
@FXML
public void initialize() {
    nameAlert.setVisible(false);
    lastNameAlert.setVisible(false);
    companyNameAlert.setVisible(false);
    passwordAlert.setVisible(false);
    confirmPassAlert.setVisible(false);
    nameField.setText(SecurityContext.getLoggedUser().getFirstName());
    lastNameField.setText(SecurityContext.getLoggedUser().getLastName());
    companyNameField.setText(SecurityContext.getLoggedUser().getCompanyName());

}

    @FXML
    public void submitManagerData() {
        if (validate()) {
            ManagerDataDTO dto = new ManagerDataDTO(nameField.getText(), lastNameField.getText(), companyNameField.getText(),
                    passwordField.getText(), confirmPassField.getText());
            managerDataService.submitManagerData(dto);
            panelLayoutService.changeName(nameField.getText(), lastNameField.getText());

            loadDialog();
        }
    }

    @FXML
    public void newName(String firstName, String lastName, String companyName){
        if (SecurityContext.getLoggedUser().role.getRole().equals("ROLE_MANAGER")){
            nameField.setText(firstName);
            lastNameField.setText(lastName);
            companyNameField.setText(companyName);
        }
    }

    private boolean validate() {
        ManagerDataValidator validator = new ManagerDataValidator(nameField.getText(),
                lastNameField.getText(),
                companyNameField.getText(),
                passwordField.getText(),
                confirmPassField.getText(),
                nameAlert,
                lastNameAlert,
                companyNameAlert,
                passwordAlert,
                confirmPassAlert);
        validator.validate();
        return validator.getValidator().isClean();
    }

    @Autowired
    public void setViewManager(ViewManager viewManager) {
        this.viewManager = viewManager;
    }



    @FXML
    private void loadDialog() {
        JFXDialogLayout content = new JFXDialogLayout();
        content.setHeading(new Text("OK"));
        content.setBody(new Text("Zmieniono dane personalne"));
        JFXDialog dialog = new JFXDialog(stackPane, content, JFXDialog.DialogTransition.CENTER);
        JFXButton button = new JFXButton("OK");
        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                dialog.close();
            }
        });
        content.setActions(button);
        dialog.show();
    }

}