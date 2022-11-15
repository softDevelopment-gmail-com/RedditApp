package uz.company.redditapp.errors;

public class IntegrationException extends RuntimeException {

    ErrorDto error;

    public IntegrationException() {
    }

    public IntegrationException(String message) {
        super(message);
    }

    public IntegrationException(ErrorDto error) {
        this.error = error;
    }

    public IntegrationException(String message, ErrorDto error) {

    }

    public ErrorDto getError() {
        return error;
    }

    public void setError(ErrorDto error) {
        this.error = error;
    }
}
