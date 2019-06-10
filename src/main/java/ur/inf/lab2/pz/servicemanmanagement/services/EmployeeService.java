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

/**
 * Klasa wykorzystywana podczas dodawania nowego pracownika
 */
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

    /**
     * Metoda obsługująca zdarzenie dodania nowego pracownika
     */
    public void addWorker(workerAddDTO dto, Text existingUserAlert) {

        existingUserAlert.setVisible(false);

        if (allUsersRepository.findAllByEmail(dto.getEmail()).isEmpty()) {

            User manager = SecurityContext.getLoggedUser();

            String firstPassword = UUID.randomUUID().toString().substring(0, 6);
            Serviceman serviceman = new Serviceman(dto.getEmail(), encryptionService.encode(firstPassword), dto.getGroupName(), manager);
            serviceman.setRole(roleRepository.findByRole(Roles.ROLE_SERVICEMAN.toString()));
            allUsersRepository.save(serviceman);
            emailSender.sendEmail(dto.getEmail(),"Account's First password"," <html><style>"+
               "h1 {font-size: 26px; color: #444444 !important; font-family: 'Lucida Grande', 'Lucida Sans', 'Lucida Sans Unicode', sans-serif; line-height: 1.5;}"+
               "h3 {font-size: 30px; color: #000000 !important; font-family: 'Lucida Grande', 'Lucida Sans', 'Lucida Sans Unicode', sans-serif;}"+
               "p {font-size: 16px; color: #ffffff; font-family: 'Lucida Grande', 'Lucida Sans', 'Lucida Sans Unicode', sans-serif; line-height: 1.5;}"+
               "table { border: 1px solid #e4e4e4;}"+
               "a {color: #062b67; text-decoration: none; font-weight: bold;}</style>"+
	      "<table width='100%' cellpadding='0' cellspacing='0' bgcolor='0095cc'>"+
             "<tr>"+
                "<td height='50' align='center'><p>Witaj w systemie SVC!</p></td>"+
             "</tr>"+
             "<tr bgcolor='ffffff'>"+
                "<td height='100' align='center'><h1>Twoje hasło do pierwszego logowania to: <h3><u>"+firstPassword+"</u></h3></h1></td>"+
             "</tr>"+
             "<tr>"+
                "<td height='50' align='center'></td>"+
             "</tr>"+
	      "</table>"+
        "</html>");
        } else existingUserAlert.setVisible(true);
    }
}
