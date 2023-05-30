package org.networking.dto;

import java.io.Serializable;
import java.util.UUID;

public class EventDTO implements Serializable {
    private final UUID id;
    private final int meters;

    public EventDTO(UUID id, int meters) {
        this.id = id;
        this.meters = meters;
    }

    public UUID getId() {
        return id;
    }

    public int getMeters() {
        return meters;
    }
}
