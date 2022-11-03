package domain;

import java.util.Objects;
import java.util.UUID;

public class User extends Entity {
    private String name, handle;

    public User(String name, String handle) {
        this.name = name;
        this.handle = handle;
        super.setId(UUID.randomUUID());
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHandle() {
        return handle;
    }

    public void setHandle(String handle) {
        this.handle = handle;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(name, user.name) && handle.equals(user.handle);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, handle);
    }

    @Override
    public String toString() {
        return name + " (@" + handle + ")";
    }
}
