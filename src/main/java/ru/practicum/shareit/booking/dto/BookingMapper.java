package ru.practicum.shareit.booking.dto;

import ru.practicum.shareit.booking.Booking;

import java.util.ArrayList;
import java.util.List;

public class BookingMapper {
    public static Booking toBooking(BookingDto bookingDto) {
        return Booking.bookingBuilder().build();
    }

    public static BookingDto toBookingDto(Booking booking) {
        return BookingDto.bookingDtoBuilder().build();
    }

    public static List<BookingDto> toBookingDto(Iterable<Booking> bookings) {
        List<BookingDto> dtos = new ArrayList<>();
        for (Booking booking : bookings) {
            dtos.add(toBookingDto(booking));
        }
        return dtos;
    }
}
