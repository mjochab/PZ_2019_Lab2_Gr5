package ur.inf.lab2.pz.servicemanmanagement.services;

import javafx.scene.text.Text;
import org.springframework.stereotype.Service;
import ur.inf.lab2.pz.servicemanmanagement.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import ur.inf.lab2.pz.servicemanmanagement.repository.RoleRepository;
import ur.inf.lab2.pz.servicemanmanagement.repository.UserRepository;
import ur.inf.lab2.pz.servicemanmanagement.services.EmailSender;
import ur.inf.lab2.pz.servicemanmanagement.services.EncryptionService;
import ur.inf.lab2.pz.servicemanmanagement.domain.enums.Roles;
import ur.inf.lab2.pz.servicemanmanagement.domain.dto.workerAddDTO;

import java.io.IOException;
import java.util.UUID;

@Service
public class EmployeeService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EncryptionService encryptionService;

    @Autowired
    private EmailSender emailSender;

    @Autowired
    private RoleRepository roleRepository;

    public void addWorker(workerAddDTO dto, Text existingUserAlert)  {

        existingUserAlert.setVisible(false);

        if(userRepository.findAllByEmail(dto.getEmail()).isEmpty()){

            String firstPassword = UUID.randomUUID().toString().substring(0, 6);
            User user = new User(dto.getEmail(), encryptionService.encode(firstPassword), dto.getGroupName());
            user.setRole(roleRepository.findByRole(Roles.ROLE_SERVICEMAN.toString()));
            userRepository.save(user);
            emailSender.sendEmail(dto.getEmail(), "Account's First password", firstPassword);

        } else existingUserAlert.setVisible(true);
        }
}
