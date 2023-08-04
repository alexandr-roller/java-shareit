package ru.practicum.shareit.request;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.request.dto.ItemRequestDto;
import ru.practicum.shareit.request.service.ItemRequestService;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

@RestController
@RequestMapping(path = "/requests")
@RequiredArgsConstructor
public class ItemRequestController {
    private final ItemRequestService itemRequestService;

    @PostMapping
    public ItemRequestDto create(@Valid @RequestBody ItemRequestDto itemDto,
                                 @NotNull @RequestHeader("X-Sharer-User-Id") Long userId) {
        return null;
    }

    @GetMapping(value = "/{requestId}")
    public ItemRequestDto findById(@PathVariable Long requestId,
                                   @NotNull @RequestHeader("X-Sharer-User-Id") Long userId) {
        return null;
    }

    @GetMapping
    public List<ItemRequestDto> findByUserId(@NotNull @RequestHeader("X-Sharer-User-Id") Long userId) {
        return null;
    }

    @GetMapping(value = "/all")
    public List<ItemRequestDto> findAll(@NotNull @RequestHeader("X-Sharer-User-Id") Long userId,
                                        @RequestParam(defaultValue = "0") @PositiveOrZero Integer from,
                                        @RequestParam(defaultValue = "10") @Positive Integer size) {
        return null;
    }
}