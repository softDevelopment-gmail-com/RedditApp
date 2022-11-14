package uz.company.redditapp.service;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import uz.company.redditapp.dto.NotificationEmailDTO;
import uz.company.redditapp.errors.MailAuthenticationException;

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
            message.setFrom("RedditCommunity");
            message.setTo(notificationEmailDTO.getRecipient());
            message.setSubject(notificationEmailDTO.getSubject());
            message.setText(mailContentBuilder.build(notificationEmailDTO.getBody()));
            javaMailSender.send(message);
        } catch (Exception e) {
            throw new MailAuthenticationException(e.getMessage(), "mail can not be sent");
        }
    }
}
