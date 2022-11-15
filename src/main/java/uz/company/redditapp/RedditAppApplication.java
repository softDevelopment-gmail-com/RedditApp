package uz.company.redditapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.scheduling.annotation.EnableAsync;
import uz.company.redditapp.config.ApplicationProperties;

@SpringBootApplication
@EnableConfigurationProperties(ApplicationProperties.class)
@EnableAsync
public class RedditAppApplication {

    public static void main(String[] args) {
        SpringApplication.run(RedditAppApplication.class, args);
    }

}
