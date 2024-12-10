package com.boardmanufacturing.controller;

import com.boardmanufacturing.entity.BoardEntity;
import com.boardmanufacturing.entity.BoardStatus;
import com.boardmanufacturing.entity.BoardHistoryEntity;
import com.boardmanufacturing.service.BoardHistoryService;
import com.boardmanufacturing.service.BoardService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;


@DisplayName("Тест контроллера BoardController")
class BoardControllerTest {

    @Mock
    private BoardService boardService;

    @Mock
    private BoardHistoryService boardHistoryService;

    @InjectMocks
    private BoardController controller;

    public BoardControllerTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Регистрация новой платы, позитивный тест")
    void registerBoard_success() {
        String boardName = "Test Board";
        BoardEntity boardEntity = new BoardEntity();
        boardEntity.setName(boardName);
        when(boardService.registerNewBoard(boardName)).thenReturn(boardEntity);

        ResponseEntity<BoardEntity> response = controller.registerBoard(boardName);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(boardName, response.getBody().getName());
        verify(boardService, times(1)).registerNewBoard(boardName);
    }

    @Test
    @DisplayName("Регистрация новой платы, негативный тест")
    void registerBoard_failure() {
        String boardName = "Test Board";
        when(boardService.registerNewBoard(boardName)).thenThrow(new RuntimeException("Error creating board"));

                assertThrows(RuntimeException.class, () -> controller.registerBoard(boardName));
        verify(boardService, times(1)).registerNewBoard(boardName);
    }

    @Test
    @DisplayName("Обновление записи о плате, позитивный тест")
    void updateBoardStep_success() {
        Long boardId = 1L;
        BoardStatus status = BoardStatus.INSTALLATION;
        BoardEntity boardEntity = new BoardEntity();
        boardEntity.setId(boardId);
        boardEntity.setBoardStatus(status);
        when(boardService.updateBoardStatus(boardId, status)).thenReturn(boardEntity);

        ResponseEntity<BoardEntity> response = controller.updateBoardStep(boardId, status);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(status, response.getBody().getBoardStatus());
        verify(boardService, times(1)).updateBoardStatus(boardId, status);
    }

    @Test
    @DisplayName("Обновление записи о плате, негативный тест")
    void updateBoardStep_failure() {
        Long boardId = 1L;
        BoardStatus status = BoardStatus.REGISTRATION;
        when(boardService.updateBoardStatus(boardId, status)).thenThrow(new RuntimeException("Error updating board"));

        assertThrows(RuntimeException.class, () -> controller.updateBoardStep(boardId, status));
        verify(boardService, times(1)).updateBoardStatus(boardId, status);
    }

    @Test
    @DisplayName("Получение истории статусов платы, позитивный тест")
    void getHistory_success() {
        Long boardId = 1L;
        BoardHistoryEntity history1 = new BoardHistoryEntity();
        BoardHistoryEntity history2 = new BoardHistoryEntity();
        List<BoardHistoryEntity> historyList = Arrays.asList(history1, history2);
        when(boardHistoryService.getHistoryById(boardId)).thenReturn(historyList);

        ResponseEntity<List<BoardHistoryEntity>> response = controller.getHistory(boardId);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(2, response.getBody().size());
        verify(boardHistoryService, times(1)).getHistoryById(boardId);
    }

    @Test
    @DisplayName("Получение истории статусов платы, негативный тест")
    void getHistory_failure() {
        Long boardId = 1L;
        when(boardHistoryService.getHistoryById(boardId)).thenReturn(Collections.emptyList());

        ResponseEntity<List<BoardHistoryEntity>> response = controller.getHistory(boardId);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(0, response.getBody().size());
        verify(boardHistoryService, times(1)).getHistoryById(boardId);
    }
}
