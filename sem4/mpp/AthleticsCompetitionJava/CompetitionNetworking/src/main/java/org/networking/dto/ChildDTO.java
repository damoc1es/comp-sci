package org.networking.dto;

import java.io.Serializable;
import java.util.UUID;

public class ChildDTO implements Serializable {
    private final UUID id;
    private final String name;
    private final int age;

    public ChildDTO(UUID id, String name, int age) {
        this.id = id;
        this.name = name;
        this.age = age;
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }
}
