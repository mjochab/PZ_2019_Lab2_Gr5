package ur.inf.lab2.pz.servicemanmanagement.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ur.inf.lab2.pz.servicemanmanagement.domain.Role;
import ur.inf.lab2.pz.servicemanmanagement.domain.SecurityContext;
import ur.inf.lab2.pz.servicemanmanagement.domain.User;
import ur.inf.lab2.pz.servicemanmanagement.domain.enums.Roles;
import ur.inf.lab2.pz.servicemanmanagement.repository.RoleRepository;
import ur.inf.lab2.pz.servicemanmanagement.repository.UserRepository;
import ur.inf.lab2.pz.servicemanmanagement.view.Layout;
import ur.inf.lab2.pz.servicemanmanagement.view.ViewComponent;
import ur.inf.lab2.pz.servicemanmanagement.view.ViewManager;

import java.io.IOException;
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

    public void userLogin(String email, String password) throws IOException{

        User user = Optional.of(userRepository.findByEmail(email)).orElseThrow(() -> new IOException());
        if (passwordEncoder.matches(password,user.getPassword())
                && user.getRole().getRole().equals(Roles.ROLE_MANAGER.toString()))
        {
            SecurityContext.setLoggedUser(user);

            if (SecurityContext.getLoggedUser().getRole().getRole().equals(Roles.ROLE_MANAGER.toString()))
                viewManager.switchLayout(Layout.PANEL, ViewComponent.DASHBOARD);
            else if (SecurityContext.getLoggedUser().getRole().getRole().equals(Roles.ROLE_SERVICEMAN.toString())) {
                if (SecurityContext.getLoggedUser().getFirstName()==null)
                    viewManager.loadComponent(ViewComponent.SERVICEMAN_REGISTER);

                viewManager.switchLayout(Layout.PANEL, ViewComponent.TIMETABLE);
            } else throw new IOException();
            }
        else throw new IOException();

    }

    public void createUser(String firstName, String lastName,
                           String companyName, String email, String password,
                           String confirmPassword, String roleName) throws IOException {

        Role role = Optional.of(roleRepository.findByRole(roleName)).orElseThrow(() -> new IOException());
        if (userRepository.findByEmail(email)!=null) throw new IOException();
        if (password.equals(confirmPassword)) {
            User user = new User();
            user.setFirstName(firstName);
            user.setLastName(lastName);
            user.setCompanyName(companyName);
            user.setEmail(email);
            user.setPassword(encryptionService.encode(password));
            user.setRole(role);
            userRepository.save(user);
        }
        viewManager.loadComponent(ViewComponent.LOGIN);
    }

}
