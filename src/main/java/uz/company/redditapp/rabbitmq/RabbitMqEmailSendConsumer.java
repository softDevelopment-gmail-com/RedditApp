package uz.company.redditapp.rabbitmq;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.AmqpRejectAndDontRequeueException;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import uz.company.redditapp.constants.MqConstants;
import uz.company.redditapp.dto.NotificationEmailDTO;
import uz.company.redditapp.service.MailService;

@Component
@Slf4j
public class RabbitMqEmailSendConsumer implements MqConstants {

    private final MailService mailService;

    @Autowired
    public RabbitMqEmailSendConsumer(MailService mailService) {
        this.mailService = mailService;
    }

    @RabbitListener(
            bindings = @QueueBinding(
                    value = @Queue(durable = "false", value = MAIL_SEND_QUEUE),
                    exchange = @Exchange(durable = "false", value = DELAY_EXCHANGE_NAME, delayed = "true"),
                    key = MAIL_SEND_QUEUE
            ),
            containerFactory = "rabbitListenerContainerFactoryMax"
    )
    public void send(NotificationEmailDTO emailDTO) {
        try {
            mailService.sendEmail(emailDTO);
        } catch (Exception e) {
            log.error("Send email rabbit mq error: ", e);
            throw new AmqpRejectAndDontRequeueException("Send email rabbit mq error: " + e.getMessage());
        }
    }
}
