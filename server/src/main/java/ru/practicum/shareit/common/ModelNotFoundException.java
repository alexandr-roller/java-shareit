package ru.practicum.shareit.common;

public class ModelNotFoundException extends RuntimeException {
    public ModelNotFoundException(String message) {
        super(message);
    }

    public ModelNotFoundException(Long id, String model) {
        super(model + " with id=" + id + " not found");
    }
}