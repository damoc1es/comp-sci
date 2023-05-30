package org.networking.objectprotocol;

import org.networking.dto.EventDTO;

public class GetEventTypesForAgeGroupResponse implements Response {
    private EventDTO[] events;

    public GetEventTypesForAgeGroupResponse(EventDTO[] events) {
        this.events = events;
    }

    public EventDTO[] getEvents() {
        return events;
    }
}
