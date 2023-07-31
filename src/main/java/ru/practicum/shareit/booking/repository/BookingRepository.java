package ru.practicum.shareit.booking.repository;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.practicum.shareit.booking.entity.Booking;
import ru.practicum.shareit.booking.util.BookingStatus;
import ru.practicum.shareit.item.model.Item;

import java.util.List;
import java.util.Optional;

public interface BookingRepository extends JpaRepository<Booking, Long> {
    List<Booking> findByItemId(Long itemId);

    List<Booking> findByItemIn(List<Item> item);

    @Query("select b " +
            " from Booking b " +
            " join fetch b.item i " +
            "where b.id = :id " +
            "  and i.owner.id = :ownerId")
    Optional<Booking> findByIdAndOwnerId(Long id, Long ownerId);

    List<Booking> findByBookerId(Long bookerId, Sort sort);

    @Query("select b " +
            " from Booking b " +
            "where b.start <= CURRENT_TIMESTAMP " +
            "  and b.end >= CURRENT_TIMESTAMP " +
            "  and b.booker.id = :bookerId " +
            "order by :sort")
    List<Booking> findByBookerIdCurrent(Long bookerId, Sort sort);

    @Query("select b " +
            " from Booking b " +
            "where b.end < CURRENT_TIMESTAMP " +
            "  and b.booker.id = :bookerId " +
            "order by :sort")
    List<Booking> findByBookerIdPast(Long bookerId, Sort sort);

    @Query("select b " +
            " from Booking b " +
            "where b.start >= CURRENT_TIMESTAMP " +
            "  and b.booker.id = :bookerId " +
            "order by :sort")
    List<Booking> findByBookerIdFuture(Long bookerId, Sort sort);

    @Query("select b " +
            " from Booking b " +
            "where b.booker.id = :bookerId " +
            "  and b.status = :status")
    List<Booking> findByBookerIdAndStatus(Long bookerId, BookingStatus status);

    @Query("select b " +
            " from Booking b " +
            " join fetch b.item i " +
            "where i.owner.id = :ownerId " +
            "order by :sort")
    List<Booking> findByOwnerId(Long ownerId, Sort sort);

    @Query("select b " +
            " from Booking b " +
            " join fetch b.item i " +
            "where i.owner.id = :ownerId " +
            "  and b.start <= CURRENT_TIMESTAMP " +
            "  and b.end >= CURRENT_TIMESTAMP " +
            "order by :sort")
    List<Booking> findByOwnerIdCurrent(Long ownerId, Sort sort);

    @Query("select b " +
            " from Booking b " +
            " join fetch b.item i " +
            "where i.owner.id = :ownerId " +
            "  and b.end < CURRENT_TIMESTAMP " +
            "order by :sort")
    List<Booking> findByOwnerIdPast(Long ownerId, Sort sort);

    @Query("select b " +
            " from Booking b " +
            " join fetch b.item i " +
            "where i.owner.id = :ownerId " +
            "  and b.start >= CURRENT_TIMESTAMP " +
            "order by :sort")
    List<Booking> findByOwnerIdFuture(Long ownerId, Sort sort);

    @Query("select b " +
            " from Booking b " +
            " join fetch b.item i " +
            "where i.owner.id = :ownerId " +
            "  and b.status = :status " +
            "order by :sort")
    List<Booking> findByOwnerIdAndStatus(Long ownerId, BookingStatus status, Sort sort);
}