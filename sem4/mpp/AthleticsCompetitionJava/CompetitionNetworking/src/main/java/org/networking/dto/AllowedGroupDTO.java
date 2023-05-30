package org.networking.dto;

import java.io.Serializable;
import java.util.UUID;

public class AllowedGroupDTO implements Serializable {
    private final UUID id;
    private final AgeGroupDTO ageGroup;
    private final EventDTO event;

    public AllowedGroupDTO(UUID id, AgeGroupDTO ageGroup, EventDTO event) {
        this.id = id;
        this.ageGroup = ageGroup;
        this.event = event;
    }

    public UUID getId() {
        return id;
    }

    public AgeGroupDTO getAgeGroup() {
        return ageGroup;
    }

    public EventDTO getEvent() {
        return event;
    }
}
