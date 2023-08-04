package ru.practicum.shareit.item.service;

import ru.practicum.shareit.item.dto.CommentDto;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.model.Comment;

import javax.validation.constraints.NotNull;
import java.util.Collection;
import java.util.List;

public interface ItemService {
    List<ItemDto> findAllByOwner(@NotNull long userId);

    ItemDto findById(long id, long userId);

    Collection<ItemDto> search(String query);

    ItemDto save(long userId, ItemDto itemDto);

    ItemDto update(long userId, long id, ItemDto itemDto);

    void delete(long userId, long id);

    Comment createComment(CommentDto commentDto, Long itemId, Long userId);
}