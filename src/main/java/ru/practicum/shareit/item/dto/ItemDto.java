package ru.practicum.shareit.item.dto;

import lombok.Builder;
import lombok.Data;
import ru.practicum.shareit.request.ItemRequest;
import ru.practicum.shareit.user.User;

import javax.validation.constraints.NotBlank;

@Data
@Builder(builderMethodName = "itemDtoBuilder")
public class ItemDto {
        private long id;

        @NotBlank
        private String name;

        private String description;

        private Boolean available;

        private User owner;

        private ItemRequest request;
}
