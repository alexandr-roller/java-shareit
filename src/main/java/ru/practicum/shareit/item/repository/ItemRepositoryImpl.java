package ru.practicum.shareit.item.repository;

import org.springframework.stereotype.Component;
import ru.practicum.shareit.item.model.Item;

import java.util.*;
import java.util.stream.Collectors;

@Component
public class ItemRepositoryImpl implements ItemRepository{
    private final Map<Long, Item> items = new HashMap<>();

    @Override
    public List<Item> findAll() {
        return (List<Item>) items.values();
    }

    @Override
    public Item findById(long id) {
        return items.get(id);
    }

    @Override
    public List<Item> search(String query) {
        return items.values().stream().filter(item -> item.getName().toLowerCase().contains(query.toLowerCase()))
                .collect(Collectors.toList());
    }

    @Override
    public Item save(Item item) {
        item.setId(getId());
        return items.put(getId(), item);
    }

    @Override
    public Item update(Item item) {
        return items.put(item.getId(), item);
    }

    @Override
    public void delete(long id) {
        items.remove(id);
    }

    private long getId() {
        long lastId;
        lastId = Collections.max(items.keySet());
        return lastId + 1;
    }
}
