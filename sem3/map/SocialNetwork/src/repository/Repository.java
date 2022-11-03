package repository;

import domain.exception.DuplicatedException;

import java.util.List;

public interface Repository<T> {
    void store(T obj) throws DuplicatedException;

    void remove(T obj);

    void update(T oldObj, T newObj);

    List<T> getList();

    int size();

    T find(T obj);
}
