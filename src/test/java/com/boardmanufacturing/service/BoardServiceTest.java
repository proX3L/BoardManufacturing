package com.boardmanufacturing.service;

import com.boardmanufacturing.entity.BoardEntity;
import com.boardmanufacturing.entity.BoardHistoryEntity;
import com.boardmanufacturing.entity.BoardStatus;
import com.boardmanufacturing.repository.BoardHistoryRepository;
import com.boardmanufacturing.repository.BoardRepository;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@FieldDefaults(level = AccessLevel.PRIVATE)
@ExtendWith(MockitoExtension.class)
@DisplayName("Тест класса BoardService")
class BoardServiceTest {

    @Mock
    BoardRepository boardRepository;

    @Mock
    BoardHistoryRepository boardHistoryRepository;

    @Mock
    BoardHistoryService boardHistoryService;

    @InjectMocks
    BoardService boardService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("добавление платы, позитивный тест")
    void RegisterNewBoardPositiveTest() {
        String boardName = "TestBoard";
        BoardEntity savedBoard = new BoardEntity();
        savedBoard.setId(1L);
        savedBoard.setName(boardName);
        savedBoard.setBoardStatus(BoardStatus.REGISTRATION);

        when(boardRepository.save(any(BoardEntity.class))).thenReturn(savedBoard);

        BoardEntity result = boardService.registerNewBoard(boardName);

        assertNotNull(result);
        assertEquals(boardName, result.getName());
        assertEquals(BoardStatus.REGISTRATION, result.getBoardStatus());
        verify(boardRepository, times(1)).save(any(BoardEntity.class));
        verify(boardHistoryRepository, times(1)).save(any(BoardHistoryEntity.class));
    }

    @Test
    @DisplayName("добавление платы, негативный тест")
    void RegisterNewBoardNegativeTest() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                boardService.registerNewBoard(null));

        assertEquals("Не допустимое имя платы", exception.getMessage());
        verify(boardRepository, never()).save(any(BoardEntity.class));
        verify(boardHistoryRepository, never()).save(any(BoardHistoryEntity.class));
    }

    @Test
    @DisplayName("обновление статуса платы, позитивный тест")
    void UpdateBoardStatusPositiveTest() {
        Long boardId = 1L;
        BoardStatus oldStatus = BoardStatus.REGISTRATION;
        BoardStatus newStatus = BoardStatus.INSTALLATION;

        BoardEntity existingBoard = new BoardEntity();
        existingBoard.setId(boardId);
        existingBoard.setBoardStatus(oldStatus);

        when(boardRepository.getReferenceById(boardId)).thenReturn(existingBoard);
        when(boardRepository.save(any(BoardEntity.class))).thenReturn(existingBoard);

        BoardEntity result = boardService.updateBoardStatus(boardId, newStatus);

        assertNotNull(result);
        assertEquals(newStatus, result.getBoardStatus());
        verify(boardHistoryService, times(1)).saveHistory(existingBoard, newStatus);
        verify(boardRepository, times(1)).save(any(BoardEntity.class));
    }

    @Test
    @DisplayName("обновление статуса платы, некорректный переход между статусами, негативный тест")
    void UpdateBoardStatusNegativeTest() {
        Long boardId = 1L;
        BoardStatus oldStatus = BoardStatus.REGISTRATION;
        BoardStatus newStatus = BoardStatus.REPAIR;

        BoardEntity existingBoard = new BoardEntity();
        existingBoard.setId(boardId);
        existingBoard.setBoardStatus(oldStatus);

        when(boardRepository.getReferenceById(boardId)).thenReturn(existingBoard);

        IllegalStateException exception = assertThrows(IllegalStateException.class, () ->
                boardService.updateBoardStatus(boardId, newStatus));

        assertEquals("Недопустимый переход статуса", exception.getMessage());
        verify(boardHistoryService, never()).saveHistory(existingBoard, newStatus);
        verify(boardRepository, never()).save(any(BoardEntity.class));
    }

    @Test
    @DisplayName("обновление статуса платы, плата не найдена, негативный тест")
    void UpdateBoardStatusNotFaultNegativeTest() {
        Long boardId = 1L;

        when(boardRepository.getReferenceById(boardId)).thenThrow(new IllegalArgumentException("Плата не найдена"));

        assertThrows(IllegalArgumentException.class, () -> boardService.updateBoardStatus(boardId, BoardStatus.INSTALLATION));
        verify(boardHistoryService, never()).saveHistory(any(BoardEntity.class), any(BoardStatus.class));
        verify(boardRepository, never()).save(any(BoardEntity.class));
    }
}
