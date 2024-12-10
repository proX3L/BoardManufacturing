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

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.never;

@FieldDefaults(level = AccessLevel.PRIVATE)
@ExtendWith(MockitoExtension.class)
@DisplayName("Тест класса BoardService")
class BoardServiceTest {

    @Mock
    BoardRepository boardRepository;

    @Mock
    BoardHistoryRepository boardHistoryRepository;

    @InjectMocks
    BoardService boardService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("добавление записи платы, позитивный тест")
    void RegisterNewBoardPositiveTest() {
        BoardEntity savedBoard = new BoardEntity();
        savedBoard.setId(1L);
        savedBoard.setName("TestBoard");
        savedBoard.setBoardStatus(BoardStatus.REGISTRATION);

        when(boardRepository.save(any(BoardEntity.class))).thenReturn(savedBoard);
        assertNotNull(boardRepository.save(savedBoard));
        verify(boardRepository).save(savedBoard);

    }

    @Test
    @DisplayName("добавление записи платы, негативный тест")
    void RegisterNewBoardNegativeTest() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                boardService.registerNewBoard(null));

        assertEquals("Не допустимое имя платы", exception.getMessage());
        verify(boardRepository, never()).save(any(BoardEntity.class));
        verify(boardHistoryRepository, never()).save(any(BoardHistoryEntity.class));
    }

}
