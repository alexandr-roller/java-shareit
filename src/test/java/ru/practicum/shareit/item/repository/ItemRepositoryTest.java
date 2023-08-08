package ru.practicum.shareit.item.repository;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.PageRequest;
import ru.practicum.shareit.booking.entity.Booking;
import ru.practicum.shareit.booking.repository.BookingRepository;
import ru.practicum.shareit.booking.util.BookingStatus;
import ru.practicum.shareit.common.CustomPageRequest;
import ru.practicum.shareit.item.entity.Item;
import ru.practicum.shareit.user.entity.User;
import ru.practicum.shareit.user.repository.UserRepository;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
class ItemRepositoryTest {
    @Autowired
    private ItemRepository itemRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private BookingRepository bookingRepository;
    private User user;
    private Item item;

    @BeforeEach
    void setUp() {
        user = userRepository.save(User.userBuilder().name("User").email("user@test.ru").build());
        item = itemRepository.save(Item.itemBuilder().name("Item").description("Description").available(true).owner(user).build());
    }

    @AfterEach
    void tearDown() {
        userRepository.deleteAll();
        itemRepository.deleteAll();
        bookingRepository.deleteAll();
    }

    @Test
    void findAvailableByNameOrDescription() {
        PageRequest pageRequest = CustomPageRequest.of(0, 10);
        assertEquals(item, itemRepository.findAvailableByNameOrDescription("Item", "Item", pageRequest).get(0));
    }

    @Test
    void isBookedInPeriod() {
        LocalDateTime now = LocalDateTime.now();
        bookingRepository.save(Booking
                .bookingBuilder()
                .item(item)
                .start(now)
                .end(now.plusHours(2))
                .status(BookingStatus.APPROVED)
                .build());
        assertTrue(itemRepository.isBookedInPeriod(item.getId(), now.minusHours(1), now.plusHours(1)));
    }

    @Test
    void wasBookedByUser() {
        LocalDateTime now = LocalDateTime.now();
        bookingRepository.save(Booking
                .bookingBuilder()
                .item(item)
                .booker(user)
                .start(now)
                .end(now.plusHours(2))
                .status(BookingStatus.APPROVED)
                .build());
        assertTrue(itemRepository.wasBookedByUser(item.getId(), user.getId()));
    }
}