package ru.practicum.shareit.booking.service;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.shareit.booking.entity.Booking;
import ru.practicum.shareit.booking.exception.BookingIsAlreadyApprovedException;
import ru.practicum.shareit.booking.exception.BookingNotFoundException;
import ru.practicum.shareit.booking.repository.BookingRepository;
import ru.practicum.shareit.booking.service.finder.BookingFinderFactory;
import ru.practicum.shareit.booking.util.BookingStatus;
import ru.practicum.shareit.item.exception.ItemNotFoundException;
import ru.practicum.shareit.item.exception.ItemUnavailableException;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.repository.ItemRepository;
import ru.practicum.shareit.user.User;
import ru.practicum.shareit.user.exceprion.UserNotFoundException;
import ru.practicum.shareit.user.repository.UserRepository;

import java.util.List;

@Service
@AllArgsConstructor
@Transactional
public class BookingServiceImpl implements BookingService {
    private final BookingRepository bookingRepository;
    private final ItemRepository itemRepository;
    private final UserRepository userRepository;
    private final BookingFinderFactory bookingFinderFactory;

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

    @Transactional(readOnly = true)
    @Override
    public Booking findById(Long bookingId, Long userId) {
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new BookingNotFoundException(bookingId));

        if (booking.getBooker().getId() != userId && booking.getItem().getOwner().getId() != userId) {
            throw new BookingNotFoundException(bookingId);
        }

        return booking;
    }

    @Transactional(readOnly = true)
    @Override
    public List<Booking> findByBookerId(Long bookerId, BookingService.BookingState state) {
        if (userRepository.existsById(bookerId)) {
            return bookingFinderFactory.getFinder(state).findByBookerId(bookerId, Sort.by("start").descending());
        } else {
            throw new UserNotFoundException(bookerId);
        }
    }

    @Transactional(readOnly = true)
    @Override
    public List<Booking> findByOwnerId(Long ownerId, BookingService.BookingState state) {
        if (userRepository.existsById(ownerId)) {
            return bookingFinderFactory.getFinder(state).findByOwnerId(ownerId, Sort.by("start").descending());
        } else {
            throw new UserNotFoundException(ownerId);
        }
    }
}