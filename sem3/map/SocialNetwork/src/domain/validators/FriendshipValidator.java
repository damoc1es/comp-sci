package domain.validators;

import domain.Friendship;
import domain.exception.ValidationException;

public class FriendshipValidator implements Validator<Friendship> {
    /**
     * Validator that doesn't throw anything
     * @param entity entity to be validated
     */
    @Override
    public void validate(Friendship entity) throws ValidationException {
        if(entity.getUserId2() == entity.getUserId1())
            throw new ValidationException("Users can't be friends with themself.");
    }
}
