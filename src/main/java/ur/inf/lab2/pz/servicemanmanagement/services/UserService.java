package ur.inf.lab2.pz.servicemanmanagement.services;

import javafx.scene.text.Text;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ur.inf.lab2.pz.servicemanmanagement.domain.*;
import ur.inf.lab2.pz.servicemanmanagement.domain.dto.ManagerRegisterDTO;
import ur.inf.lab2.pz.servicemanmanagement.domain.dto.ServicemanFirstLoginDTO;
import ur.inf.lab2.pz.servicemanmanagement.domain.enums.Roles;
import ur.inf.lab2.pz.servicemanmanagement.notifications.NotificationService;
import ur.inf.lab2.pz.servicemanmanagement.repository.AllUsersRepository;
import ur.inf.lab2.pz.servicemanmanagement.repository.ManagerRepository;
import ur.inf.lab2.pz.servicemanmanagement.repository.RoleRepository;
import ur.inf.lab2.pz.servicemanmanagement.repository.ServicemanRepository;
import ur.inf.lab2.pz.servicemanmanagement.view.Layout;
import ur.inf.lab2.pz.servicemanmanagement.view.ViewComponent;
import ur.inf.lab2.pz.servicemanmanagement.view.ViewManager;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {


    @Autowired
    private ManagerRepository managerRepository;

    @Autowired
    private AllUsersRepository allUsersRepository;

    @Autowired
    private ServicemanRepository servicemanRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private EncryptionService encryptionService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private ViewManager viewManager;

    @Autowired
    private NotificationService notificationService;

    public void userLogin(String email, String password) throws IOException {

        List<User> allByEmail = allUsersRepository.findAllByEmail(email);

        User user = null;
        if (allByEmail.isEmpty())
            throw new IOException();
        else
            user = allByEmail.get(0);

        if (passwordEncoder.matches(password, user.getPassword())) {

            SecurityContext.setLoggedUser(user);

            if (SecurityContext.getLoggedUser().getRole().getRole().equals(Roles.ROLE_MANAGER.toString()))
                viewManager.switchLayout(Layout.PANEL, ViewComponent.DASHBOARD);

            else if (SecurityContext.getLoggedUser().getRole().getRole().equals(Roles.ROLE_SERVICEMAN.toString())) {
                if (!SecurityContext.getLoggedUser().isEnabled()) {
                    viewManager.loadComponent(ViewComponent.SERVICEMAN_REGISTER);
                } else viewManager.switchLayout(Layout.PANEL, ViewComponent.SERVICEMAN_TIMETABLE);
            } else throw new IOException();
        } else throw new IOException();

    }

    public void createUser(ManagerRegisterDTO dto, Text existingUserAlert) throws IOException {

        Role role = Optional.of(roleRepository.findByRole(dto.getRole())).orElseThrow(() -> new IOException());

        existingUserAlert.setVisible(false);
        if (allUsersRepository.findAllByEmail(dto.getEmail()).isEmpty()) {
            Manager manager = new Manager();
            manager.setFirstName(dto.getFirstName());
            manager.setLastName(dto.getLastName());
            manager.setCompanyName(dto.getCompanyName());
            manager.setEmail(dto.getEmail());
            manager.setPassword(encryptionService.encode(dto.getPassword()));
            manager.setRole(role);
            allUsersRepository.save(manager);
            viewManager.loadComponent(ViewComponent.LOGIN);
        } else existingUserAlert.setVisible(true);
    }


    public void changePersonalData(ServicemanFirstLoginDTO data) throws IOException {
        Serviceman currentUser = (Serviceman) SecurityContext.getLoggedUser();

        currentUser.setFirstName(data.getFirstName());
        currentUser.setLastName(data.getLastName());
        currentUser.setPassword(encryptionService.encode(data.getPassword()));
        currentUser.setEnabled(true);
        servicemanRepository.save(currentUser);

        notificationService.addNotification(
                "Pierwsze logowanie",
                        "Użytkownik " + currentUser.getFirstName() + " dołączył do naszego zespołu.",
                currentUser.getManager().getId()
        );

        viewManager.switchLayout(Layout.PANEL, ViewComponent.TIMETABLE);
    }

}