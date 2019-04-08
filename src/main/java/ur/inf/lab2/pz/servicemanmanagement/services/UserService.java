package ur.inf.lab2.pz.servicemanmanagement.services;

import javafx.scene.text.Text;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ur.inf.lab2.pz.servicemanmanagement.controller.ManagerRegisterController;
import ur.inf.lab2.pz.servicemanmanagement.domain.Role;
import ur.inf.lab2.pz.servicemanmanagement.domain.SecurityContext;
import ur.inf.lab2.pz.servicemanmanagement.domain.User;
import ur.inf.lab2.pz.servicemanmanagement.domain.enums.Roles;
import ur.inf.lab2.pz.servicemanmanagement.repository.RoleRepository;
import ur.inf.lab2.pz.servicemanmanagement.repository.UserRepository;
import ur.inf.lab2.pz.servicemanmanagement.utils.StringUtils;
import ur.inf.lab2.pz.servicemanmanagement.view.ViewManager;
import ur.inf.lab2.pz.servicemanmanagement.view.ViewType;

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

    @Autowired
    private StringUtils stringUtils;

    @Autowired
    private ManagerRegisterController managerRegisterController;

    public void userLogin(String email, String password, Text alertLabel) throws NullPointerException, IOException {

        try {
            User user = Optional.of(userRepository.findByEmail(email)).orElseThrow(() -> new NullPointerException());
            if (passwordEncoder.matches(password, user.getPassword())
                    && user.getRole().getRole().equals(Roles.ROLE_MANAGER.toString())) {
                SecurityContext.setLoggedUser(user);

                if (SecurityContext.getLoggedUser().getRole().getRole().equals(Roles.ROLE_MANAGER.toString()))
                    viewManager.show(ViewType.DASHBOARD);
                else if (SecurityContext.getLoggedUser().getRole().getRole().equals(Roles.ROLE_SERVICEMAN.toString())) {
                    if (SecurityContext.getLoggedUser().getFirstName() == null)
                        viewManager.show(ViewType.SERVICEMAN_REGISTER);
                    viewManager.show(ViewType.SERVICEMAN_TIMETABLE);
                } else throw new NullPointerException();
            } else throw new NullPointerException();
        } catch (NullPointerException e) {
            alertLabel.setVisible(true);
        }

    }

    public void createUser(String firstName, String lastName,
                           String companyName, String email, String password,
                           String confirmPassword, String roleName, Text firstNameAlert, Text lastNameAlert,
                           Text emailAlert, Text companyNameAlert, Text privacyAlert, Text existingUserAlert) throws IOException {

        firstNameAlert.setVisible(false);
        lastNameAlert.setVisible(false);
        companyNameAlert.setVisible(false);
        emailAlert.setVisible(false);
        privacyAlert.setVisible(false);
        existingUserAlert.setVisible(false);

        if (stringUtils.isEmptyOrWhitespaceOnly(firstName)) firstNameAlert.setVisible(true);
        if (stringUtils.isEmptyOrWhitespaceOnly(lastName)) lastNameAlert.setVisible(true);
        if (stringUtils.isEmptyOrWhitespaceOnly(companyName)) companyNameAlert.setVisible(true);
        if (stringUtils.isEmptyOrWhitespaceOnly(email)) emailAlert.setVisible(true);
        if (!password.equals(confirmPassword)) privacyAlert.setText("Hasla nie zgadzaja sie");
        privacyAlert.setVisible(true);

        if (!stringUtils.isEmptyOrWhitespaceOnly(firstName)
                && !stringUtils.isEmptyOrWhitespaceOnly(lastName) && !stringUtils.isEmptyOrWhitespaceOnly(companyName)
                && !stringUtils.isEmptyOrWhitespaceOnly(email) && password.equals(confirmPassword)) {
            Role role = Optional.of(roleRepository.findByRole(roleName)).orElseThrow(() -> new IOException());
            if (userRepository.findByEmail(email) != null) throw new IOException();
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
            viewManager.show(ViewType.LOGIN);
        }
    }

}
