package ur.inf.lab2.pz.servicemanmanagement.services;

/**
 * Interfejs używany do obsługi funkci wysyłania wiadomości email nowo zarejestrowanych użytkowników
 */
public interface EmailSender {

    void sendEmail(String to, String subject, String content);
}
