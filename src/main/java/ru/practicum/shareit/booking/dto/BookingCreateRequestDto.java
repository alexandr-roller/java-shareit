package ru.practicum.shareit.booking.dto;

import lombok.*;
import ru.practicum.shareit.booking.validator.ValidPeriod;

import javax.validation.constraints.Future;
import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
@ValidPeriod
public class BookingCreateRequestDto {
    @FutureOrPresent
    private LocalDateTime start;

    @Future
    private LocalDateTime end;

    private Long bookerId;

    @NotNull
    private Long itemId;
}
