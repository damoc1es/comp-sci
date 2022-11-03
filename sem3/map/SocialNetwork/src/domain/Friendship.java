package domain;

import java.util.Objects;
import java.util.UUID;

public class Friendship extends Entity {
    private final UUID userId1, userId2;

    /**
     * Friendship constructor
     * @param id1 first user's id
     * @param id2 second user's id
     */
    public Friendship(UUID id1, UUID id2) {
        userId1 = id1;
        userId2 = id2;
        super.setId(UUID.randomUUID());
    }

    /**
     * userId1 getter
     * @return User1's ID
     */
    public UUID getUserId1() {
        return userId1;
    }

    /**
     * userId2 getter
     * @return User2's ID
     */
    public UUID getUserId2() {
        return userId2;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Friendship that = (Friendship) o;
        return userId1.equals(that.userId1) && userId2.equals(that.userId2);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId1, userId2);
    }
}
