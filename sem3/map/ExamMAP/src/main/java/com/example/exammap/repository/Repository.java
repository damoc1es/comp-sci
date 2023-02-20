package com.example.exammap.repository;

import java.util.List;

public interface Repository<ID, T> {
    List<T> getAll();

    T findById(ID id);
}
