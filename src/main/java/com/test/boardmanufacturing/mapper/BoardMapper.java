package com.test.boardmanufacturing.mapper;

import com.test.boardmanufacturing.dto.BoardDto;
import com.test.boardmanufacturing.entity.BoardEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface BoardMapper {

    @Mapping(target = "id", ignore = true)
    BoardDto toDto(BoardEntity board);

    @Mapping(target = "id", ignore = true)
    BoardEntity toEntity(BoardDto board);
}
