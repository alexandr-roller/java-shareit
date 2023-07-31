package ru.practicum.shareit.request.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.request.dto.ItemRequestDto;
import ru.practicum.shareit.request.dto.ItemRequestMapper;
import ru.practicum.shareit.request.repository.ItemRequestRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ItemRequestServiceImpl implements ItemRequestService {
    private final ItemRequestRepository itemRequestRepository;

    @Override
    public List<ItemRequestDto> findAll() {
        return ItemRequestMapper.toItemRequestDto(itemRequestRepository.findAll());
    }

    @Override
    public ItemRequestDto findById(long id) {
        return ItemRequestMapper.toItemRequestDto(itemRequestRepository.findById(id));
    }

    @Override
    public ItemRequestDto save(ItemRequestDto itemRequestDto) {
        return ItemRequestMapper.toItemRequestDto(itemRequestRepository.save(ItemRequestMapper.toItemRequest(itemRequestDto)));
    }

    @Override
    public ItemRequestDto update(long id, ItemRequestDto itemRequestDto) {
        return ItemRequestMapper.toItemRequestDto(itemRequestRepository.update(id, ItemRequestMapper.toItemRequest(itemRequestDto)));
    }

    @Override
    public void delete(long id) {
        itemRequestRepository.delete(id);
    }
}