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
import ur.inf.lab2.pz.servicemanmanagement.view.Layout;
import ur.inf.lab2.pz.servicemanmanagement.view.ViewComponent;
import ur.inf.lab2.pz.servicemanmanagement.view.ViewManager;

import javax.swing.text.View;
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

    public void createUser(String firstName, String lastName,
                           String companyName, String email, String password,
                           String confirmPassword, String roleName, Text firstNameAlert, Text lastNameAlert,
                           Text companyNameAlert, Text privacyAlert, Text existingUserAlert) throws IOException {

        if (StringUtils.isEmptyOrWhitespaceOnly(firstName)) firstNameAlert.setVisible(true);
        if (StringUtils.isEmptyOrWhitespaceOnly(lastName)) lastNameAlert.setVisible(true);
        if (StringUtils.isEmptyOrWhitespaceOnly(companyName)) companyNameAlert.setVisible(true);


//        if (stringUtils.isEmptyOrWhitespaceOnly(email)) emailAlert.setVisible(true);
//        if (!email.contains("@") || email.length() < 3) {
//            emailAlert.setText("Email jest nieprawidłowy");
//            emailAlert.setVisible(true);}
        if (!password.equals(confirmPassword)) {privacyAlert.setText("Hasła nie zgadzaja sie");
            privacyAlert.setVisible(true);}
        if (password.length() < 6 || password.length() > 16)
        {privacyAlert.setText("Hasło musi zawierać conajmniej 6 i maksymalnie 16 znaków.");
            privacyAlert.setVisible(true);}

        if (!StringUtils.isEmptyOrWhitespaceOnly(firstName)
                && !StringUtils.isEmptyOrWhitespaceOnly(lastName)
                && !StringUtils.isEmptyOrWhitespaceOnly(companyName)
                && !StringUtils.isEmptyOrWhitespaceOnly(email)
                && password.equals(confirmPassword)
                && !StringUtils.isEmptyOrWhitespaceOnly(password)
                && email.contains("@")
                && email.length()>2
                && password.length()>5
                && password.length()<17) {
            Role role = Optional.of(roleRepository.findByRole(roleName)).orElseThrow(() -> new IOException());

            if (!userRepository.findAllByEmail(email).isEmpty()) existingUserAlert.setVisible(true);
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
                    viewManager.loadComponent(ViewComponent.LOGIN);

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

        viewManager.switchLayout(Layout.PANEL, ViewComponent.TIMETABLE);
    }

}