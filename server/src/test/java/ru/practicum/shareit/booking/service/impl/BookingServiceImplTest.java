package ru.practicum.shareit.booking.service.impl;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import ru.practicum.shareit.booking.entity.Booking;
import ru.practicum.shareit.booking.exception.BookingIsAlreadyApprovedException;
import ru.practicum.shareit.booking.exception.BookingNotFoundException;
import ru.practicum.shareit.booking.service.BookingService;
import ru.practicum.shareit.booking.service.BookingServiceImpl;
import ru.practicum.shareit.booking.util.BookingStatus;
import ru.practicum.shareit.item.exception.ItemUnavailableException;
import ru.practicum.shareit.user.exception.UserNotFoundException;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@RunWith(SpringRunner.class)
@SpringBootTest
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@AutoConfigureTestDatabase
@Sql(value = {"/schema.sql", "/testdata/insert-users.sql", "/testdata/insert-items.sql", "/testdata/insert-bookings.sql"})
class BookingServiceImplTest {
    private final BookingServiceImpl bookingService;

    @Test
    void shouldCreate() {
        Booking booking = Booking
                .bookingBuilder()
                .start(LocalDateTime.now().plusHours(1))
                .end(LocalDateTime.now().plusHours(2))
                .status(BookingStatus.WAITING)
                .build();
        assertThrows(ItemUnavailableException.class, () -> bookingService.create(booking, 2L, 2L));
        assertThrows(UserNotFoundException.class, () -> bookingService.create(booking, 3L, 99L));
        Booking createdBooking = bookingService.create(booking, 3L, 2L);
        assertThat(createdBooking).hasFieldOrPropertyWithValue("status", BookingStatus.WAITING);
        assertThat(createdBooking.getStart()).isNotNull();
        assertThat(createdBooking.getEnd()).isNotNull();
    }

    @Test
    void shouldApprove() {
        Booking booking = bookingService.approve(4L, 1L, true);
        assertThat(booking).hasFieldOrPropertyWithValue("status", BookingStatus.APPROVED);
        assertThrows(BookingIsAlreadyApprovedException.class, () -> bookingService.approve(4L, 1L, true));
    }

    @Test
    void shouldFindById() {
        assertThrows(BookingNotFoundException.class, () -> bookingService.findById(99L, 2L));
        Booking booking = bookingService.findById(1L, 1L);
        assertThat(booking).hasFieldOrPropertyWithValue("id", 1L);
        assertThat(booking).hasFieldOrPropertyWithValue("status", BookingStatus.APPROVED);
        assertThat(booking.getStart()).isNotNull();
        assertThat(booking.getEnd()).isNotNull();
    }

    @Test
    void shouldFindByBookerId() {
        assertThat(bookingService.findByBookerId(3L, BookingService.BookingState.ALL, 0, 10)).hasSize(4);
        assertThat(bookingService.findByBookerId(3L, BookingService.BookingState.CURRENT, 0, 10)).hasSize(1);
        assertThat(bookingService.findByBookerId(3L, BookingService.BookingState.FUTURE, 0, 10)).hasSize(2);
        assertThat(bookingService.findByBookerId(3L, BookingService.BookingState.WAITING, 0, 10)).hasSize(1);
        assertThat(bookingService.findByBookerId(3L, BookingService.BookingState.PAST, 0, 10)).hasSize(1);
        assertThat(bookingService.findByBookerId(3L, BookingService.BookingState.REJECTED, 0, 10)).hasSize(1);
        assertThrows(UserNotFoundException.class, () -> bookingService.findByBookerId(99L, BookingService.BookingState.ALL, 0, 10));
    }

    @Test
    void shouldFindByOwnerId() {
        assertThat(bookingService.findByOwnerId(1L, BookingService.BookingState.ALL, 0, 10)).hasSize(4);
        assertThat(bookingService.findByOwnerId(1L, BookingService.BookingState.CURRENT, 0, 10)).hasSize(1);
        assertThat(bookingService.findByOwnerId(1L, BookingService.BookingState.FUTURE, 0, 10)).hasSize(2);
        assertThat(bookingService.findByOwnerId(1L, BookingService.BookingState.WAITING, 0, 10)).hasSize(1);
        assertThat(bookingService.findByOwnerId(1L, BookingService.BookingState.PAST, 0, 10)).hasSize(1);
        assertThat(bookingService.findByOwnerId(1L, BookingService.BookingState.REJECTED, 0, 10)).hasSize(1);
        assertThrows(UserNotFoundException.class, () -> bookingService.findByOwnerId(99L, BookingService.BookingState.ALL, 0, 10));
    }
}