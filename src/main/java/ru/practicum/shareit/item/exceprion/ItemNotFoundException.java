package ru.practicum.shareit.item.exceprion;

import ru.practicum.shareit.common.ModelNotFoundException;

public class ItemNotFoundException extends ModelNotFoundException {
    public ItemNotFoundException(Long id) {
        super(id, "Item");
    }

    public ItemNotFoundException(String message) {
        super(message);
    }

}
