package ru.practicum.shareit.item.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;
import ru.practicum.shareit.request.ItemRequest;
import ru.practicum.shareit.user.User;

import javax.validation.constraints.NotBlank;

@Data
@Builder(builderMethodName = "itemBuilder")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Item {
    private long id;

    @NotBlank
    private String name;

    private String description;

    private Boolean available;

    private User owner;

    private ItemRequest request;
}
