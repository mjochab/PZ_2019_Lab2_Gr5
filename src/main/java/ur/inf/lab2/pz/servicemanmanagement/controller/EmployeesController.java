package ur.inf.lab2.pz.servicemanmanagement.controller;

import javafx.fxml.FXML;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ur.inf.lab2.pz.servicemanmanagement.domain.MyEntity;
import ur.inf.lab2.pz.servicemanmanagement.repository.ExampleRepository;

import java.util.List;

@Controller
public class EmployeesController {

    @Autowired
    private ExampleRepository exampleRepository;

    @FXML
    public void initialize() {

    }

    @FXML
    public void addWorker() {
        exampleRepository.findAll();


    }
}
