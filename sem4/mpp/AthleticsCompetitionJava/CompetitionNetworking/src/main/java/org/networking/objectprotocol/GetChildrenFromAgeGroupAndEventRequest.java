package org.networking.objectprotocol;

import org.networking.dto.AgeGroupDTO;
import org.networking.dto.EventDTO;

public class GetChildrenFromAgeGroupAndEventRequest implements Request {
    private AgeGroupDTO ageGroup;
    private EventDTO event;

    public GetChildrenFromAgeGroupAndEventRequest(AgeGroupDTO ageGroup, EventDTO event) {
        this.ageGroup = ageGroup;
        this.event = event;
    }

    public AgeGroupDTO getAgeGroup() {
        return ageGroup;
    }

    public EventDTO getEvent() {
        return event;
    }
}
