package uz.company.redditapp.controller;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uz.company.redditapp.constants.ApiConstants;
import uz.company.redditapp.dto.RegisterDTO;
import uz.company.redditapp.service.AuthService;

@RestController
@RequestMapping(ApiConstants.auth)
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@AllArgsConstructor(access = AccessLevel.PUBLIC)
public class AuthController {

    AuthService authService;

    @PostMapping(ApiConstants.signUp)
    public ResponseEntity<String> signUp(@RequestBody RegisterDTO registerDto) {
        authService.signUp(registerDto);
        return ResponseEntity.ok().body("Success");
    }


}
