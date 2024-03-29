package org.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import java.util.UUID;

@Entity
@Table(name = "organisers")
public class Organiser extends org.model.Entity<UUID> {
    private String username;
    private String password;

    public Organiser() {
        super(UUID.randomUUID());
    }

    public Organiser(UUID id, String username, String password) {
        super(id);
        this.username = username;
        this.password = password;
    }

    @Id
    @Column(name = "username")
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Column(name = "password")
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "Organiser{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
