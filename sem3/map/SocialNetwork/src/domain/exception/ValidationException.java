package domain.exception;

public class ValidationException extends SNException {

    public ValidationException(String errMessage) {
        super(errMessage);
    }
}
