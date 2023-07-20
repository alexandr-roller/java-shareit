package ru.practicum.shareit.booking;

import lombok.Builder;
import lombok.Data;
import ru.practicum.shareit.booking.util.BookingStatus;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.User;

import java.time.LocalDateTime;

@Data
@Builder(builderMethodName = "bookingBuilder")
public class Booking {
    private long id;

    private LocalDateTime start;

    private LocalDateTime end;

    private Item item;

    private User booker;

    private BookingStatus bookingStatus;
}
