package ru.practicum.shareit.request.repository;

import ru.practicum.shareit.request.ItemRequest;

import java.util.List;

public interface ItemRequestRepository {
    List<ItemRequest> findAll();

    ItemRequest findById(long id);

    ItemRequest save(ItemRequest model);

    ItemRequest update(long id, ItemRequest model);

    void delete(long id);
}
