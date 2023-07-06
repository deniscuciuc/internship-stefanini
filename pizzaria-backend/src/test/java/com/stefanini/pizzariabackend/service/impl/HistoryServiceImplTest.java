package com.stefanini.pizzariabackend.service.impl;

import com.stefanini.pizzariabackend.domain.History;
import com.stefanini.pizzariabackend.domain.User;
import com.stefanini.pizzariabackend.repo.HistoryRepository;
import com.stefanini.pizzariabackend.repo.UserRepository;
import com.stefanini.pizzariabackend.service.HistoryService;
import com.stefanini.pizzariabackend.service.impl.exception.NotFoundException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;


class HistoryServiceImplTest {

    @Mock
    private HistoryRepository historyRepository;

    @Mock
    private UserRepository userRepository;

    private AutoCloseable autoCloseable;
    private HistoryService underTest;
    private ArgumentCaptor<History> historyArgumentCaptor;

    @BeforeEach
    void setUp() {
        autoCloseable = MockitoAnnotations.openMocks(this);
        underTest = new HistoryServiceImpl(historyRepository, userRepository);
        historyArgumentCaptor = ArgumentCaptor.forClass(History.class);
    }

    @AfterEach
    void tearDown() throws Exception {
        autoCloseable.close();
    }

    /**
     * Unit test for {@link HistoryService#createHistory(History) createHistory} method
     */
    @Test
    void shouldCreateHistoryAndToNotChangeAnythingBeforeSavingInDatabase() {
        History history = new History(
                "Order was finished",
                LocalDate.now()
        );

        underTest.createHistory(history);

        verify(historyRepository).save(historyArgumentCaptor.capture());

        History capturedHistory = historyArgumentCaptor.getValue();
        assertThat(capturedHistory).isEqualTo(history);
    }

    /**
     * Unit test for {@link HistoryService#getAllHistories() getAllHistories} method
     */
    @Test
    void shouldReturnAllHistories() {
        underTest.getAllHistories();
        verify(historyRepository).findAll();
    }

    /**
     * Unit test for {@link HistoryService#getPaginatedHistories(Long, int, int) getPaginatedHistories} method
     */
    @Test
    void shouldThrowNotFoundExceptionBecauseUserWithSuchIdNotExists() {
        Long fakeUserId = (long) 5;
        int page = 1, pageSize = 5;

        assertThrows(NotFoundException.class, () -> underTest.getPaginatedHistories(fakeUserId, page, pageSize));
    }

    /**
     * Unit test for {@link HistoryService#getPaginatedHistories(Long, int, int) getPaginatedHistories} method
     */
    @Test
    void shouldReturnCorrectNumberAndSizeOfHistoriesOfUser() {
        User user = new User(
                (long) 3,
                "denis@gmail.com",
                "killer",
                "677123"
        );

        int page = 1, pageSize = 2;

        given(userRepository.existsById(user.getId())).willReturn(true);
        given(userRepository.findById(user.getId())).willReturn(Optional.of(user));

        underTest.getPaginatedHistories(user.getId(), page, pageSize);

        ArgumentCaptor<Pageable> pageableArgumentCaptor = ArgumentCaptor.forClass(Pageable.class);
        ArgumentCaptor<User> userArgumentCaptor = ArgumentCaptor.forClass(User.class);

        verify(historyRepository).findHistoriesByUser(userArgumentCaptor.capture(), pageableArgumentCaptor.capture());

        User capturedUser = userArgumentCaptor.getValue();
        Pageable capturedPaging = pageableArgumentCaptor.getValue();

        assertThat(capturedUser).isEqualTo(user);
        assertThat(capturedPaging.getPageNumber()).isEqualTo(page);
        assertThat(capturedPaging.getPageSize()).isEqualTo(pageSize);
    }
}