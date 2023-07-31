package ru.practicum.shareit.request.dto;

import ru.practicum.shareit.request.ItemRequest;

import java.util.ArrayList;
import java.util.List;

public class ItemRequestMapper {
    public static ItemRequest toItemRequest(ItemRequestDto itemRequestDto) {
        return ItemRequest.builder().build();
    }

    public static ItemRequestDto toItemRequestDto(ItemRequest itemRequest) {
        return ItemRequestDto.builder().build();
    }

    public static List<ItemRequestDto> toItemRequestDto(Iterable<ItemRequest> itemRequests) {
        List<ItemRequestDto> dtos = new ArrayList<>();
        for (ItemRequest itemRequest : itemRequests) {
            dtos.add(toItemRequestDto(itemRequest));
        }
        return dtos;
    }
}
