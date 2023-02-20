package com.example.exammap.domain;

public class City {
    String id;
    String name;

    @Override
    public String toString() {
        return getName();
    }

    public City(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
