package ru.practicum.shareit.item.service.impl;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import ru.practicum.shareit.item.dto.CommentDto;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.entity.Comment;
import ru.practicum.shareit.item.exception.ItemNotFoundException;
import ru.practicum.shareit.item.exception.ItemWasNotBookedByUserException;
import ru.practicum.shareit.item.service.ItemServiceImpl;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@RunWith(SpringRunner.class)
@SpringBootTest
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@AutoConfigureTestDatabase
@Sql(value = {"/schema.sql", "/testdata/insert-users.sql", "/testdata/insert-items.sql", "/testdata/insert-bookings.sql"})
class ItemServiceImplTest {
    private final ItemServiceImpl itemService;

    @Test
    void shouldCreate() {
        ItemDto item = ItemDto
                .itemDtoBuilder()
                .name("Item")
                .description("Description")
                .available(true)
                .build();
        item = itemService.save(2L, item);
        assertThat(item).hasFieldOrPropertyWithValue("name", "Item");
        assertThat(item).hasFieldOrPropertyWithValue("description", "Description");
        assertThat(item).hasFieldOrPropertyWithValue("available", true);
    }

    @Test
    void shouldUpdate() {
        ItemDto item = ItemDto
                .itemDtoBuilder()
                .name("Item")
                .description("New Description")
                .available(true)
                .build();
        item = itemService.update(1L, 1L, item);
        assertThat(item).hasFieldOrPropertyWithValue("id", 1L);
        assertThat(item).hasFieldOrPropertyWithValue("name", "Item");
        assertThat(item).hasFieldOrPropertyWithValue("description", "New Description");
        assertThat(item).hasFieldOrPropertyWithValue("available", true);
    }

    @Test
    void shouldFindById() {
        ItemDto item = itemService.findById(2L, 2L);
        assertThat(item).hasFieldOrPropertyWithValue("id", 2L);
        assertThat(item).hasFieldOrPropertyWithValue("name", "Item2");
        assertThat(item).hasFieldOrPropertyWithValue("description", "Description2");
        assertThat(item).hasFieldOrPropertyWithValue("available", false);
        assertThrows(ItemNotFoundException.class, () -> itemService.findById(99L, 2L));
    }

    @Test
    void shouldFindByOwnerId() {
        assertThat(itemService.findAllByOwner(1L, 0, 10)).hasSize(3);
    }

    @Test
    void shouldFindByNameOrDescription() {
        List<ItemDto> items = itemService.search("Item1", 0, 10);
        ItemDto item = items.get(0);
        assertThat(item).hasFieldOrPropertyWithValue("id", 1L);
        assertThat(item).hasFieldOrPropertyWithValue("name", "Item1");
        assertThat(item).hasFieldOrPropertyWithValue("description", "Description1");
        assertThat(item).hasFieldOrPropertyWithValue("available", true);
    }

    @Test
    void shouldCreateComment() {
        CommentDto comment = CommentDto
                .builder()
                .text("Text")
                .created(LocalDateTime.now())
                .build();
        assertThrows(ItemWasNotBookedByUserException.class, () -> itemService.createComment(comment, 1L, 2L));
        Comment createdComment = itemService.createComment(comment, 1L, 3L);
        assertThat(createdComment).hasFieldOrPropertyWithValue("id", 1L);
        assertThat(createdComment).hasFieldOrPropertyWithValue("text", "Text");
        assertThat(createdComment.getCreated()).isNotNull();
        assertThat(createdComment.getAuthor().getId()).isEqualTo(3L);
        assertThat(createdComment.getItem().getId()).isEqualTo(1L);
    }
}