package com.boardmanufacturing.repository;

import com.boardmanufacturing.entity.BoardHistoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BoardHistoryRepository extends JpaRepository<BoardHistoryEntity, Long> {
    List<BoardHistoryEntity> findByBoard(Long boardId);
}
