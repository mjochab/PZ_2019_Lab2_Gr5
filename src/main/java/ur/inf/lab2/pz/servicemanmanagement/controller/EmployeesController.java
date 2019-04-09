package ur.inf.lab2.pz.servicemanmanagement.controller;

import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ur.inf.lab2.pz.servicemanmanagement.domain.MyEntity;
import ur.inf.lab2.pz.servicemanmanagement.domain.User;
import ur.inf.lab2.pz.servicemanmanagement.domain.enums.Roles;
import ur.inf.lab2.pz.servicemanmanagement.repository.ExampleRepository;
import ur.inf.lab2.pz.servicemanmanagement.repository.RoleRepository;
import ur.inf.lab2.pz.servicemanmanagement.repository.UserRepository;
import ur.inf.lab2.pz.servicemanmanagement.services.EmailSender;
import ur.inf.lab2.pz.servicemanmanagement.services.EncryptionService;

import java.util.List;
import java.util.UUID;

@Controller
public class EmployeesController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EmailSender emailSender;

    @Autowired
    private EncryptionService encryptionService;

    @FXML
    private JFXTextField emailInput;

    @FXML
    private JFXTextField groupNameInput;

    @Autowired
    private RoleRepository roleRepository;


    @FXML
    public void initialize() {

    }

    @FXML
    public void addWorker() {
        System.out.println(emailInput.getText());
        System.out.println(groupNameInput.getText());
        String firstPassword = UUID.randomUUID().toString().substring(0,6);
        String groupName = groupNameInput.getText();
        User user = new User(emailInput.getText(), encryptionService.encode(firstPassword), groupName);
        user.setRole(roleRepository.findByRole(Roles.ROLE_SERVICEMAN.toString()));
        userRepository.save(user);
        emailSender.sendEmail(emailInput.getText(), "Account's First password", firstPassword);
        emailInput.clear();
        groupNameInput.clear();
    }
}
