package ru.practicum.shareit.user.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(builderMethodName = "userDtoBuilder")
public class UserDto {
    private Long id;

    private String name;

    @NotBlank
    @Email
    private String email;

    private String login;
}
