package uz.company.redditapp.controller;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.company.redditapp.constants.ApiConstants;
import uz.company.redditapp.dto.LoginDTO;
import uz.company.redditapp.dto.RegisterDTO;
import uz.company.redditapp.dto.TokenDTO;
import uz.company.redditapp.service.AuthService;

import java.util.Collections;

@RestController
@RequestMapping(ApiConstants.auth)
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@AllArgsConstructor(access = AccessLevel.PUBLIC)
public class AuthController {

    AuthService authService;

    @PostMapping(ApiConstants.signUp)
    public ResponseEntity<String> signUp(@RequestBody RegisterDTO registerDto) {
        authService.signUp(registerDto);
        return ResponseEntity.ok().build();
    }

    @GetMapping(ApiConstants.verify + "/{token}")
    public ResponseEntity<Void> verify(@PathVariable String token) {
        authService.verify(token);
        return ResponseEntity.ok().build();
    }

    @PostMapping(ApiConstants.login)
    public ResponseEntity<TokenDTO> login(@RequestBody LoginDTO loginDTO) {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.setContentType(MediaType.APPLICATION_JSON);
        TokenDTO tokenDTO = authService.login(loginDTO);
        headers.setBearerAuth(tokenDTO.getAccessToken());
        return new ResponseEntity<>(tokenDTO, headers, HttpStatus.OK);
    }


}
