package ru.practicum.shareit.request.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.request.dto.ItemRequestDto;
import ru.practicum.shareit.request.dto.ItemRequestMapper;
import ru.practicum.shareit.request.entity.ItemRequest;
import ru.practicum.shareit.request.service.ItemRequestService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path = "/requests")
@RequiredArgsConstructor
@Validated
public class ItemRequestController {
    private final ItemRequestService itemRequestService;

    @PostMapping
    public ItemRequestDto create(@RequestBody ItemRequestDto itemRequestDto,
                                 @RequestHeader("X-Sharer-User-Id") Long userId) {
        ItemRequest request = ItemRequestMapper.toItemRequest(itemRequestDto);

        return ItemRequestMapper.toItemRequestDto(itemRequestService.create(request, userId));
    }

    @GetMapping(value = "/{requestId}")
    public ItemRequestDto findById(@PathVariable Long requestId,
                                   @RequestHeader("X-Sharer-User-Id") Long userId) {
        return ItemRequestMapper.toItemRequestDto(itemRequestService.findById(requestId, userId));
    }

    @GetMapping
    public List<ItemRequestDto> findByUserId(@RequestHeader("X-Sharer-User-Id") Long userId) {
        return itemRequestService.findByUserId(userId)
                .stream()
                .map(ItemRequestMapper::toItemRequestDto)
                .collect(Collectors.toUnmodifiableList());
    }

    @GetMapping(value = "/all")
    public List<ItemRequestDto> findAll(@RequestHeader("X-Sharer-User-Id") Long userId,
                                        @RequestParam(defaultValue = "0") Integer from,
                                        @RequestParam(defaultValue = "10") Integer size) {
        return itemRequestService.findAll(userId, from, size)
                .stream()
                .map(ItemRequestMapper::toItemRequestDto)
                .collect(Collectors.toUnmodifiableList());
    }
}