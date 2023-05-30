package org.server.repository;

import org.model.AgeGroup;
import org.model.AllowedGroup;
import org.model.Event;

import java.util.List;
import java.util.UUID;

public interface AllowedGroupsRepo extends Repository<UUID, AllowedGroup> {
    List<AllowedGroup> findByAgeGroupId(UUID id);
    AllowedGroup fromEventAndAgeGroup(AgeGroup ageGroup, Event event);
    List<AllowedGroup> findByEventId(UUID id);
}