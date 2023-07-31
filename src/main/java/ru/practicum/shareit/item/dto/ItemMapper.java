package ru.practicum.shareit.item.dto;

import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.request.dto.ItemRequestMapper;

import java.util.ArrayList;
import java.util.List;

public class ItemMapper {
    public static Item toItem(ItemDto itemDto) {
        return Item.itemBuilder()
                .id(itemDto.getId())
                .name(itemDto.getName())
                .description(itemDto.getDescription())
                .available(itemDto.getAvailable())
                .request(ItemRequestMapper.toItemRequest(itemDto.getRequest()))
                .build();
    }

    public static ItemDto toItemDto(Item item) {
        return ItemDto.itemDtoBuilder()
                .id(item.getId())
                .name(item.getName())
                .description(item.getDescription())
                .available(item.getAvailable())
                .request(ItemRequestMapper.toItemRequestDto(item.getRequest()))
                .build();
    }

    public static List<ItemDto> toItemDto(Iterable<Item> items) {
        List<ItemDto> dtos = new ArrayList<>();
        for (Item item : items) {
            dtos.add(toItemDto(item));
        }
        return dtos;
    }
}
