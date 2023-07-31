package ru.practicum.shareit.request.repository;

import org.springframework.stereotype.Component;
import ru.practicum.shareit.request.ItemRequest;

import java.util.List;

@Component
public class ItemRequestRepositoryImpl implements ItemRequestRepository {
    @Override
    public List<ItemRequest> findAll() {
        return null;
    }

    @Override
    public ItemRequest findById(long id) {
        return null;
    }

    @Override
    public ItemRequest save(ItemRequest model) {
        return null;
    }

    @Override
    public ItemRequest update(long id, ItemRequest model) {
        return null;
    }

    @Override
    public void delete(long id) {

    }
}