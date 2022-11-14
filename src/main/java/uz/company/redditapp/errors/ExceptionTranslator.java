package uz.company.redditapp.errors;

import org.apache.commons.lang3.StringUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.NativeWebRequest;
import org.zalando.problem.DefaultProblem;
import org.zalando.problem.Problem;
import org.zalando.problem.ProblemBuilder;
import org.zalando.problem.spring.web.advice.ProblemHandling;
import org.zalando.problem.spring.web.advice.security.SecurityAdviceTrait;
import org.zalando.problem.violations.ConstraintViolationProblem;

import javax.annotation.Nullable;
import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

@RestControllerAdvice
public class ExceptionTranslator implements ProblemHandling, SecurityAdviceTrait {

    private static final String MESSAGE_KEY = "message";
    private static final String PATH_KEY = "path";
    private static final String VIOLATIONS_KEY = "violations";

    @Override
    public ResponseEntity<Problem> process(@Nullable ResponseEntity<Problem> entity, NativeWebRequest request) {
        if (entity == null) {
            return null;
        }
        Problem problem = entity.getBody();

        if (!(problem instanceof ConstraintViolationProblem || problem instanceof DefaultProblem)) {
            return entity;
        }

        HttpServletRequest nativeRequest = request.getNativeRequest(HttpServletRequest.class);
        String requestUri = nativeRequest != null ? nativeRequest.getRequestURI() : StringUtils.EMPTY;
        ProblemBuilder builder = Problem
                .builder()
                .withStatus(problem.getStatus())
                .withTitle(problem.getTitle())
                .with(PATH_KEY, requestUri);

        if (problem instanceof ConstraintViolationProblem) {
            builder
                    .with(VIOLATIONS_KEY, ((ConstraintViolationProblem) problem).getViolations());
        } else {
            builder.withCause(((DefaultProblem) problem).getCause()).withDetail(problem.getDetail()).withInstance(problem.getInstance());
            problem.getParameters().forEach(builder::with);
            if (!problem.getParameters().containsKey(MESSAGE_KEY) && problem.getStatus() != null) {
                builder.with(MESSAGE_KEY, "error.http." + problem.getStatus().getStatusCode());
            }
        }
        return new ResponseEntity<>(builder.build(), entity.getHeaders(), entity.getStatusCode());
    }

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
    public ResponseEntity<Problem> handleUserAlreadyExistException(EntityAlreadyExistException e, NativeWebRequest request) {
        Problem problem = Problem
                .builder()
                .withStatus(e.getStatus())
                .withTitle(e.getUserMessage())
                .withDetail(Optional.ofNullable(e.getDeveloperMessage()).orElse(e.getUserMessage()))
                .build();
        return create(e, problem, request);
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<Problem> handleNotFoundException(NotFoundException e, NativeWebRequest request) {
        Problem problem = Problem
                .builder()
                .withStatus(e.getStatus())
                .withTitle(e.getUserMessage())
                .withDetail(Optional.ofNullable(e.getDeveloperMessage()).orElse(e.getUserMessage()))
                .build();
        return create(e, problem, request);
    }
}
