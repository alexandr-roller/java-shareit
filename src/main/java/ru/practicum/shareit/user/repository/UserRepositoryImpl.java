package ru.practicum.shareit.user.repository;

import org.springframework.stereotype.Component;
import ru.practicum.shareit.user.User;

import java.util.List;

@Component
public class UserRepositoryImpl implements UserRepository{
    @Override
    public List<User> findAll() {
        return null;
    }

    @Override
    public User findById(long id) {
        return null;
    }

    @Override
    public User save(User model) {
        return null;
    }

    @Override
    public User update(User model) {
        return null;
    }

    @Override
    public void delete(long id) {

    }
}
