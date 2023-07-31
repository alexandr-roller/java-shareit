package ru.practicum.shareit.item.exception;

public class ItemUnavailableException extends RuntimeException {
    public ItemUnavailableException() {
        super("Item is unavailable");
    }
}
