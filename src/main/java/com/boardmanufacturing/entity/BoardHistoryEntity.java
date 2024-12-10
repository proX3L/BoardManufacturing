package com.boardmanufacturing.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * Entity для таблиц board_history
 */
@Entity
@Setter
@Getter
@Table(name = "board_history", schema = "public")
@RequiredArgsConstructor
public class BoardHistoryEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column
    private BoardStatus status;

    @Column
    private LocalDateTime timestamp;

    @Column
    private Long board;
}