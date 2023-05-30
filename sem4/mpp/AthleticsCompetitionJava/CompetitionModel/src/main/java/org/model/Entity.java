package org.model;

import java.io.Serializable;

public class Entity<ID> implements Serializable {
    private static final long serialVersionUID = 52L;
    private ID id;

    public Entity(ID id) {
        this.id = id;
    }

    public ID getId() {
        return id;
    }

    public void setId(ID id) {
        this.id = id;
    }
}
