package com.test.boardmanufacturing.dto;

import com.test.boardmanufacturing.entity.BoardEntity;
import com.test.boardmanufacturing.entity.ProcessHistoryEntity;
import com.test.boardmanufacturing.entity.WorkflowStep;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * DTO для сущности {@link ProcessHistoryEntity}
 */
@Data
@EqualsAndHashCode
@NoArgsConstructor
public class ProcessHistoryDto implements Serializable {
    private Long id;
    private BoardEntity boardEntity;
    private WorkflowStep step;
    private LocalDateTime timestamp = LocalDateTime.now();

    public ProcessHistoryDto(Long id, BoardEntity boardEntity, WorkflowStep step, LocalDateTime timestamp) {
        this.id = id;
        this.boardEntity = boardEntity;
        this.step = step;
        this.timestamp = timestamp;
    }
}
