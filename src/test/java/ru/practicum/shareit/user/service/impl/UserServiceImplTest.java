package ru.practicum.shareit.user.service.impl;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.exception.UserNotFoundException;
import ru.practicum.shareit.user.service.UserServiceImpl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@RunWith(SpringRunner.class)
@SpringBootTest
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@AutoConfigureTestDatabase
@Sql(value = {"/schema.sql", "/testdata/insert-users.sql"})
class UserServiceImplTest {
    private final UserServiceImpl userService;

    @Test
    void shouldCreate() {
        UserDto user = UserDto
                .userDtoBuilder()
                .name("Name")
                .email("testEmail@test.com")
                .build();
        user = userService.save(user);
        assertThat(user).hasFieldOrPropertyWithValue("name", "Name");
        assertThat(user).hasFieldOrPropertyWithValue("email", "testEmail@test.com");
    }

    @Test
    void shouldUpdate() {
        UserDto user = UserDto
                .userDtoBuilder()
                .name("NewNameUser1")
                .email("newUser1@test.com")
                .build();
        user = userService.update(1L, user);
        assertThat(user).hasFieldOrPropertyWithValue("id", 1L);
        assertThat(user).hasFieldOrPropertyWithValue("name", "NewNameUser1");
        assertThat(user).hasFieldOrPropertyWithValue("email", "newUser1@test.com");
    }

    @Test
    void shouldFindById() {
        UserDto user = userService.findById(2L);
        assertThat(user).hasFieldOrPropertyWithValue("id", 2L);
        assertThat(user).hasFieldOrPropertyWithValue("name", "User2");
        assertThat(user).hasFieldOrPropertyWithValue("email", "user2@test.com");
    }

    @Test
    void shouldNotFindByWrongId() {
        assertThrows(UserNotFoundException.class, () -> userService.findById(99L));
    }

    @Test
    @Sql(value = {"/testdata/delete-users.sql", "/testdata/insert-users.sql"})
    void shouldFindAll() {
        assertThat(userService.findAll(0, 10)).hasSize(3);
    }

    @Test
    void shouldDeleteById() {
        userService.delete(1L);
        assertThat(userService.findAll(0, 10)).map(UserDto::getId).doesNotContain(1L);
    }
}