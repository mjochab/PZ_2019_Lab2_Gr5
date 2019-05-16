package ur.inf.lab2.pz.servicemanmanagement.services;

import javafx.scene.text.Text;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ur.inf.lab2.pz.servicemanmanagement.domain.SecurityContext;
import ur.inf.lab2.pz.servicemanmanagement.domain.Serviceman;
import ur.inf.lab2.pz.servicemanmanagement.domain.User;
import ur.inf.lab2.pz.servicemanmanagement.domain.dto.workerAddDTO;
import ur.inf.lab2.pz.servicemanmanagement.domain.enums.Roles;
import ur.inf.lab2.pz.servicemanmanagement.repository.AllUsersRepository;
import ur.inf.lab2.pz.servicemanmanagement.repository.RoleRepository;

import java.util.UUID;

@Service
public class EmployeeService {

    @Autowired
    private AllUsersRepository allUsersRepository;

    @Autowired
    private EncryptionService encryptionService;

    @Autowired
    private EmailSender emailSender;

    @Autowired
    private RoleRepository roleRepository;

    public void addWorker(workerAddDTO dto, Text existingUserAlert) {

        existingUserAlert.setVisible(false);

        if (allUsersRepository.findAllByEmail(dto.getEmail()).isEmpty()) {

            User manager = SecurityContext.getLoggedUser();

            String firstPassword = UUID.randomUUID().toString().substring(0, 6);
            Serviceman serviceman = new Serviceman(dto.getEmail(), encryptionService.encode(firstPassword), dto.getGroupName(), manager);
            serviceman.setRole(roleRepository.findByRole(Roles.ROLE_SERVICEMAN.toString()));
            allUsersRepository.save(serviceman);
            emailSender.sendEmail(dto.getEmail(), "Account's First password", firstPassword);

        } else existingUserAlert.setVisible(true);
    }
}
