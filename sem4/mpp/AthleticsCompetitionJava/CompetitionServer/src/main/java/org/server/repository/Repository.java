package org.server.repository;

import org.model.exception.DuplicatedException;
import org.model.Entity;

public interface Repository<ID, E extends Entity<ID>> {
    void store(E obj) throws DuplicatedException;
    void remove(E obj) throws Exception;
    void update(ID id, E newObj);
    Iterable<E> getAll();
    E findById(ID id);
}
