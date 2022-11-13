package domain;

import java.io.Serial;
import java.io.Serializable;
import java.util.UUID;

public class Entity implements Serializable {
    @Serial
    private static final long serialVersionUID = 52L;
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
