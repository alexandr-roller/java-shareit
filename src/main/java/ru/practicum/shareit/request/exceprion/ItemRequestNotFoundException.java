package ru.practicum.shareit.request.exceprion;

import ru.practicum.shareit.common.ModelNotFoundException;

public class ItemRequestNotFoundException extends ModelNotFoundException {

    public ItemRequestNotFoundException(String message) {
        super(message);
    }

    public ItemRequestNotFoundException(Long id) {
        super(id, "itemRequest");
    }
}
