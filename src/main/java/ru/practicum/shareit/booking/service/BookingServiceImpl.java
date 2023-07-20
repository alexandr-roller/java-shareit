package ru.practicum.shareit.booking.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.booking.dto.BookingMapper;
import ru.practicum.shareit.booking.repository.BookingRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookingServiceImpl implements BookingService {
    private final BookingRepository bookingRepository;

    @Override
    public List<BookingDto> findAll() {
        return BookingMapper.toBookingDto(bookingRepository.findAll());
    }

    @Override
    public BookingDto findById(long id) {
        return BookingMapper.toBookingDto(bookingRepository.findById(id));
    }

    @Override
    public BookingDto save(BookingDto bookingDto) {
        return BookingMapper.toBookingDto(bookingRepository.save(BookingMapper.toBooking(bookingDto)));
    }

    @Override
    public BookingDto update(long id, BookingDto bookingDto) {
        return BookingMapper.toBookingDto(bookingRepository.update(id, BookingMapper.toBooking(bookingDto)));
    }

    @Override
    public void delete(long id) {
        bookingRepository.delete(id);
    }
}