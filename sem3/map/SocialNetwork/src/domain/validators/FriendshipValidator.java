package domain.validators;

import domain.Friendship;
import domain.exception.ValidationException;

public class FriendshipValidator implements Validator<Friendship> {
    /**
     * Validates the Friendship
     * @param entity friendship to be validated
     * @throws ValidationException if the users are the same
     */
    @Override
    public void validate(Friendship entity) throws ValidationException {
        if(entity.getUserId2() == entity.getUserId1())
            throw new ValidationException("Users can't be friends with themself.");
    }
}
