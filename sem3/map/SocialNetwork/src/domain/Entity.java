package domain;

import java.io.Serializable;
import java.util.UUID;

public class Entity implements Serializable {
    private UUID id;

    public UUID getId() {
        return id;
    }

    public void setId(UUID newId) {
        id = newId;
    }
}
