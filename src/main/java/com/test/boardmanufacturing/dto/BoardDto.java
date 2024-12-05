package com.test.boardmanufacturing.dto;

import com.test.boardmanufacturing.entity.BoardEntity;
import com.test.boardmanufacturing.entity.BoardStatus;
import com.test.boardmanufacturing.entity.WorkflowStep;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * DTO для сущности {@link BoardEntity}
 */

@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class BoardDto implements Serializable {
    private Long id;
    private String name;
    private WorkflowStep currentStep;
    private BoardStatus status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public void setName(String name) {
        this.name = name;
    }

    public void setCurrentStep(WorkflowStep currentStep) {
        this.currentStep = currentStep;
    }

    public void setStatus(BoardStatus status) {
        this.status = status;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}
