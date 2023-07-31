package ru.practicum.shareit.request.service;

import ru.practicum.shareit.request.dto.ItemRequestDto;

import java.util.List;

public interface ItemRequestService {
    List<ItemRequestDto> findAll();

    ItemRequestDto findById(long id);

    ItemRequestDto save(ItemRequestDto model);

    ItemRequestDto update(long id, ItemRequestDto model);

    void delete(long id);
}
