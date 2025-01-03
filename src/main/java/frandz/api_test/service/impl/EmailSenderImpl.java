package frandz.api_test.service.impl;

import frandz.api_test.service.EmailSender;
import jakarta.mail.internet.MimeMessage;
import lombok.AllArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class EmailSenderImpl implements EmailSender {

    private final JavaMailSender javaMailSender;

    @Override
    public void sendEmail(String toEmail, String body) {
        try {
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper helper =
                    new MimeMessageHelper(mimeMessage, "utf-8");
            helper.setText(body, true);
            helper.setTo(toEmail);
            helper.setSubject("Confirm your email");
            helper.setFrom("dfrandz8@gmail.com");
            javaMailSender.send(mimeMessage);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
