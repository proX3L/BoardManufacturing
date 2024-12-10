package com.boardmanufacturing.service;

import com.boardmanufacturing.entity.BoardEntity;
import com.boardmanufacturing.entity.BoardHistoryEntity;
import com.boardmanufacturing.entity.BoardStatus;
import com.boardmanufacturing.repository.BoardHistoryRepository;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;


@FieldDefaults(level = AccessLevel.PRIVATE)
@ExtendWith(MockitoExtension.class)
@DisplayName("Тест класса BoardHistoryService")
class BoardHistoryServiceTest {

    @Mock
    BoardHistoryRepository repository;

    @InjectMocks
    BoardHistoryService service;

    BoardEntity boardEntity;
    BoardHistoryEntity historyEntity;

    @BeforeEach
    void setUp() {
        boardEntity = new BoardEntity();
        boardEntity.setId(1L);
        boardEntity.setName("Test Board");

        historyEntity = new BoardHistoryEntity();
        historyEntity.setBoard(boardEntity.getId());
        historyEntity.setStatus(BoardStatus.REGISTRATION);
        historyEntity.setTimestamp(LocalDateTime.now());
    }

    @Test
    @DisplayName("Получение истории платы по id, позитивный тест")
    void GetHistoryByIdPositiveTest() {
        when(repository.findByBoard(boardEntity.getId())).thenReturn(List.of(historyEntity));

        List<BoardHistoryEntity> result = service.getHistoryById(boardEntity.getId());

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(historyEntity.getStatus(), result.get(0).getStatus());
        verify(repository, times(1)).findByBoard(boardEntity.getId());
    }

    @Test
    @DisplayName("Получение истории платы, негативный тест")
    void GetHistoryByIdNegativeTest() {
        when(repository.findByBoard(boardEntity.getId())).thenReturn(List.of());

        List<BoardHistoryEntity> result = service.getHistoryById(boardEntity.getId());

        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(repository, times(1)).findByBoard(boardEntity.getId());
    }

    @Test
    @DisplayName("Создание записи статуса по boardEntity и указание статуса REGISTRATION, позитивный тест")
    void SaveHistoryPositiveTest() {
        service.saveHistory(boardEntity, BoardStatus.REGISTRATION);
        verify(repository, times(1)).save(any(BoardHistoryEntity.class));
    }

    @Test
    @DisplayName("Создание записи статуса платы по null, негативный тест")
    void SaveHistoryNegativeTest_NullBoard() {
        assertThrows(NullPointerException.class, () -> service.saveHistory(null, BoardStatus.PACKAGING));
        verify(repository, never()).save(any(BoardHistoryEntity.class));
    }

    @Test
    @DisplayName("Создание записи статуса платы по boardEntity и указание статуса null, негативный тест")
    void SaveHistoryNegativeTest_NullStatus() {
        assertThrows(NullPointerException.class, () -> service.saveHistory(boardEntity, null));
        verify(repository, never()).save(any(BoardHistoryEntity.class));
    }
}
