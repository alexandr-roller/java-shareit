package ru.practicum.shareit.booking.service;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.shareit.booking.entity.Booking;
import ru.practicum.shareit.booking.exception.BookingIsAlreadyApprovedException;
import ru.practicum.shareit.booking.exception.BookingNotFoundException;
import ru.practicum.shareit.booking.repository.BookingRepository;
import ru.practicum.shareit.booking.service.finder.BookingFinderFactory;
import ru.practicum.shareit.booking.util.BookingStatus;
import ru.practicum.shareit.common.CustomPageRequest;
import ru.practicum.shareit.item.exception.ItemNotFoundException;
import ru.practicum.shareit.item.exception.ItemUnavailableException;
import ru.practicum.shareit.item.entity.Item;
import ru.practicum.shareit.item.repository.ItemRepository;
import ru.practicum.shareit.user.entity.User;
import ru.practicum.shareit.user.exception.UserNotFoundException;
import ru.practicum.shareit.user.repository.UserRepository;

import java.util.List;
import java.util.Objects;

@Service
@AllArgsConstructor
@Transactional(readOnly = true)
public class BookingServiceImpl implements BookingService {
    private final BookingRepository bookingRepository;
    private final ItemRepository itemRepository;
    private final UserRepository userRepository;
    private final BookingFinderFactory bookingFinderFactory;

    @Transactional
    @Override
    public Booking create(Booking booking, Long itemId, Long userId) {
        Item item = itemRepository.findById(itemId).orElseThrow(() -> new ItemNotFoundException(itemId));
        User user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException(userId));

        if (!item.getAvailable()) {
            throw new ItemUnavailableException();
        }

        boolean isBooked = itemRepository.isBookedInPeriod(item.getId(), booking.getStart(), booking.getEnd());

        if (isBooked || userId.equals(item.getOwner().getId())) {
            throw new ItemNotFoundException("Available item not found");
        }

        booking.setItem(item);
        booking.setBooker(user);

        return bookingRepository.save(booking);
    }

    @Transactional
    @Override
    public Booking approve(Long bookingId, Long ownerId, boolean approve) {
        Booking booking = bookingRepository.findByIdAndOwnerId(bookingId, ownerId)
                .orElseThrow(() -> new BookingNotFoundException(bookingId));

        if (booking.getStatus() != BookingStatus.WAITING) {
            throw new BookingIsAlreadyApprovedException();
        }

        booking.setStatus(approve ? BookingStatus.APPROVED : BookingStatus.REJECTED);
        return bookingRepository.save(booking);
    }

    @Override
    public Booking findById(Long bookingId, Long userId) {
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new BookingNotFoundException(bookingId));

        if (!Objects.equals(booking.getBooker().getId(), userId) && !Objects.equals(booking.getItem().getOwner().getId(), userId)) {
            throw new BookingNotFoundException(bookingId);
        }

        return booking;
    }

    @Override
    public List<Booking> findByBookerId(Long bookerId, BookingService.BookingState state, Integer from, Integer size) {
        if (userRepository.existsById(bookerId)) {
            PageRequest pageRequest = CustomPageRequest.of(from, size, Sort.by("start").descending());
            return bookingFinderFactory.getFinder(state).findByBookerId(bookerId, pageRequest);
        } else {
            throw new UserNotFoundException(bookerId);
        }
    }

    @Override
    public List<Booking> findByOwnerId(Long ownerId, BookingService.BookingState state, Integer from, Integer size) {
        if (userRepository.existsById(ownerId)) {
            PageRequest pageRequest = CustomPageRequest.of(from, size, Sort.by("start").descending());
            return bookingFinderFactory.getFinder(state).findByOwnerId(ownerId, pageRequest);
        } else {
            throw new UserNotFoundException(ownerId);
        }
    }
}