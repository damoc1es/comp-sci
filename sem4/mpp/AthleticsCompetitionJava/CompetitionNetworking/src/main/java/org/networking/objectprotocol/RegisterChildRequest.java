package org.networking.objectprotocol;

import org.networking.dto.EventDTO;

public class RegisterChildRequest implements Request {
    private String name;
    private int age;
    private EventDTO event;

    public RegisterChildRequest(String name, int age, EventDTO event) {
        this.name = name;
        this.age = age;
        this.event = event;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public EventDTO getEvent() {
        return event;
    }
}
