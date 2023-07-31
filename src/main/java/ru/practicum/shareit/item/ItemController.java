package ru.practicum.shareit.item;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.item.dto.CommentDto;
import ru.practicum.shareit.item.dto.CommentMapper;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.service.ItemService;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Collection;

@RestController
@RequestMapping("/items")
@RequiredArgsConstructor
public class ItemController {
    private final ItemService itemService;

    @GetMapping
    public Collection<ItemDto> findAllByOwner(@NotNull @RequestHeader("X-Sharer-User-Id") long userId) {
        return itemService.findAllByOwner(userId);
    }

    @GetMapping(value = "/{id}")
    public ItemDto findById(@PathVariable long id,
                            @NotNull @RequestHeader("X-Sharer-User-Id") long userId) {
        return itemService.findById(id, userId);
    }

    @GetMapping(value = "/search")
    public Collection<ItemDto> search(@RequestParam(name = "text") String query) {
        return itemService.search(query);
    }

    @PostMapping
    public ItemDto save(@NotNull @RequestHeader("X-Sharer-User-Id") long userId,
                        @Valid @RequestBody ItemDto itemDto) {
        return itemService.save(userId, itemDto);
    }

    @PostMapping(value = "/{itemId}/comment")
    public CommentDto createComment(@PathVariable Long itemId,
                                    @Valid @RequestBody CommentDto commentDto,
                                    @NotNull @RequestHeader("X-Sharer-User-Id") Long userId) {
        return CommentMapper.toDto(itemService.createComment(commentDto, itemId, userId));
    }

    @PatchMapping(value = "/{id}")
    public ItemDto update(@NotNull @RequestHeader("X-Sharer-User-Id") long userId,
                          @PathVariable long id,
                          @RequestBody ItemDto itemDto) {
        return itemService.update(userId, id, itemDto);
    }

    @DeleteMapping(value = "/{id}")
    public void delete(@NotNull @RequestHeader("X-Sharer-User-Id") long userId,
                       @PathVariable long id) {
        itemService.delete(userId, id);
    }
}
