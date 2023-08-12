package ru.practicum.shareit.user.exception;

import ru.practicum.shareit.common.ModelNotFoundException;

public class UserNotFoundException extends ModelNotFoundException {
    public UserNotFoundException(Long id) {
        super(id, "User");
    }
}