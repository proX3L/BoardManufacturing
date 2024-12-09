package com.boardmanufacturing.repository;

import com.boardmanufacturing.dto.BoardDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BoardRepository extends JpaRepository<BoardDto, Long> {
}
