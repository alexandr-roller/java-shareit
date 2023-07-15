package ru.practicum.shareit.item;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.service.ItemService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/items")
@RequiredArgsConstructor
public class ItemController {
    private final ItemService itemService;

    @GetMapping
    public List<ItemDto> findAll() {
        return itemService.findAll();
    }

    @GetMapping(value = "/{id}")
    public ItemDto findById(@PathVariable long id) {
        return itemService.findById(id);
    }

    @GetMapping(value = "/{search}")
    public List<ItemDto> search(@RequestParam String query) {
        return itemService.search(query);
    }

    @PostMapping
    private ItemDto save(@Valid @RequestBody ItemDto itemDto) {
        return itemService.save(itemDto);
    }

    @PutMapping
    public ItemDto update(@Valid @RequestBody ItemDto itemDto) {
        return itemService.update(itemDto);
    }

    @DeleteMapping(value = "/{id}")
    public void delete(@PathVariable long id) {
        itemService.delete(id);
    }
}
