package org.server.repository;

import org.model.Child;

import java.util.UUID;

public interface ChildrenRepo extends Repository<UUID, Child> {
    Child findChildByNameAndAge(String name, Integer age);
}
