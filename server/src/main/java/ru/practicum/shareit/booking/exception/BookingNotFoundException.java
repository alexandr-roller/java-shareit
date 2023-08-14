package ru.practicum.shareit.booking.exception;

import ru.practicum.shareit.common.ModelNotFoundException;

public class BookingNotFoundException extends ModelNotFoundException {
    public BookingNotFoundException(Long id) {
        super(id, "Booking");
    }
}
