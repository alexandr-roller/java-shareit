package ru.practicum.shareit.booking.service;

import ru.practicum.shareit.booking.dto.BookingDto;

import java.util.List;

public interface BookingService {
    List<BookingDto> findAll();

    BookingDto findById(long id);

    BookingDto save(BookingDto model);

    BookingDto update(long id, BookingDto model);

    void delete(long id);
}
