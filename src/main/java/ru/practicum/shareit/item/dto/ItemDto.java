package ru.practicum.shareit.item.dto;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder(builderMethodName = "itemDtoBuilder")
public class ItemDto {
    private Long id;

    @NotBlank
    private String name;

    @NotBlank
    private String description;

    @NotNull
    private Boolean available;

    private Long requestId;

    private BookingDtoForItem lastBooking;

    private BookingDtoForItem nextBooking;

    @Builder.Default
    private List<CommentDto> comments = new ArrayList<>();

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class BookingDtoForItem {
        private Long id;
        private Long bookerId;
    }
}
