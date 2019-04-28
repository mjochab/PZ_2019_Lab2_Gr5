package ur.inf.lab2.pz.servicemanmanagement.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ur.inf.lab2.pz.servicemanmanagement.domain.dto.NewClientDTO;
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
    void addNewClient(ActionEvent event) {

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

    @FXML
    void closeDialog(ActionEvent event) {
        Stage stage = (Stage) closeDialogButton.getScene().getWindow();
        stage.close();
    }

}
