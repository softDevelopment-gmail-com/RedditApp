package uz.company.redditapp.errors;

import org.zalando.problem.AbstractThrowableProblem;
import org.zalando.problem.Status;

public class EntityAlreadyExistException extends AbstractThrowableProblem {

    String userMessage;
    String developerMessage;

    public EntityAlreadyExistException(String userMessage, String developerMessage) {
        super(null,userMessage, Status.FOUND,developerMessage);
        this.userMessage = userMessage;
        this.developerMessage = developerMessage;
    }
}
