package domain.validators;

public class NoValidator<T> implements Validator<T> {
    /**
     * Validator that doesn't throw anything
     * @param entity entity to be validated
     */
    @Override
    public void validate(T entity) {}
}
