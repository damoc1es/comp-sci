package org.networking.objectprotocol;

import org.networking.dto.EventDTO;

public class GetAllEventsResponse implements Response {
    private EventDTO[] events;

    public GetAllEventsResponse(EventDTO[] events) {
        this.events = events;
    }

    public EventDTO[] getEvents() {
        return events;
    }
}
