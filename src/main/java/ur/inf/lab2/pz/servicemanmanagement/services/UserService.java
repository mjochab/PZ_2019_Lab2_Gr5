package ur.inf.lab2.pz.servicemanmanagement.services;

import javafx.scene.text.Text;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ur.inf.lab2.pz.servicemanmanagement.domain.Role;
import ur.inf.lab2.pz.servicemanmanagement.domain.SecurityContext;
import ur.inf.lab2.pz.servicemanmanagement.domain.User;
import ur.inf.lab2.pz.servicemanmanagement.domain.dto.ServicemanFirstLoginDTO;
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

    public void userLogin(String email, String password) throws IOException {

        User user = Optional.of(userRepository.findByEmail(email)).orElseThrow(() -> new IOException());
        if (passwordEncoder.matches(password, user.getPassword())) {
            SecurityContext.setLoggedUser(user);

            if (SecurityContext.getLoggedUser().getRole().getRole().equals(Roles.ROLE_MANAGER.toString()))
                viewManager.show(ViewType.DASHBOARD);
            else if (SecurityContext.getLoggedUser().getRole().getRole().equals(Roles.ROLE_SERVICEMAN.toString())) {
                if (!SecurityContext.getLoggedUser().isEnabled()) {
                    viewManager.show(ViewType.SERVICEMAN_FIRST_LOGIN);
                } else viewManager.show(ViewType.SERVICEMAN_TIMETABLE);
            } else throw new IOException();
        } else throw new IOException();

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
        if (!email.contains("@") && email.length() < 3) {emailAlert.setText("Email jest nieprawidłowy");
            emailAlert.setVisible(true);}
        if (!password.equals(confirmPassword)) {privacyAlert.setText("Hasła nie zgadzaja sie");
            privacyAlert.setVisible(true);}
        if (password.length() < 6 || password.length() > 16)
        {privacyAlert.setText("Hasło musi zawierać conajmniej 6 i maksymalnie 16 znaków.");
            privacyAlert.setVisible(true);}

        if (!stringUtils.isEmptyOrWhitespaceOnly(firstName)
                && !stringUtils.isEmptyOrWhitespaceOnly(lastName)
                && !stringUtils.isEmptyOrWhitespaceOnly(companyName)
                && !stringUtils.isEmptyOrWhitespaceOnly(email)
                && password.equals(confirmPassword)
                && !stringUtils.isEmptyOrWhitespaceOnly(password)
                && email.contains("@")
                && email.length()>2
                && password.length()>5
                && password.length()<17) {
            Role role = Optional.of(roleRepository.findByRole(roleName)).orElseThrow(() -> new IOException());

            if (userRepository.findByEmail(email) != null) existingUserAlert.setVisible(true);
            else{
                if (password.equals(confirmPassword)) {
                    User user = new User();
                    user.setFirstName(firstName);
                    user.setLastName(lastName);
                    user.setCompanyName(companyName);
                    user.setEmail(email);
                    user.setPassword(encryptionService.encode(password));
                    user.setRole(role);
                    userRepository.save(user);
                    viewManager.show(ViewType.LOGIN);

                }
            }}
    }

    public void changePersonalData(ServicemanFirstLoginDTO data) throws IOException {
        User currentUser = SecurityContext.getLoggedUser();
        currentUser.setFirstName(data.getFirstName());
        currentUser.setLastName(data.getLastName());
        currentUser.setPassword(encryptionService.encode(data.getPassword()));
        currentUser.setEnabled(true);
        userRepository.save(currentUser);

        viewManager.show(ViewType.SERVICEMAN_TIMETABLE);
    }

}