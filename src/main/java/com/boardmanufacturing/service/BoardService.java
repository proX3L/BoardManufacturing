package com.boardmanufacturing.service;

import com.boardmanufacturing.dto.BoardDto;
import com.boardmanufacturing.dto.BoardHistoryDto;
import com.boardmanufacturing.dto.BoardStatus;
import com.boardmanufacturing.repository.BoardHistoryRepository;
import com.boardmanufacturing.repository.BoardRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;


@Service
public class BoardService {
    private final BoardRepository boardRepository;
    private final BoardHistoryService boardHistoryService;
    private static final BoardStatus REGISTRATION = BoardStatus.REGISTRATION;
    private final BoardHistoryRepository boardHistoryRepository;

    public BoardService(BoardRepository boardRepository, BoardHistoryService boardHistoryService,
                        BoardHistoryRepository boardHistoryRepository) {
        this.boardRepository = boardRepository;
        this.boardHistoryService = boardHistoryService;
        this.boardHistoryRepository = boardHistoryRepository;
    }


    /**
     * Регистриуем плату, присваивая статус REGISTRATION, так же формируем запись в board_history
     */
    public BoardDto registerNewBoard(String name) {
        BoardDto boardDto = new BoardDto();
        boardDto.setName(name);

        // напрямую не трогаем енам, записываем его копию со значением
        boardDto.setBoardStatus(REGISTRATION);
        boardDto = boardRepository.save(boardDto);

        BoardHistoryDto history = new BoardHistoryDto();
        history.setStatus(REGISTRATION);
        history.setBoard(boardDto.getId());
        history.setTimestamp(LocalDateTime.now());

        boardHistoryRepository.save(history);

        return boardDto;
    }

    /**
     * Обновляем статус платы в таблице board и добавляя запись в board_history
     */
    public BoardDto updateBoardStatus(Long id, BoardStatus newStatus) {
        // смотрим старый статус
        BoardDto boardDto = boardRepository.getReferenceById(id);
        BoardStatus oldStatus = boardDto.getBoardStatus();

        // если новый статус корректно сменяет старый, то перезаписываем дто. Если нет - кидаем ексепшн
        if (verifyCorrectStatus(oldStatus, newStatus)) {
            boardDto.setBoardStatus(newStatus);

            // сохраняем в историю айди платы и статус
            // Сохраняем изменения в истории статусов
            boardHistoryService.saveHistory(boardDto, newStatus);

            return boardRepository.save(boardDto);
        } else {
            throw new IllegalStateException("Недопустимый переход статуса");
        }
    }

    /**
     * Проверяем корректно ли меняется статус
     */
    private Boolean verifyCorrectStatus(BoardStatus oldStatus, BoardStatus newStatus) {
        if (oldStatus == BoardStatus.REGISTRATION && newStatus == BoardStatus.INSTALLATION)
            return true;
        if (oldStatus == BoardStatus.INSTALLATION && newStatus == BoardStatus.QUALITY_CONTROL)
            return true;
        if (oldStatus == BoardStatus.QUALITY_CONTROL && newStatus == BoardStatus.REPAIR)
            return true;
        if (oldStatus == BoardStatus.REPAIR && newStatus == BoardStatus.QUALITY_CONTROL)
            return true;
        if (oldStatus == BoardStatus.QUALITY_CONTROL && newStatus == BoardStatus.PACKAGING)
            return true;

        return false;
    }
}


