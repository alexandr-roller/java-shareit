package ru.practicum.shareit.item.repository;

import ru.practicum.shareit.item.model.Item;

import java.util.Collection;

public interface ItemRepository {
    Collection<Item> findAllByOwner(long userId);

    Item findById(long id);

    Collection<Item> search(String query);

    Item save(Item item);

    Item update(Item item);

    void delete(long id);
}