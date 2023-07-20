package ru.practicum.shareit.booking.repository;

import org.springframework.stereotype.Component;
import ru.practicum.shareit.booking.Booking;

import java.util.List;

@Component
public class BookingRepositoryImpl implements BookingRepository {
    @Override
    public List<Booking> findAll() {
        return null;
    }

    @Override
    public Booking findById(long id) {
        return null;
    }

    @Override
    public Booking save(Booking model) {
        return null;
    }

    @Override
    public Booking update(long id, Booking model) {
        return null;
    }

    @Override
    public void delete(long id) {

    }
}