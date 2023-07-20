package ru.practicum.shareit.user.repository;

import ru.practicum.shareit.user.User;

import java.util.Collection;

public interface UserRepository {
    Collection<User> findAll();

    User findById(long id);

    User save(User user);

    User update(long id, User user);

    void delete(long id);
}
