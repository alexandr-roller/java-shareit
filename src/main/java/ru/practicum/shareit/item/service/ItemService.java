package ru.practicum.shareit.item.service;

import ru.practicum.shareit.common.Crud;
import ru.practicum.shareit.item.dto.ItemDto;

import java.util.List;

public interface ItemService extends Crud<ItemDto> {
    List<ItemDto> search(String query);
}
