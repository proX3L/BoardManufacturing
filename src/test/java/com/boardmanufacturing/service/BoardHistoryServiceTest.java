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

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


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

    // Positive test for getHistoryById (valid board ID)
    @Test
    void testGetHistoryById_Success() {
        // Arrange: mock repository response
        when(repository.findByBoard(boardEntity.getId())).thenReturn(List.of(historyEntity));

        // Act: call the method
        List<BoardHistoryEntity> result = service.getHistoryById(boardEntity.getId());

        // Assert: validate result
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(historyEntity.getStatus(), result.get(0).getStatus());
        verify(repository, times(1)).findByBoard(boardEntity.getId());
    }

    // Negative test for getHistoryById (nonexistent board ID)
    @Test
    void testGetHistoryById_Failure_BoardNotFound() {
        // Arrange: mock empty response for nonexistent board
        when(repository.findByBoard(boardEntity.getId())).thenReturn(List.of());

        // Act: call the method
        List<BoardHistoryEntity> result = service.getHistoryById(boardEntity.getId());

        // Assert: validate empty result
        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(repository, times(1)).findByBoard(boardEntity.getId());
    }

    // Positive test for saveHistory (valid board and status)
    @Test
    void testSaveHistory_Success() {
        // Act: call saveHistory method
        service.saveHistory(boardEntity, BoardStatus.INSTALLATION);

        // Assert: verify save call on repository
        verify(repository, times(1)).save(any(BoardHistoryEntity.class));
    }

    // Negative test for saveHistory (null board)
    @Test
    void testSaveHistory_Failure_NullBoard() {
        assertThrows(NullPointerException.class, () -> service.saveHistory(null, BoardStatus.PACKAGING));
        verify(repository, never()).save(any(BoardHistoryEntity.class));
    }

    // Negative test for saveHistory (null status)
    @Test
    void testSaveHistory_Failure_NullStatus() {
        assertThrows(NullPointerException.class, () -> service.saveHistory(boardEntity, null));
        verify(repository, never()).save(any(BoardHistoryEntity.class));
    }
}
