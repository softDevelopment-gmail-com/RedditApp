package uz.company.redditapp.service;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.company.redditapp.domain.Authority;
import uz.company.redditapp.domain.User;
import uz.company.redditapp.domain.VerificationToken;
import uz.company.redditapp.dto.LoginDTO;
import uz.company.redditapp.dto.NotificationEmailDTO;
import uz.company.redditapp.dto.RegisterDTO;
import uz.company.redditapp.dto.TokenDTO;
import uz.company.redditapp.enums.Role;
import uz.company.redditapp.errors.EntityAlreadyExistException;
import uz.company.redditapp.errors.NotFoundException;
import uz.company.redditapp.rabbitmq.RabbitMqEmailSendConsumer;
import uz.company.redditapp.repository.AuthorityRepository;
import uz.company.redditapp.repository.UserRepository;
import uz.company.redditapp.repository.VerificationTokenRepository;
import uz.company.redditapp.security.TokenProvider;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@AllArgsConstructor
//@Transactional
public class AuthService {

    PasswordEncoder passwordEncoder;
    RabbitMqEmailSendConsumer rabbitMqEmailSendConsumer;
    AuthorityRepository authorityRepository;
    UserRepository userRepository;
    AuthenticationManagerBuilder authenticationManagerBuilder;
    TokenProvider tokenProvider;
    VerificationTokenRepository verificationTokenRepository;
    MailService mailService;

    public void signUp(RegisterDTO registerDto) {
        if (userRepository.existsByEmailAndDeletedFalse(registerDto.getEmail())) {
            throw new EntityAlreadyExistException("this username already registered.", "email already exist.");
        }

        if (userRepository.existsByUsernameAndDeletedFalse(registerDto.getUsername())) {
            throw new EntityAlreadyExistException("user with this username already exist", "username taken already");
        }
        User user = new User();
        user.setUsername(registerDto.getUsername());
        user.setEmail(registerDto.getEmail());
        Set<Authority> authorities = new HashSet<>();
        authorityRepository.findById(Role.ROLE_ADMIN.name()).ifPresent(authorities::add);
        user.setAuthorities(authorities);
        user.setPassword(passwordEncoder.encode(registerDto.getPassword()));
        userRepository.save(user);
        String token = tokenProvider.generateVerificationToken(user);
        sendEmail(user, token);
    }

    @Async
    public void sendEmail(User user, String token) {
        NotificationEmailDTO notificationEmailDTO = NotificationEmailDTO
                .builder()
                .subject("Please, Activate your account !!!")
                .recipient(user.getEmail())
                .body("Thank you for signing up to Reddit, please click on the below url to activate your account: " +
                        "http://localhost:8081/api/v1/auth/verify/" + token)
                .build();
        rabbitMqEmailSendConsumer.send(notificationEmailDTO);
    }

    @Transactional
    public void verify(String token) {
        Optional<VerificationToken> verificationToken = Optional.ofNullable(verificationTokenRepository.findByTokenAndDeletedFalse(token).orElseThrow(
                () -> new NotFoundException("invalid token", "token not found")
        ));

        String username = verificationToken.get().getUser().getUsername();
        User user = userRepository.findOneWithAuthoritiesByUsernameAndDeletedFalse(username).orElseThrow(() ->
                new NotFoundException("username not found", "user with this username not found")
        );
        user.setEnabled(true);
        userRepository.save(user);
    }

    public TokenDTO login(LoginDTO loginDTO) {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                loginDTO.getUsername(),
                loginDTO.getPassword()
        );
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = tokenProvider.createToken(authentication);
        return new TokenDTO(token);
    }
}
