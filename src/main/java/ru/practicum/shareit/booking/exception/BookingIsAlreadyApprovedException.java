package ru.practicum.shareit.booking.exception;

public class BookingIsAlreadyApprovedException extends RuntimeException {
    public BookingIsAlreadyApprovedException() {
        super("Booking is already approved");
    }
}
