package ru.practicum.shareit.item.exception;

import ru.practicum.shareit.common.ModelNotFoundException;

public class ItemNotFoundException extends ModelNotFoundException {
    public ItemNotFoundException(Long id) {
        super(id, "Item");
    }

    public ItemNotFoundException(String message) {
        super(message);
    }

}
