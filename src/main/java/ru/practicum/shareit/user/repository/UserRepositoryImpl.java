package ru.practicum.shareit.user.repository;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import ru.practicum.shareit.user.User;
import ru.practicum.shareit.user.exceprion.UserNotFoundException;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Primary
@Component
public class UserRepositoryImpl implements UserRepository {
    private final Map<Long, User> users = new HashMap<>();
    private long lastId = 0L;

    @Override
    public Collection<User> findAll() {
        return users.values();
    }

    @Override
    public User findById(long id) {
        User user = users.get(id);
        if (user != null) {
            return user;
        } else {
            throw new UserNotFoundException(id);
        }
    }

    @Override
    public User save(User user) {
        user.setId(++lastId);
        users.put(user.getId(), user);
        return users.get(user.getId());
    }

    @Override
    public User update(long id, User user) {
        users.put(id, user);
        return users.get(id);
    }

    @Override
    public void delete(long id) {
        if (users.containsKey(id)) {
            users.remove(id);
        } else {
            throw new UserNotFoundException(id);
        }
    }
}