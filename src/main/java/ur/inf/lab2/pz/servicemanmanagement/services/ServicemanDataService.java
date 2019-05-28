package ur.inf.lab2.pz.servicemanmanagement.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ur.inf.lab2.pz.servicemanmanagement.domain.SecurityContext;
import ur.inf.lab2.pz.servicemanmanagement.domain.User;
import ur.inf.lab2.pz.servicemanmanagement.domain.dto.ServicemanDataDTO;
import ur.inf.lab2.pz.servicemanmanagement.repository.AllUsersRepository;
import ur.inf.lab2.pz.servicemanmanagement.repository.UserRepository;

@Service
public class ServicemanDataService {
    @Autowired
    private AllUsersRepository userRepository;

    @Autowired
    private EncryptionService encryptionService;

    public void submitServicemanData(ServicemanDataDTO data)  {
        User currentUser = SecurityContext.getLoggedUser();
        currentUser.setFirstName(data.getFirstName());
        currentUser.setLastName(data.getLastName());
        currentUser.setPassword(encryptionService.encode(data.getPassword()));
        currentUser.setEnabled(true);
        userRepository.save(currentUser);

        //  viewManager.switchLayout(Layout.PANEL, ViewComponent.TIMETABLE);
    }
}
