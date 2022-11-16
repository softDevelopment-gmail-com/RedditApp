package uz.company.redditapp.errors;

import lombok.Getter;
import lombok.Setter;
import org.zalando.problem.AbstractThrowableProblem;
import org.zalando.problem.Status;

@Getter
@Setter
public class EntityAlreadyExistException extends AbstractThrowableProblem {

    String userMessage;
    String developerMessage;

    public EntityAlreadyExistException(String userMessage, String developerMessage) {
        super(null, userMessage, Status.CONFLICT, developerMessage);
        this.userMessage = userMessage;
        this.developerMessage = developerMessage;
    }
}
