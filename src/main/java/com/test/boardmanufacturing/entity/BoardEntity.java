package com.test.boardmanufacturing.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.EqualsAndHashCode;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

/**
 * Entity для таблицы board (Печатная плата)
 */
@Entity
@ToString
@EqualsAndHashCode
@RequiredArgsConstructor
@Getter
@Table(name = "board", schema = "public")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BoardEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column
    String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "current_step")
    WorkflowStep currentStep;

    @Enumerated(EnumType.STRING)
    @Column
    BoardStatus status;

    @Column(name = "created_at")
    LocalDateTime createdAt = LocalDateTime.now();

    @Column(name = "updated_at")
    LocalDateTime updatedAt = LocalDateTime.now();


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