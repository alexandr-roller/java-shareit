package ru.practicum.shareit.user.dto;

import lombok.Builder;
import lombok.Data;
import org.springframework.lang.Nullable;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Pattern;
import java.time.LocalDate;

@Data
@Builder(builderMethodName = "userDtoBuilder")
public class UserDto {
    private long id;

    @Email
    private String email;

    @NotBlank
    @Pattern(regexp = "^\\S*$")
    private String login;

    @Nullable
    private String name;

    @PastOrPresent
    private LocalDate birthday;
}
