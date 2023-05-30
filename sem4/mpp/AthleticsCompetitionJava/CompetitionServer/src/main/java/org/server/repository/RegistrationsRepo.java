package org.server.repository;

import org.model.AgeGroup;
import org.model.Child;
import org.model.Event;
import org.model.Registration;

import java.util.List;
import java.util.UUID;

public interface RegistrationsRepo extends Repository<UUID, Registration> {
    Integer countEventsWhereChildParticipates(Child child);
    List<Registration> fromEventAndAgeGroup(AgeGroup ageGroup, Event event);
}
