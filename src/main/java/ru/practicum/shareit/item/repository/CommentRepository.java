package ru.practicum.shareit.item.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.practicum.shareit.item.entity.Comment;
import ru.practicum.shareit.item.entity.Item;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    @Query("select c from Comment c join fetch c.author where c.item.id = :itemId")
    List<Comment> findByItemId(Long itemId);

    @Query("select c from Comment c join fetch c.author where c.item in :items")
    List<Comment> findByItemIn(List<Item> items);
}
