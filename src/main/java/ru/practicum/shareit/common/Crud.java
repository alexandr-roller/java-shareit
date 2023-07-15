package ru.practicum.shareit.common;

import java.util.List;

public interface Crud<T> {
    List<T> findAll();

    T findById(long id);

    T save(T model);

    T update(T model);

    void delete(long id);
}
