package ru.practicum.shareit.item.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import ru.practicum.shareit.request.dto.ItemRequestDto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder(builderMethodName = "itemDtoBuilder")
public class ItemDto {
    private long id;

    @NotBlank
    private String name;

    @NotBlank
    private String description;

    @NotNull
    private Boolean available;

    private ItemRequestDto request;

    private BookingDtoForItem lastBooking;

    private BookingDtoForItem nextBooking;

    @Builder.Default
    private List<CommentDto> comments = new ArrayList<>();

    @Getter
    @Builder
    @AllArgsConstructor
    public static class BookingDtoForItem {
        private Long id;
        private Long bookerId;
    }
}
