package ru.practicum.shareit.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;
import ru.practicum.shareit.user.User;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@Data
@Builder (builderClassName = "itemRequestBuilder")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ItemRequest {
    private long id;

    private String description;

    @NotBlank
    private User requester;

    private LocalDateTime created;
}
