package org.model;

import java.util.Objects;
import java.util.UUID;

public class Event extends Entity<UUID> {
    private int meters;

    public Event() {
        super(UUID.randomUUID());
    }

    public Event(UUID id, int meters) {
        super(id);
        this.meters = meters;
    }

    public int getMeters() {
        return meters;
    }

    public void setMeters(int meters) {
        this.meters = meters;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Event event = (Event) o;
        return meters == event.meters;
    }

    @Override
    public int hashCode() {
        return Objects.hash(meters);
    }

    @Override
    public String toString() {
        return "Proba de " + meters + " metrii";
    }
}