package ur.inf.lab2.pz.servicemanmanagement.services;
import javafx.scene.text.Text;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ur.inf.lab2.pz.servicemanmanagement.domain.Role;
import ur.inf.lab2.pz.servicemanmanagement.domain.SecurityContext;
import ur.inf.lab2.pz.servicemanmanagement.domain.User;
import ur.inf.lab2.pz.servicemanmanagement.domain.dto.ManagerRegisterDTO;
import ur.inf.lab2.pz.servicemanmanagement.domain.dto.ServicemanFirstLoginDTO;
import ur.inf.lab2.pz.servicemanmanagement.domain.enums.Roles;
import ur.inf.lab2.pz.servicemanmanagement.repository.RoleRepository;
import ur.inf.lab2.pz.servicemanmanagement.repository.UserRepository;
import ur.inf.lab2.pz.servicemanmanagement.view.Layout;
import ur.inf.lab2.pz.servicemanmanagement.view.ViewComponent;
import ur.inf.lab2.pz.servicemanmanagement.view.ViewManager;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private EncryptionService encryptionService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private ViewManager viewManager;

    public void userLogin(String email, String password) throws IOException {

        List<User> allByEmail = userRepository.findAllByEmail(email);

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
                } else viewManager.switchLayout(Layout.PANEL, ViewComponent.TIMETABLE);
            } else throw new IOException();
        } else throw new IOException();

    }

    public void createUser(ManagerRegisterDTO dto, Text existingUserAlert) throws IOException {

            Role role = Optional.of(roleRepository.findByRole(dto.getRole())).orElseThrow(() -> new IOException());

            existingUserAlert.setVisible(false);
            if(userRepository.findAllByEmail(dto.getEmail()).isEmpty()){
                    User user = new User();
                    user.setFirstName(dto.getFirstName());
                    user.setLastName(dto.getLastName());
                    user.setCompanyName(dto.getCompanyName());
                    user.setEmail(dto.getEmail());
                    user.setPassword(encryptionService.encode(dto.getPassword()));
                    user.setRole(role);
                    userRepository.save(user);
                    viewManager.loadComponent(ViewComponent.LOGIN);
            } else existingUserAlert.setVisible(true);
    }


    public void changePersonalData(ServicemanFirstLoginDTO data) throws IOException {
        User currentUser = SecurityContext.getLoggedUser();
        currentUser.setFirstName(data.getFirstName());
        currentUser.setLastName(data.getLastName());
        currentUser.setPassword(encryptionService.encode(data.getPassword()));
        currentUser.setEnabled(true);
        userRepository.save(currentUser);

        viewManager.switchLayout(Layout.PANEL, ViewComponent.TIMETABLE);
    }

}