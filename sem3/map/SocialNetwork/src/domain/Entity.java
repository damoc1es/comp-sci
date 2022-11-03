package domain;

import java.io.Serializable;
import java.util.UUID;

public class Entity implements Serializable {
    private UUID id;

    /**
     * ID getter
     * @return ID
     */
    public UUID getId() {
        return id;
    }

    /**
     * ID setter
     * @param newId
     */
    public void setId(UUID newId) {
        id = newId;
    }
}
