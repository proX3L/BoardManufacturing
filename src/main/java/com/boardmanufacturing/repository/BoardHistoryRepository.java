package com.boardmanufacturing.repository;

import com.boardmanufacturing.dto.BoardHistoryDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BoardHistoryRepository extends JpaRepository<BoardHistoryDto, Long> {
    List<BoardHistoryDto> findByBoard(Long boardId);
}
