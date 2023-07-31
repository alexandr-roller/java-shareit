package ru.practicum.shareit.booking.service.finder;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;
import ru.practicum.shareit.booking.entity.Booking;
import ru.practicum.shareit.booking.repository.BookingRepository;
import ru.practicum.shareit.booking.service.BookingService;
import ru.practicum.shareit.booking.util.BookingStatus;

import java.util.List;

@Component
@RequiredArgsConstructor
public class BookingFinderWaiting implements BookingFinder {
    private final BookingRepository bookingRepository;

    @Override
    public List<Booking> findByOwnerId(Long ownerId, Sort sort) {
        return bookingRepository.findByOwnerIdAndStatus(ownerId, BookingStatus.WAITING, sort);
    }

    @Override
    public List<Booking> findByBookerId(Long bookerId, Sort sort) {
        return bookingRepository.findByBookerIdAndStatus(bookerId, BookingStatus.WAITING);
    }

    @Override
    public BookingService.BookingState getSearchType() {
        return BookingService.BookingState.WAITING;
    }
}
