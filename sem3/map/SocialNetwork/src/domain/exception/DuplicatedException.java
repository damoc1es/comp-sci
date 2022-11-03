package domain.exception;

public class DuplicatedException extends SNException {

    public DuplicatedException(String errMessage) {
        super(errMessage);
    }
}
