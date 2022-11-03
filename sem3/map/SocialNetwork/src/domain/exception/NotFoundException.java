package domain.exception;

public class NotFoundException extends SNException {
    public NotFoundException(String errMessage) {
        super(errMessage);
    }
}
