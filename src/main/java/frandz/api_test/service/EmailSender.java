package frandz.api_test.service;

public interface EmailSender {
    void sendEmail(String toEmail, String body);
}
