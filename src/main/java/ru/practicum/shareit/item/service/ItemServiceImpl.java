package ru.practicum.shareit.item.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.dto.ItemMapper;
import ru.practicum.shareit.item.exceprion.ItemNotFoundException;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.repository.ItemRepository;
import ru.practicum.shareit.user.User;
import ru.practicum.shareit.user.repository.UserRepository;

import javax.validation.constraints.NotNull;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class ItemServiceImpl implements ItemService {
    private final ItemRepository itemRepository;
    private final UserRepository userRepository;

    @Override
    public List<ItemDto> findAllByOwner(@NotNull long userId) {
        userRepository.findById(userId);
        return ItemMapper.toItemDto(itemRepository.findAllByOwner(userId));
    }

    @Override
    public ItemDto findById(long id) {
        return ItemMapper.toItemDto(itemRepository.findById(id));
    }

    @Override
    public List<ItemDto> search(String query) {
        return ItemMapper.toItemDto(itemRepository.search(query));
    }

    @Override
    public ItemDto save(long userId, ItemDto itemDto) {
        userRepository.findById(userId);
        User user = userRepository.findById(userId);
        Item item = ItemMapper.toItem(itemDto);
        item.setOwner(user);
        return ItemMapper.toItemDto(itemRepository.save(item));
    }

    @Override
    public ItemDto update(long userId, long id, ItemDto itemDto) {
        Item item = ItemMapper.toItem(itemDto);
        Item updatedItem = itemRepository.findById(id);
        User owner = userRepository.findById(userId);

        if (owner.getId() != updatedItem.getOwner().getId()) {
            throw new ItemNotFoundException("Item id = " + id + " with user id = " + userId + " not found");
        }
        if (item.getName() != null) {
            updatedItem.setName(item.getName());
        }
        if (item.getDescription() != null) {
            updatedItem.setDescription(item.getDescription());
        }
        if (item.getAvailable() != null) {
            updatedItem.setAvailable(item.getAvailable());
        }
        return ItemMapper.toItemDto(itemRepository.update(updatedItem));
    }

    @Override
    public void delete(long userId, long id) {
        Item item = itemRepository.findById(id);
        User owner = userRepository.findById(userId);
        if (item.getOwner().getId() == userId) {
            itemRepository.delete(id);
        } else {
            throw new ItemNotFoundException("Item id = " + id + " with user id = " + userId + " not found");
        }
    }
}