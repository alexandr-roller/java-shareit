package ru.practicum.shareit.booking.service;

import ru.practicum.shareit.booking.entity.Booking;

import java.util.List;

public interface BookingService {
    Booking create(Booking booking, Long itemid, Long userId);

    Booking approve(Long bookingId, Long ownerId, boolean approve);

    Booking findById(Long bookingId, Long userId);

    List<Booking> findByBookerId(Long bookerId, BookingState state);

    List<Booking> findByOwnerId(Long ownerId, BookingState state);

    enum BookingState {
        ALL, CURRENT, PAST, FUTURE, WAITING, REJECTED
    }
}
