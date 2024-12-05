package com.test.boardmanufacturing.controller;

import com.test.boardmanufacturing.dto.BoardDto;
import com.test.boardmanufacturing.dto.ProcessHistoryDto;
import com.test.boardmanufacturing.entity.BoardEntity;
import com.test.boardmanufacturing.entity.WorkflowStep;
import com.test.boardmanufacturing.service.BoardService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.NotBlank;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/boards")
@Tag(name = "Board API", description = "Управление платами")
public class BoardController {
    private final BoardService service;

    public BoardController(BoardService service) {
        this.service = service;
    }

    @PostMapping("")
    public ResponseEntity<BoardDto> registerBoard(@RequestParam String name) {
        BoardDto boardDto = new BoardDto();
        boardDto.setName(name);
        return ResponseEntity.ok(boardDto);
    }

    @GetMapping("/{id}/history")
    public ResponseEntity<List<ProcessHistoryDto>> getHistory(@PathVariable Long id) {
        List<ProcessHistoryDto> history = service.getHistory(id);
        return ResponseEntity.ok(history);
    }
}

