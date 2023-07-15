package ru.practicum.shareit.item.repository;

import ru.practicum.shareit.common.Crud;
import ru.practicum.shareit.item.model.Item;

import java.util.List;

public interface ItemRepository extends Crud<Item> {
    List<Item> search(String query);
}