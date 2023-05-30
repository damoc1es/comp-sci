package org.server.repository;

import org.model.Organiser;

import java.util.UUID;

public interface OrganisersRepo extends Repository<UUID, Organiser> {
    Organiser findByUsername(String username);
}
