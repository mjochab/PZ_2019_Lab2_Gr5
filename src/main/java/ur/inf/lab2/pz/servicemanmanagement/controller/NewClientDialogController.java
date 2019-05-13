package ur.inf.lab2.pz.servicemanmanagement.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ur.inf.lab2.pz.servicemanmanagement.domain.dto.NewClientDTO;
import ur.inf.lab2.pz.servicemanmanagement.domain.validator.NewClientValidator;
import ur.inf.lab2.pz.servicemanmanagement.domain.validator.WorkerAddValidator;
import ur.inf.lab2.pz.servicemanmanagement.services.ClientService;

@Controller
public class NewClientDialogController {

    @Autowired
    private ClientService clientService;

    @Autowired
    private NewTaskDialogController newTaskDialogController;

    @FXML
    private JFXTextField firstNameTextField, lastNameTextField, streetTextField, houseNumberTextField,
            apartmentNumberTextField, cityTextField, phoneTextField;

    @FXML
    private JFXButton addNewClientButton, closeDialogButton;

    @FXML
    private Text firstNameAlert, lastNameAlert, streetNumberAlert, houseNumberAlert, apartmentNumberAlert, cityAlert, phoneAlert, jpAlert;

    @FXML
    public void initialize() {
        firstNameAlert.setVisible(false);
        lastNameAlert.setVisible(false);
        streetNumberAlert.setVisible(false);
        houseNumberAlert.setVisible(false);
        apartmentNumberAlert.setVisible(false);
        cityAlert.setVisible(false);
        phoneAlert.setVisible(false);
        jpAlert.setVisible(false);

    }

    @FXML
    void addNewClient(ActionEvent event) {

        if(validate()) {
            NewClientDTO newClientDTO = new NewClientDTO(firstNameTextField.getText(),
                    lastNameTextField.getText(),
                    streetTextField.getText(),
                    Integer.parseInt(houseNumberTextField.getText()),
                    Integer.parseInt(apartmentNumberTextField.getText()),
                    Long.parseLong(phoneTextField.getText()),
                    cityTextField.getText());

            clientService.createNewClient(newClientDTO);

            Stage stage = (Stage) addNewClientButton.getScene().getWindow();
            stage.close();

            newTaskDialogController.loadTable();
        }
    }

    @FXML
    void closeDialog(ActionEvent event) {
        Stage stage = (Stage) closeDialogButton.getScene().getWindow();
        stage.close();
    }

    private boolean validate() {
        NewClientValidator validator = new NewClientValidator(

                firstNameTextField.getText(),
                lastNameTextField.getText(),
                streetTextField.getText(),
                houseNumberTextField.getText(),
                apartmentNumberTextField.getText(),
                cityTextField.getText(),
                phoneTextField.getText(),
                firstNameAlert,
                lastNameAlert,
                streetNumberAlert,
                houseNumberAlert,
                apartmentNumberAlert,
                cityAlert,
                phoneAlert);

        validator.validate();
        return validator.getValidator().isClean();
    }
}