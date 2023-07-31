package ru.practicum.shareit.item.service;

import ru.practicum.shareit.item.dto.ItemDto;

import javax.validation.constraints.NotNull;
import java.util.Collection;
import java.util.List;

public interface ItemService {
    List<ItemDto> findAllByOwner(@NotNull long userId);

    ItemDto findById(long id);

    Collection<ItemDto> search(String query);

    ItemDto save(long userId, ItemDto itemDto);

    ItemDto update(long userId, long id, ItemDto itemDto);

    void delete(long userId, long id);
}
