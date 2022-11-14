package uz.company.redditapp.errors;

import lombok.Getter;
import lombok.Setter;
import org.zalando.problem.AbstractThrowableProblem;
import org.zalando.problem.Status;

@Getter
@Setter
public class NotFoundException extends AbstractThrowableProblem {

    String userMessage;

    String developerMessage;

    public NotFoundException(String userMessage, String developerMessage) {
        super(null, userMessage, Status.NOT_FOUND, developerMessage);
        this.userMessage = userMessage;
        this.developerMessage = developerMessage;
    }
}
