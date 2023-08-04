package ru.practicum.shareit.item.dto;

import lombok.experimental.UtilityClass;
import ru.practicum.shareit.item.model.Comment;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@UtilityClass
public class CommentMapper {
    public Comment toComment(CommentDto commentDto) {
        return Comment.builder()
                .text(commentDto.getText())
                .created(LocalDateTime.now())
                .build();
    }

    public static List<Comment> toComment(Iterable<CommentDto> comments) {
        List<Comment> dtos = new ArrayList<>();
        for (CommentDto comment : comments) {
            dtos.add(toComment(comment));
        }
        return dtos;
    }

    public CommentDto toDto(Comment comment) {
        return CommentDto.builder()
                .id(comment.getId())
                .text(comment.getText())
                .authorName(comment.getAuthor().getName())
                .created(comment.getCreated())
                .build();
    }

    public static List<CommentDto> toDto(Iterable<Comment> comments) {
        List<CommentDto> dtos = new ArrayList<>();
        for (Comment comment : comments) {
            dtos.add(toDto(comment));
        }
        return dtos;
    }
}