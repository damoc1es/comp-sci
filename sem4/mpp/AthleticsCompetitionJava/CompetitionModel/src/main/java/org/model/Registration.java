package org.model;

import java.util.UUID;

public class Registration extends Entity<UUID> {
    private Child child;
    private AllowedGroup allowedGroup;

    public Registration(UUID id, Child child, AllowedGroup allowedGroup) {
        super(id);
        this.child = child;
        this.allowedGroup = allowedGroup;
    }

    public Child getChild() {
        return child;
    }

    public void setChild(Child child) {
        this.child = child;
    }

    public AllowedGroup getAllowedGroup() {
        return allowedGroup;
    }

    public void setAllowedGroup(AllowedGroup allowedGroup) {
        this.allowedGroup = allowedGroup;
    }

    @Override
    public String toString() {
        return "Registration{" +
                "child=" + child +
                ", allowedGroup=" + allowedGroup +
                '}';
    }
}
