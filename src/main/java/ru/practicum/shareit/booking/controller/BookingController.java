package ru.practicum.shareit.booking.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.booking.dto.BookingCreateRequestDto;
import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.booking.dto.BookingMapper;
import ru.practicum.shareit.booking.entity.Booking;
import ru.practicum.shareit.booking.service.BookingService;
import ru.practicum.shareit.booking.util.BookingStatus;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path = "/bookings")
@RequiredArgsConstructor
@Validated
public class BookingController {
    private final BookingService bookingService;

    @PostMapping
    public BookingDto create(
            @Valid @RequestBody BookingCreateRequestDto requestDto,
            @NotNull @RequestHeader("X-Sharer-User-Id") Long userId) {
        Booking booking = Booking
                .bookingBuilder()
                .start(requestDto.getStart())
                .end(requestDto.getEnd())
                .status(BookingStatus.WAITING)
                .build();

        return BookingMapper.toBookingDto(bookingService.create(booking, requestDto.getItemId(), userId));
    }

    @PatchMapping(value = "/{bookingId}")
    public BookingDto approve(
            @NotNull @RequestHeader("X-Sharer-User-Id") Long ownerId,
            @PathVariable Long bookingId,
            @RequestParam boolean approved) {
        return BookingMapper.toBookingDto(bookingService.approve(bookingId, ownerId, approved));
    }

    @GetMapping(value = "/{bookingId}")
    public BookingDto findById(
            @NotNull @RequestHeader("X-Sharer-User-Id") Long userId,
            @PathVariable Long bookingId) {
        return BookingMapper.toBookingDto(bookingService.findById(bookingId, userId));
    }

    @GetMapping
    public List<BookingDto> findByBookerId(
            @NotNull @RequestHeader("X-Sharer-User-Id") Long userId,
            @RequestParam(defaultValue = "ALL") BookingService.BookingState state,
            @RequestParam(defaultValue = "0") @PositiveOrZero Integer from,
            @RequestParam(defaultValue = "10") @Positive Integer size) {
        return bookingService
                .findByBookerId(userId, state, from, size)
                .stream()
                .map(BookingMapper::toBookingDto)
                .collect(Collectors.toUnmodifiableList());
    }

    @GetMapping(value = "/owner")
    public List<BookingDto> findByOwnerId(
            @NotNull @RequestHeader("X-Sharer-User-Id") Long ownerId,
            @RequestParam(defaultValue = "ALL") BookingService.BookingState state,
            @RequestParam(defaultValue = "0") @PositiveOrZero Integer from,
            @RequestParam(defaultValue = "10") @Positive Integer size) {
        return bookingService
                .findByOwnerId(ownerId, state, from, size)
                .stream()
                .map(BookingMapper::toBookingDto)
                .collect(Collectors.toUnmodifiableList());
    }
}
