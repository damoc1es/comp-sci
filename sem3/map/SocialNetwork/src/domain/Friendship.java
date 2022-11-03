package domain;

import java.util.Objects;
import java.util.UUID;

public class Friendship extends Entity {
    private final UUID userId1, userId2;

    public Friendship(UUID id1, UUID id2) {
        userId1 = id1;
        userId2 = id2;
        super.setId(UUID.randomUUID());
    }

    public UUID getUserId1() {
        return userId1;
    }

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
