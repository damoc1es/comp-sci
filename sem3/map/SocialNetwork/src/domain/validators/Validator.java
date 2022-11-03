package domain.validators;

import domain.exception.ValidationException;

public interface Validator<T> {
    void validate(T entity) throws ValidationException;
}
