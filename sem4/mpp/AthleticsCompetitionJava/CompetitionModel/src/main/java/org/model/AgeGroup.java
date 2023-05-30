package org.model;

import java.util.Objects;
import java.util.UUID;

public class AgeGroup extends Entity<UUID> {
    private int minAge, maxAge;

    public AgeGroup(UUID id, int minAge, int maxAge) {
        super(id);
        this.minAge = minAge;
        this.maxAge = maxAge;
    }

    public int getMinAge() {
        return minAge;
    }

    public void setMinAge(int minAge) {
        this.minAge = minAge;
    }

    public int getMaxAge() {
        return maxAge;
    }

    public void setMaxAge(int maxAge) {
        this.maxAge = maxAge;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AgeGroup ageGroup = (AgeGroup) o;
        return minAge == ageGroup.minAge && maxAge == ageGroup.maxAge;
    }

    @Override
    public int hashCode() {
        return Objects.hash(minAge, maxAge);
    }

    @Override
    public String toString() {
        return "Grupa de varsta " + minAge + "-" + maxAge + " ani";
    }
}