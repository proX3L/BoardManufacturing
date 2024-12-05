package com.test.boardmanufacturing.entity;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import lombok.EqualsAndHashCode;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

/**
 * Entity для таблицы process_history (История процесса)
 */
@Entity
@ToString
@EqualsAndHashCode
@RequiredArgsConstructor
@Getter
@Table(name = "process_history", schema = "public")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProcessHistoryEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @ManyToOne(optional = false)
    private BoardEntity boardEntity;

    @Enumerated(EnumType.STRING)
    @Column
    WorkflowStep step;

    @Column
    LocalDateTime timestamp = LocalDateTime.now();

    public void setBoardEntity(BoardEntity boardEntity) {
        this.boardEntity = boardEntity;
    }

    public void setStep(WorkflowStep step) {
        this.step = step;
    }
}
