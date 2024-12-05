package com.test.boardmanufacturing.service;

import com.test.boardmanufacturing.dto.BoardDto;
import com.test.boardmanufacturing.dto.ProcessHistoryDto;
import com.test.boardmanufacturing.entity.BoardEntity;
import com.test.boardmanufacturing.entity.BoardStatus;
import com.test.boardmanufacturing.entity.WorkflowStep;
import com.test.boardmanufacturing.mapper.BoardMapper;
import com.test.boardmanufacturing.mapper.ProcessHistoryMapper;
import com.test.boardmanufacturing.repository.BoardRepository;
import com.test.boardmanufacturing.repository.ProcessHistoryRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class BoardService {
    BoardRepository boardRepository;
    ProcessHistoryRepository historyRepository;
    BoardMapper boardMapper;
    ProcessHistoryMapper processHistoryMapper;


    public BoardDto registerBoard(String name) {
        BoardEntity boardEntity = new BoardEntity();
        boardEntity.setName(name);
        boardEntity.setCurrentStep(WorkflowStep.REGISTRATION);
        boardEntity.setStatus(BoardStatus.ACTIVE);

        boardEntity = boardRepository.save(boardEntity);
        return boardMapper.toDto(boardEntity);
    }

    public List<ProcessHistoryDto> getHistory(Long boardId) {
        List<ProcessHistoryDto> history = historyRepository.findByIdCustom(boardId);
        return history.stream()
                .map(processHistoryMapper::toDto)
                .toList();
    }
}


