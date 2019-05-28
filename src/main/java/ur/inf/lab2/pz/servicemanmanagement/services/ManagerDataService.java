package ur.inf.lab2.pz.servicemanmanagement.services;

import javafx.scene.text.Text;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ur.inf.lab2.pz.servicemanmanagement.domain.Role;
import ur.inf.lab2.pz.servicemanmanagement.domain.SecurityContext;
import ur.inf.lab2.pz.servicemanmanagement.domain.User;
import ur.inf.lab2.pz.servicemanmanagement.domain.dto.ManagerDataDTO;
import ur.inf.lab2.pz.servicemanmanagement.domain.dto.ManagerRegisterDTO;
import ur.inf.lab2.pz.servicemanmanagement.domain.dto.ServicemanFirstLoginDTO;
import ur.inf.lab2.pz.servicemanmanagement.domain.enums.Roles;
import ur.inf.lab2.pz.servicemanmanagement.repository.AllUsersRepository;
import ur.inf.lab2.pz.servicemanmanagement.repository.RoleRepository;
import ur.inf.lab2.pz.servicemanmanagement.repository.UserRepository;
import ur.inf.lab2.pz.servicemanmanagement.view.Layout;
import ur.inf.lab2.pz.servicemanmanagement.view.ViewComponent;
import ur.inf.lab2.pz.servicemanmanagement.view.ViewManager;
import ur.inf.lab2.pz.servicemanmanagement.domain.User;

@Service
public class ManagerDataService {
    @Autowired
    private AllUsersRepository userRepository;

    @Autowired
    private EncryptionService encryptionService;

    public void submitManagerData(ManagerDataDTO data)  {
        User currentUser = SecurityContext.getLoggedUser();
        currentUser.setFirstName(data.getFirstName());
        currentUser.setLastName(data.getLastName());
        currentUser.setCompanyName(data.getCompanyName());
        currentUser.setPassword(encryptionService.encode(data.getPassword()));
        currentUser.setEnabled(true);
        userRepository.save(currentUser);

      //  viewManager.switchLayout(Layout.PANEL, ViewComponent.TIMETABLE);
    }
}
