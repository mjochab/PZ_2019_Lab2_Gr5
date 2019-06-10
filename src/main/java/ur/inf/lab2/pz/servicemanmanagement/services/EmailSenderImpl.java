package ur.inf.lab2.pz.servicemanmanagement.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import ur.inf.lab2.pz.servicemanmanagement.domain.User;
import ur.inf.lab2.pz.servicemanmanagement.repository.UserRepository;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.UUID;

/**
 * Klasa implementująca interfejs używany do obsługi funkci wysyłania wiadomości email nowo zarejestrowanych użytkowników
 */
@Service
public class EmailSenderImpl implements EmailSender {

    @Autowired
    private JavaMailSender javaMailSender;

    /**
     * Metoda służaca do obsługi zdarzenia wysłania wiadomości email
     * @param to parametr określający na jaki adres email ma zostać wysłana wiadomość
     * @param title parametr określający tytuł wiadomości
     * @param content parametr określajacy zawartość wiadomośći
     */
    @Override
    public void sendEmail(String to, String title, String content) {
        MimeMessage mail = javaMailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(mail, true);
            helper.setTo(to);
            helper.setFrom("manager.pz.26@gmail.com");
            helper.setSubject(title);
            helper.setText(content, true);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
        javaMailSender.send(mail);
    }

}
