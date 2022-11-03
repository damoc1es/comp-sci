package domain.validators;

import domain.User;
import domain.exception.ValidationException;

public class UserValidator implements Validator<User> {
    public void validate(User x) throws ValidationException {
        String handle = x.getHandle();

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
