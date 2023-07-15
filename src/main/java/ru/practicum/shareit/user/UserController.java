package ru.practicum.shareit.user;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.service.UserService;

import java.util.List;

@RestController
@RequestMapping(path = "/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping
    public List<UserDto> findAll() {
        return userService.findAll();
    }

    @GetMapping
    public UserDto findById(@RequestHeader("X-SharIt-User-Id") long id) {
        return userService.findById(id);
    }

    @PostMapping
    public UserDto save(UserDto userDto) {
        return userService.save(userDto);
    }

    @PutMapping
    public UserDto update(UserDto userDto) {
        return userService.update(userDto);
    }

    @DeleteMapping
    public void delete(long id) {
        userService.delete(id);
    }
}
