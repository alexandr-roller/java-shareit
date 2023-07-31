package ru.practicum.shareit.booking.dto;

import lombok.Builder;
import lombok.Getter;
import ru.practicum.shareit.booking.validator.ValidPeriod;

import javax.validation.constraints.Future;
import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Getter
@Builder
@ValidPeriod
public class BookingCreateRequestDto {
    @FutureOrPresent
    private LocalDateTime start;

    @Future
    private LocalDateTime end;

    private Long userId;

    @NotNull
    private Long itemId;
}
