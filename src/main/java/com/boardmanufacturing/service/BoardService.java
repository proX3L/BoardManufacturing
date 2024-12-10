package com.boardmanufacturing.service;

import com.boardmanufacturing.entity.BoardEntity;
import com.boardmanufacturing.entity.BoardHistoryEntity;
import com.boardmanufacturing.entity.BoardStatus;
import com.boardmanufacturing.repository.BoardHistoryRepository;
import com.boardmanufacturing.repository.BoardRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class BoardService {
    BoardRepository boardRepository;
    BoardHistoryService boardHistoryService;
    BoardHistoryRepository boardHistoryRepository;

    static BoardStatus REGISTRATION = BoardStatus.REGISTRATION;

    /**
     * Регистриуем плату, присваивая статус REGISTRATION, так же формируем запись в board_history
     */
    public BoardEntity registerNewBoard(String name) {
        if(name == null || name.isEmpty()) {
            throw new IllegalArgumentException("Не допустимое имя платы");
        }

        BoardEntity boardEntity = new BoardEntity();
        boardEntity.setName(name);

        // напрямую не трогаем енам, записываем его копию со значением
        boardEntity.setBoardStatus(REGISTRATION);
        boardEntity = boardRepository.save(boardEntity);

        BoardHistoryEntity history = new BoardHistoryEntity();
        history.setStatus(REGISTRATION);
        history.setBoard(boardEntity.getId());
        history.setTimestamp(LocalDateTime.now());

        boardHistoryRepository.save(history);

        return boardEntity;
    }

    /**
     * Обновляем статус платы в таблице board и добавляя запись в board_history
     */
    public BoardEntity updateBoardStatus(Long id, BoardStatus newStatus) {
        // Смотрим старый статус
        BoardEntity boardEntity = boardRepository.getReferenceById(id);

        BoardStatus oldStatus = boardEntity.getBoardStatus();
        // Если новый статус корректно сменяет старый, то перезаписываем сущьность. В противном случае кидаем ексепшн.
        if (verifyCorrectStatus(oldStatus, newStatus)) {
            boardEntity.setBoardStatus(newStatus);

            // Создаем запись в истории статусов
            boardHistoryService.saveHistory(boardEntity, newStatus);

            return boardRepository.save(boardEntity);
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


