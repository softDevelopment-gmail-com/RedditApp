package uz.company.redditapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import uz.company.redditapp.config.ApplicationProperties;

@SpringBootApplication
@EnableConfigurationProperties(ApplicationProperties.class)
public class RedditAppApplication {

    public static void main(String[] args) {
        SpringApplication.run(RedditAppApplication.class, args);
    }

}
