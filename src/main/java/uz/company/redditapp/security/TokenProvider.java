package uz.company.redditapp.security;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import uz.company.redditapp.domain.User;
import uz.company.redditapp.domain.VerificationToken;
import uz.company.redditapp.repository.VerificationTokenRepository;

import java.time.LocalDateTime;
import java.util.UUID;

@Component
@AllArgsConstructor
public class TokenProvider {

    private final long verificationTokenExpiryTimeInDays = 2;

    private final VerificationTokenRepository verificationTokenRepository;

    public String generateVerificationToken(User user) {
        String token = UUID.randomUUID().toString();

        VerificationToken verificationToken = new VerificationToken();
        verificationToken.setToken(token);
        verificationToken.setUser(user);
        verificationToken.setExpiryDate(LocalDateTime.now().plusDays(verificationTokenExpiryTimeInDays));
        verificationTokenRepository.save(verificationToken);
        return token;
    }
}
