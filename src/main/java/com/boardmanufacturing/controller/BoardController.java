package com.boardmanufacturing.controller;

import com.boardmanufacturing.dto.BoardDto;
import com.boardmanufacturing.dto.BoardStatus;
import com.boardmanufacturing.dto.BoardHistoryDto;
import com.boardmanufacturing.service.BoardHistoryService;
import com.boardmanufacturing.service.BoardService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/boards")
@Tag(name = "Контроллер для печатных плат",
        description = "Позволяет взаимодействовать с сущностью Board по id")
public class BoardController {
    private final BoardService service;
    private final BoardHistoryService historyService;

    public BoardController(BoardService service, BoardHistoryService historyService) {
        this.service = service;
        this.historyService = historyService;
    }

    @PostMapping("/create/{name}")
    @Operation(summary = "Создать информацию о плате, указав имя",
            description = "Создает объект BoardDto")
    public ResponseEntity<BoardDto> registerBoard(@PathVariable String name) {
        return ResponseEntity.ok(service.registerNewBoard(name));
    }

    @PutMapping("/update/{id}/{status}")
    @Operation(summary = "Обновить статус платы по id",
            description = "Обновляет статус BoardDto по id")
    public ResponseEntity<BoardDto> updateBoardStep(@PathVariable("id") Long id, @PathVariable("status") BoardStatus status) {
         return ResponseEntity.ok(service.updateBoardStatus(id, status));
    }

    @GetMapping("/history/{id}")
    @Operation(summary = "Получить информацию о истории изменения статусов по id",
            description = "Получает объект BoardHistoryDto по id")
    public ResponseEntity<List<BoardHistoryDto>> getHistory(@PathVariable Long id) {
        return ResponseEntity.ok(historyService.getHistoryById(id));
    }
}

