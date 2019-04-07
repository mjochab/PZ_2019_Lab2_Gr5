package ur.inf.lab2.pz.servicemanmanagement.controller;

public interface EmailSender {

    void sendEmail(String to, String subject, String content);
}
