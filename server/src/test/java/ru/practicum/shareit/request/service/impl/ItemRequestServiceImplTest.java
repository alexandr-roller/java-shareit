package ru.practicum.shareit.request.service.impl;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.shareit.request.entity.ItemRequest;
import ru.practicum.shareit.request.service.ItemRequestServiceImpl;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@AutoConfigureTestDatabase
@Sql({"/schema.sql", "/testdata/insert-users.sql", "/testdata/insert-requests.sql"})
class ItemRequestServiceImplTest {
    private final ItemRequestServiceImpl requestService;

    @Test
    void shouldCreate() {
        ItemRequest request = ItemRequest
                .builder()
                .description("Description")
                .created(LocalDateTime.now())
                .build();
        request = requestService.create(request, 1L);
        assertThat(request).hasFieldOrPropertyWithValue("description", "Description");
        assertThat(request.getCreated()).isNotNull();
        assertThat(request.getRequester().getId()).isEqualTo(1L);
    }

    @Test
    void shouldFindByUserId() {
        List<ItemRequest> requests = requestService.findByUserId(3L);
        ItemRequest request = requests.get(0);
        assertThat(request).hasFieldOrPropertyWithValue("description", "Description1");
        assertThat(request.getCreated()).isNotNull();
        assertThat(request.getRequester().getId()).isEqualTo(3L);
    }

    @Test
    void shouldFindById() {
        ItemRequest request = requestService.findById(1L, 3L);
        assertThat(request).hasFieldOrPropertyWithValue("id", 1L);
        assertThat(request).hasFieldOrPropertyWithValue("description", "Description1");
        assertThat(request.getCreated()).isNotNull();
        assertThat(request.getRequester().getId()).isEqualTo(3L);
    }

    @Test
    void shouldFindAll() {
        assertThat(requestService.findAll(1L, 0, 10)).hasSize(3);
    }
}