package ru.practicum.shareit.user.dto;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Data
@Builder(builderMethodName = "userDtoBuilder")
public class UserDto {
    private long id;

    private String name;

    @NotBlank
    @Email
    private String email;

    private String login;
}
