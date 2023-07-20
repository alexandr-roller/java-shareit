package ru.practicum.shareit.user;

import lombok.Builder;
import lombok.Data;

@Data
@Builder(builderMethodName = "userBuilder")
public class User {
    private long id;

    private String name;

    private String email;

    private String login;
}
