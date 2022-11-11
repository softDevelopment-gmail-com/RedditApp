package uz.company.redditapp.service;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.company.redditapp.domain.User;
import uz.company.redditapp.dto.NotificationEmailDTO;
import uz.company.redditapp.dto.RegisterDTO;
import uz.company.redditapp.errors.EntityAlreadyExistException;
import uz.company.redditapp.repository.UserRepository;
import uz.company.redditapp.security.TokenProvider;

@Service
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@AllArgsConstructor
@Transactional
public class AuthService {

    PasswordEncoder passwordEncoder;
    UserRepository userRepository;
    TokenProvider tokenProvider;
    MailService mailService;

    public void signUp(RegisterDTO registerDto) {
        if (userRepository.existsByEmailAndDeletedFalse(registerDto.getEmail())) {
            throw new EntityAlreadyExistException("this username already registered.","email already exist.");
        }

        if (userRepository.existsByUsernameAndDeletedFalse(registerDto.getUsername())) {
            throw new EntityAlreadyExistException("user with this username already exist", "username taken already");
        }
        User user = new User();
        user.setUsername(registerDto.getUsername());
        user.setEmail(registerDto.getEmail());
        user.setPassword(passwordEncoder.encode(registerDto.getPassword()));
        userRepository.save(user);

        String token = tokenProvider.generateVerificationToken(user);
        sendEmail(user,token);
    }




    public void sendEmail(User user, String token) {
        NotificationEmailDTO notificationEmailDTO = NotificationEmailDTO
                .builder()
                .subject("Please, Activate your account !!!")
                .recipient(user.getEmail())
                .body("Thank you for signing up to Reddit, please click on the below url to activate your account: " +
                        "http://localhost:8080/api/v1/auth/verify/" + token)
                .build();
        mailService.sendEmail(notificationEmailDTO);
    }
}
