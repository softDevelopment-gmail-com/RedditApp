package uz.company.redditapp.rabbitmq;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.amqp.core.MessagePropertiesBuilder;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import uz.company.redditapp.constants.MqConstants;
import uz.company.redditapp.dto.NotificationEmailDTO;

@Component
@Slf4j
public class RabbitMqEmailSendProducer implements MqConstants {

    private final RabbitTemplate rabbitTemplate;
    private final ObjectMapper objectMapper;

    @Autowired
    public RabbitMqEmailSendProducer(RabbitTemplate rabbitTemplate, ObjectMapper objectMapper) {
        this.rabbitTemplate = rabbitTemplate;
        this.objectMapper = objectMapper;
    }

    public void send(NotificationEmailDTO emailDTO) {
        String payloadString;

        try {
            payloadString = objectMapper.writeValueAsString(emailDTO);
        } catch (Exception e) {
            log.error(e.getMessage());
            return;
        }

        Message jsonMessage = MessageBuilder
                .withBody(payloadString.getBytes())
                .andProperties(
                        MessagePropertiesBuilder
                                .newInstance()
                                .setContentType(MediaType.APPLICATION_JSON_VALUE)
                                .setHeader(X_DELAY, DEFAULT_DELAY)
                                .setHeader(TYPE_ID, MAIL_SEND_PAYLOAD)
                                .build()
                )
                .build();
        rabbitTemplate.send(DELAY_EXCHANGE_NAME, MAIL_SEND_QUEUE, jsonMessage);
    }
}
