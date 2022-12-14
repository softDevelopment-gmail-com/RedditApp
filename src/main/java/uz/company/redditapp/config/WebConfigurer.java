package uz.company.redditapp.config;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.Properties;

@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@AllArgsConstructor
@Configuration
public class WebConfigurer {
    @Bean
    public JavaMailSender getJavaMailSender() {
        JavaMailSenderImpl mailSender=new JavaMailSenderImpl();
        mailSender.setHost("smtp.gmail.com");
        mailSender.setProtocol("smtp");
        mailSender.setUsername("shukhratDev1201@gmail.com");
        mailSender.setPassword("12012002shukhratjonrayimjonov");
        Properties properties=mailSender.getJavaMailProperties();
        properties.put("mail.transport.protocol","smtp");
        properties.put("mail.smtp.auth",true);
        properties.put("mail.smtp.starttls.enable","true");
        properties.put("mail.smtp.host","mail.gmail.com");
        properties.put("mail.smtp.port","587");
        properties.put("mail.debug","true");
        return mailSender;
    }

    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        return new CorsFilter(source);
    }
}
