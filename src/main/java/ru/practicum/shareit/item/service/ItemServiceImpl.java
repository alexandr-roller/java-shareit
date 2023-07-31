package ru.practicum.shareit.item.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.shareit.booking.entity.Booking;
import ru.practicum.shareit.booking.repository.BookingRepository;
import ru.practicum.shareit.booking.util.BookingStatus;
import ru.practicum.shareit.item.dto.CommentDto;
import ru.practicum.shareit.item.dto.CommentMapper;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.dto.ItemMapper;
import ru.practicum.shareit.item.exception.ItemNotFoundException;
import ru.practicum.shareit.item.exception.ItemWasNotBookedByUserException;
import ru.practicum.shareit.item.model.Comment;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.repository.CommentRepository;
import ru.practicum.shareit.item.repository.ItemRepository;
import ru.practicum.shareit.user.User;
import ru.practicum.shareit.user.exceprion.UserNotFoundException;
import ru.practicum.shareit.user.repository.UserRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class ItemServiceImpl implements ItemService {
    private final ItemRepository itemRepository;
    private final UserRepository userRepository;
    private final BookingRepository bookingRepository;
    private final CommentRepository commentRepository;

    @PersistenceContext
    private final EntityManager entityManager;

    @Transactional(readOnly = true)
    @Override
    public List<ItemDto> findAllByOwner(@NotNull long userId) {
        List<Item> items = itemRepository.findByOwnerId(userId);
        setDataFromDb(items, true);
        return ItemMapper.toItemDto(items);
    }

    @Transactional(readOnly = true)
    @Override
    public ItemDto findById(long id, long userId) {
        Item item = itemRepository.findById(id).orElseThrow(() -> new ItemNotFoundException(id));
        item.setComments(commentRepository.findByItemId(id));
        item.setBookings(bookingRepository.findByItemId(id));

        if (item.getOwner().getId() == userId) {
            setLastAndNextBooking(item);
        }

        return ItemMapper.toItemDto(item);
    }

    @Transactional(readOnly = true)
    @Override
    public List<ItemDto> search(String query) {
        if (query.isBlank()) {
            return Collections.emptyList();
        } else {
            List<Item> items = itemRepository.findAvailableByNameOrDescription(query, query);
            setDataFromDb(items, false);
            return ItemMapper.toItemDto(items);
        }
    }

    @Override
    public ItemDto save(long userId, ItemDto itemDto) {
        User user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException(userId));
        Item item = ItemMapper.toItem(itemDto);
        item.setOwner(user);
        return ItemMapper.toItemDto(itemRepository.save(item));
    }

    @Override
    public ItemDto update(long userId, long id, ItemDto itemDto) {
        Item item = ItemMapper.toItem(itemDto);
        Item updatedItem = itemRepository.findById(id).orElseThrow(() -> new ItemNotFoundException(id));
        User owner = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException(userId));

        if (owner.getId() != updatedItem.getOwner().getId()) {
            throw new ItemNotFoundException("Item id = " + id + " with user id = " + userId + " not found");
        }
        if (item.getName() != null) {
            updatedItem.setName(item.getName());
        }
        if (item.getDescription() != null) {
            updatedItem.setDescription(item.getDescription());
        }
        if (item.getAvailable() != null) {
            updatedItem.setAvailable(item.getAvailable());
        }
        return ItemMapper.toItemDto(itemRepository.save(updatedItem));
    }

    @Override
    public void delete(long userId, long id) {
        Item item = itemRepository.findById(id).orElseThrow(() -> new ItemNotFoundException(id));
        if (item.getOwner().getId() == userId) {
            itemRepository.deleteById(id);
        } else {
            throw new ItemNotFoundException("Item id = " + id + " with user id = " + userId + " not found");
        }
    }

    @Override
    public Comment createComment(CommentDto commentDto, Long itemId, Long userId) {
        Comment comment = CommentMapper.toComment(commentDto);

        if (itemRepository.wasBookedByUser(itemId, userId)) {
            comment.setItem(entityManager.getReference(Item.class, itemId));
            comment.setAuthor(entityManager.getReference(User.class, userId));
            return commentRepository.save(comment);
        } else {
            throw new ItemWasNotBookedByUserException();
        }
    }

    private void setDataFromDb(List<Item> items, boolean setLastAndNextBooking) {
        Map<Item, List<Comment>> allComments = commentRepository.findByItemIn(items)
                .stream()
                .collect(Collectors.groupingBy(Comment::getItem, Collectors.toList()));

        Map<Item, List<Booking>> allBookings = bookingRepository.findByItemIn(items)
                .stream()
                .collect(Collectors.groupingBy(Booking::getItem, Collectors.toList()));

        for (Item item : items) {
            item.setComments(allComments.getOrDefault(item, Collections.emptyList()));
            item.setBookings(allBookings.getOrDefault(item, Collections.emptyList()));
            if (setLastAndNextBooking) {
                setLastAndNextBooking(item);
            }
        }
    }

    private void setLastAndNextBooking(Item item) {
        LocalDateTime now = LocalDateTime.now();

        List<Booking> bookings = item.getBookings();
        Booking lastBooking = null;
        Booking nextBooking = null;
        if (bookings != null) {
            lastBooking = bookings
                    .stream()
                    .filter(b -> b.getStatus().equals(BookingStatus.APPROVED))
                    .filter(b -> b.getStart().isBefore(now) || b.getStart().equals(now))
                    .max(Comparator.comparing(Booking::getStart))
                    .orElse(null);

            nextBooking = bookings
                    .stream()
                    .filter(b -> b.getStatus().equals(BookingStatus.APPROVED))
                    .filter(b -> b.getStart().isAfter(now))
                    .min(Comparator.comparing(Booking::getStart))
                    .orElse(null);
        }

        item.setLastBooking(lastBooking);
        item.setNextBooking(nextBooking);
    }
}