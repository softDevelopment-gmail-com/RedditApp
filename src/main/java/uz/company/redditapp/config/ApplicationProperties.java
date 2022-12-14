package uz.company.redditapp.config;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "application", ignoreUnknownFields = false)
@Getter
@Setter
public class ApplicationProperties {

    private final MailConfig mailConfig = new MailConfig();
    private final SecurityConfig securityConfig = new SecurityConfig();

    @FieldDefaults(level = AccessLevel.PRIVATE)
    @Getter
    @Setter
    public static class Credential {

        String username;
        String password;
    }

    @FieldDefaults(level = AccessLevel.PRIVATE)
    @Getter
    @Setter
    public static class MailConfig extends Credential {

        String protocol;
        String host;
    }

    @FieldDefaults(level = AccessLevel.PRIVATE)
    @Getter
    @Setter
    public static class SecurityConfig {
        String base64Secret;
        Long tokenValidityInMillis;
    }
}
