package org.model;

import java.util.Objects;
import java.util.UUID;

public class AllowedGroup extends Entity<UUID> {
    private AgeGroup ageGroup;
    private Event event;

    public AllowedGroup(UUID uuid, AgeGroup ageGroup, Event event) {
        super(uuid);
        this.ageGroup = ageGroup;
        this.event = event;
    }

    public AgeGroup getAgeGroup() {
        return ageGroup;
    }

    public void setAgeGroup(AgeGroup ageGroup) {
        this.ageGroup = ageGroup;
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AllowedGroup that = (AllowedGroup) o;
        return Objects.equals(ageGroup, that.ageGroup) && Objects.equals(event, that.event);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ageGroup, event);
    }

    @Override
    public String toString() {
        return "AllowedGroup{" +
                "ageGroup=" + ageGroup +
                ", eventType=" + event +
                '}';
    }
}