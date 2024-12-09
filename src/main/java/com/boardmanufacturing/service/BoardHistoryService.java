package com.boardmanufacturing.service;

import com.boardmanufacturing.dto.BoardDto;
import com.boardmanufacturing.dto.BoardHistoryDto;
import com.boardmanufacturing.dto.BoardStatus;
import com.boardmanufacturing.repository.BoardHistoryRepository;
import com.boardmanufacturing.repository.BoardRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;


@Service
public class BoardHistoryService {
    BoardRepository boardRepository;
    BoardHistoryRepository repository;

    public BoardHistoryService(BoardHistoryRepository repository, BoardRepository boardRepository) {
        this.repository = repository;
        this.boardRepository = boardRepository;
    }

    public List<BoardHistoryDto> getHistoryById(Long boardId) {
        return repository.findByBoard(boardId).stream().toList();
    }

    public BoardHistoryDto saveHistory(BoardDto board, BoardStatus boardStatus) {
        BoardHistoryDto historyDto = new BoardHistoryDto();
        historyDto.setBoard(board.getId());
        historyDto.setStatus(boardStatus);
        historyDto.setTimestamp(LocalDateTime.now());

        return repository.save(historyDto);
    }
}

