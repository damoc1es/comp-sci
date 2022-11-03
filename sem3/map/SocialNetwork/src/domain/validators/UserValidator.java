package domain.validators;

import domain.User;
import domain.exception.ValidationException;

public class UserValidator implements Validator<User> {
    /**
     * Validates the user
     * @param entity user to be validated
     * @throws ValidationException handle contains spaces/anything other than letters, numbers and periods or handle's
     * length is less than 3 or more than 30 characters
     */
    @Override
    public void validate(User entity) throws ValidationException {
        String handle = entity.getHandle();

        if(handle.contains(" ")) {
            throw new ValidationException("Handle can't contain spaces.");
        }

        if(!handle.matches("[A-Za-z0-9.]*")) {
            throw new ValidationException("Handle can only contain letters, numbers, and periods.");
        }

        if(handle.length() < 3) {
            throw new ValidationException("Handle must have at least 3 characters.");
        }

        if(handle.length() > 30) {
            throw new ValidationException("Handle must not exceed 30 characters.");
        }
    }
}
