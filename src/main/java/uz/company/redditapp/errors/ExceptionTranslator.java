package uz.company.redditapp.errors;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.NativeWebRequest;
import org.zalando.problem.Problem;
import org.zalando.problem.spring.web.advice.ProblemHandling;
import org.zalando.problem.spring.web.advice.security.SecurityAdviceTrait;

import java.util.Optional;

@RestControllerAdvice
public class ExceptionTranslator implements ProblemHandling, SecurityAdviceTrait {


    @ExceptionHandler(MailAuthenticationException.class)
    public ResponseEntity<Problem> handleMailAuthenticationException(MailAuthenticationException e, NativeWebRequest request) {
        Problem problem = Problem
                .builder()
                .withStatus(e.getStatus())
                .withTitle(e.getUserMessage())
                .withDetail(Optional.ofNullable(e.getDeveloperMessage()).orElse(e.getUserMessage()))
                .build();
        return create(e, problem, request);
    }

    @ExceptionHandler(EntityAlreadyExistException.class)
    public ResponseEntity<Problem> handleUserAlreadyExistException(MailAuthenticationException e, NativeWebRequest request) {
        Problem problem = Problem
                .builder()
                .withStatus(e.getStatus())
                .withTitle(e.getUserMessage())
                .withDetail(Optional.ofNullable(e.getDeveloperMessage()).orElse(e.getUserMessage()))
                .build();
        return create(e, problem, request);
    }
}
