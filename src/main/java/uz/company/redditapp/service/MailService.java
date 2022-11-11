package uz.company.redditapp.service;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.MailAuthenticationException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import uz.company.redditapp.dto.NotificationEmailDTO;

@Service
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PUBLIC)
@Slf4j
public class MailService {

    MailContentBuilder mailContentBuilder;
    JavaMailSender javaMailSender;


    public void sendEmail(NotificationEmailDTO notificationEmailDTO) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom("Reddit community");
            message.setTo(notificationEmailDTO.getRecipient());
            message.setSubject(notificationEmailDTO.getSubject());
            message.setText(mailContentBuilder.build(notificationEmailDTO.getBody()));
            javaMailSender.send(message);
        } catch (MailAuthenticationException e) {
            log.error(e.getMessage(), e);
            throw new uz.company.redditapp.errors.MailAuthenticationException(e.getMessage(), "mail can not be sent");
        }
    }


}
