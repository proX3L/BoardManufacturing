package com.test.boardmanufacturing.mapper;
import com.test.boardmanufacturing.dto.ProcessHistoryDto;
import com.test.boardmanufacturing.entity.ProcessHistoryEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ProcessHistoryMapper {

    @Mapping(target = "id", ignore = true)
    ProcessHistoryDto toDto(ProcessHistoryDto processHistory);

    @Mapping(target = "id", ignore = true)
    ProcessHistoryEntity toEntity(ProcessHistoryDto processHistory);
}
