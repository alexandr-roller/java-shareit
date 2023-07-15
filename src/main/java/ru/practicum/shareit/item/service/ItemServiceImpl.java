package ru.practicum.shareit.item.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.dto.ItemMapper;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.repository.ItemRepository;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class ItemServiceImpl implements ItemService{
    private final ItemRepository itemRepository;

    @Override
    public List<ItemDto> findAll() {
        return ItemMapper.toItemDto(itemRepository.findAll());
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
    public ItemDto save(ItemDto itemDto) {
        Item item = itemRepository.save(ItemMapper.toItem(itemDto));
        return ItemMapper.toItemDto(item);
    }

    @Override
    public ItemDto update(ItemDto itemDto) {
        Item item = itemRepository.update(ItemMapper.toItem(itemDto));
        return ItemMapper.toItemDto(item);
    }

    @Override
    public void delete(long id) {
        itemRepository.delete(id);
    }
}
