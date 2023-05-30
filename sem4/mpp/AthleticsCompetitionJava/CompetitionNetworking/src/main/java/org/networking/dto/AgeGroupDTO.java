package org.networking.dto;

import java.io.Serializable;
import java.util.UUID;

public class AgeGroupDTO implements Serializable {
    private final UUID id;
    private final int minAge;
    private final int maxAge;

    public AgeGroupDTO(UUID id, int minAge, int maxAge) {
        this.id = id;
        this.minAge = minAge;
        this.maxAge = maxAge;
    }

    public UUID getId() {
        return id;
    }

    public int getMinAge() {
        return minAge;
    }

    public int getMaxAge() {
        return maxAge;
    }
}
