package ru.practicum.shareit.booking.repository;

import ru.practicum.shareit.booking.Booking;

import java.util.List;

public interface BookingRepository {
    List<Booking> findAll();

    Booking findById(long id);

    Booking save(Booking model);

    Booking update(long id, Booking model);

    void delete(long id);
}