package uz.company.redditapp.errors;

import lombok.Getter;
import lombok.Setter;
import org.zalando.problem.AbstractThrowableProblem;
import org.zalando.problem.Status;

@Getter
@Setter
public class MailAuthenticationException extends AbstractThrowableProblem {

    String developerMessage;

    String userMessage;

    public MailAuthenticationException(String developerMessage, String userMessage) {
        super(null, userMessage,Status.UNAUTHORIZED,developerMessage);
        this.developerMessage = developerMessage;
        this.userMessage = userMessage;
    }
}
