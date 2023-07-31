package ru.practicum.shareit.request.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;
import ru.practicum.shareit.user.dto.UserDto;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
@Builder(builderClassName = "itemRequestDtoBuilder")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ItemRequestDto {
    private long id;

    private String description;

    @NotNull
    private UserDto requester;

    private LocalDateTime created;
}
