package ur.inf.lab2.pz.servicemanmanagement.controller;

import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.scene.text.Text;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ur.inf.lab2.pz.servicemanmanagement.domain.validator.WorkerAddValidator;
import ur.inf.lab2.pz.servicemanmanagement.services.EmployeeService;
import ur.inf.lab2.pz.servicemanmanagement.domain.dto.workerAddDTO;

import java.awt.event.ActionEvent;
import java.io.IOException;

@Controller
public class EmployeesController {

    @Autowired
    private EmployeeService employeeService;

    @FXML
    private JFXTextField emailInput;

    @FXML
    private JFXTextField groupNameInput;

    @FXML
    private Text emailAlert;

    @FXML
    private Text existingUserAlert;

    @FXML
    private Text emptyGroupAlert;

    @FXML
    public void initialize() {
        emailAlert.setVisible(false);
        existingUserAlert.setVisible(false);
        emptyGroupAlert.setVisible(false);
    }

    public void addWorker() {
        if(validate()){
            workerAddDTO dto = new workerAddDTO(emailInput.getText(), groupNameInput.getText());
            employeeService.addWorker(dto, existingUserAlert);
        }
    }

    private boolean validate() {
        WorkerAddValidator validator = new WorkerAddValidator(
                emailInput.getText(),
                groupNameInput.getText(),
                emailAlert,
                emptyGroupAlert,
                existingUserAlert);
        validator.validate();
        return validator.getValidator().isClean();
    }
}

