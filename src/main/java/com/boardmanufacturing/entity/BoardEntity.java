package com.boardmanufacturing.entity;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Column;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * Entity для таблиц board
 */
@Entity
@Data
@Table(name = "board", schema = "public")

public class BoardEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "board_id")
    private Long id;

    @Column
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private BoardStatus boardStatus;

    @OneToMany(mappedBy = "board")
    private List<BoardHistoryEntity> boardHistoryEntityList;
}
