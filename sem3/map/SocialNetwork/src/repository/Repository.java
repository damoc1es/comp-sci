package repository;

import domain.Entity;
import domain.exception.DuplicatedException;
import domain.exception.ValidationException;

import java.util.List;
import java.util.UUID;

public interface Repository<T extends Entity> {
    void store(T obj) throws DuplicatedException, ValidationException;

    void remove(T obj);

    void update(T oldObj, T newObj) throws ValidationException;

    List<T> getList();

    int size();

    Iterable<T> findAll();

    T find(T obj);

    T findByUUID(UUID uuid);

    List<T> filterEntities(EntityFilterFunction function);
}
