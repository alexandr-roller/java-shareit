package ru.practicum.shareit.item.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.practicum.shareit.item.exceprion.ItemNotFoundException;
import ru.practicum.shareit.item.model.Item;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class ItemRepositoryImpl implements ItemRepository {
    private final Map<Long, Item> items = new HashMap<>();
    private long lastId;

    @Override
    public Collection<Item> findAllByOwner(long userId) {
        return items.values().stream().filter(item -> item.getOwner().getId() == userId).collect(Collectors.toList());
    }

    @Override
    public Item findById(long id) {
        Item item = items.get(id);
        if (item != null) {
            return item;
        } else {
            throw new ItemNotFoundException(id);
        }
    }

    @Override
    public Collection<Item> search(String query) {
        if (query.isBlank()) {
            return Collections.emptyList();
        } else {
            String text = query.toLowerCase();
            return items.values().stream().filter(item -> (item.getName().toLowerCase().contains(text)
                    || item.getDescription().toLowerCase().contains(text))
                    && item.getAvailable()).collect(Collectors.toList());
        }
    }

    @Override
    public Item save(Item item) {
        item.setId(++lastId);
        items.put(item.getId(), item);
        return item;
    }

    @Override
    public Item update(Item item) {
        items.put(item.getId(), item);
        return item;
    }

    @Override
    public void delete(long id) {
        items.remove(id);
    }
}