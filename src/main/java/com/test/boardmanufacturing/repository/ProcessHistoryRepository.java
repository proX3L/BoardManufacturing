package com.test.boardmanufacturing.repository;

import com.test.boardmanufacturing.dto.ProcessHistoryDto;
import com.test.boardmanufacturing.entity.ProcessHistoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProcessHistoryRepository extends JpaRepository<ProcessHistoryEntity, Long> {

//    @Query("SELECT new com.test.boardmanufacturing.dto.ProcessHistoryDto(p.id, p.boardEntity, p.step, p.timestamp) " +
//            "FROM ProcessHistoryEntity p WHERE p.id = :id")
//    List<ProcessHistoryDto> findById(@Param("id") Long id).;
    List<ProcessHistoryDto> getHistoryById(@Param("id") Long id);

    List<ProcessHistoryDto> findByIdCustom(Long boardId);
}
