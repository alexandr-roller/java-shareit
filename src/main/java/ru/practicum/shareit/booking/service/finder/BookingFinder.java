package ru.practicum.shareit.booking.service.finder;

import org.springframework.data.domain.Sort;
import ru.practicum.shareit.booking.entity.Booking;
import ru.practicum.shareit.booking.service.BookingService;

import java.util.List;

public interface BookingFinder {
    List<Booking> findByOwnerId(Long ownerId, Sort sort);

    List<Booking> findByBookerId(Long bookerId, Sort sort);

    BookingService.BookingState getSearchType();
}
