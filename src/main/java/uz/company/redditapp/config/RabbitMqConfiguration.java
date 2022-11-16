package uz.company.redditapp.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.support.converter.DefaultClassMapper;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Primary;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import uz.company.redditapp.constants.MqConstants;
import uz.company.redditapp.dto.NotificationEmailDTO;

import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableRabbit
@Lazy
public class RabbitMqConfiguration implements MqConstants {

    @Autowired
    private ObjectMapper objectMapper;

    @Primary
    @Bean
    @Lazy
    public SimpleRabbitListenerContainerFactory rabbitListenerContainerFactory(ConnectionFactory connectionFactory) {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory);
        factory.setMessageConverter(producerJackson2MessageConverter());
        factory.setConcurrentConsumers(4);
        factory.setMaxConcurrentConsumers(8);
        return factory;
    }

    @Bean
    @Lazy
    public SimpleRabbitListenerContainerFactory rabbitListenerContainerFactoryMin(ConnectionFactory connectionFactory) {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory);
        factory.setMessageConverter(producerJackson2MessageConverter());
        factory.setConcurrentConsumers(1);
        factory.setMaxConcurrentConsumers(1);
        return factory;
    }

    @Bean
    @Lazy
    public SimpleRabbitListenerContainerFactory rabbitListenerContainerFactoryMax(ConnectionFactory connectionFactory) {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory);
        factory.setMessageConverter(producerJackson2MessageConverter());
        factory.setConcurrentConsumers(10);
        factory.setMaxConcurrentConsumers(20);
        return factory;
    }

    @Bean
    @Lazy
    public Jackson2JsonMessageConverter producerJackson2MessageConverter() {
        Jackson2JsonMessageConverter jackson2JsonMessageConverter = new Jackson2JsonMessageConverter();
        jackson2JsonMessageConverter.setClassMapper(classMapper());
        return jackson2JsonMessageConverter;
    }

    @Bean
    @Lazy
    public MappingJackson2MessageConverter consumerJackson2MessageConverter() {
        MappingJackson2MessageConverter messageConverter = new MappingJackson2MessageConverter();
        messageConverter.setObjectMapper(objectMapper);
        return messageConverter;
    }

    @Bean
    @Lazy
    public DefaultClassMapper classMapper() {
        DefaultClassMapper classMapper = new DefaultClassMapper();
        Map<String, Class<?>> idClassMapping = new HashMap<>();
        idClassMapping.put(MAIL_SEND_PAYLOAD, NotificationEmailDTO.class);
        classMapper.setIdClassMapping(idClassMapping);
        classMapper.setTrustedPackages("*");
        return classMapper;
    }

}
