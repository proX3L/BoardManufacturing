package com.test.boardmanufacturing.controller;

import com.test.boardmanufacturing.dto.ProcessHistoryDto;
import com.test.boardmanufacturing.entity.ProcessHistoryEntity;
import com.test.boardmanufacturing.service.ProcessHistoryService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/process-history")
public class ProcessHistoryController {

    private final ProcessHistoryService processHistoryService;

    public ProcessHistoryController(ProcessHistoryService processHistoryService) {
        this.processHistoryService = processHistoryService;
    }

    /**
     * Получение истории процесса по ID.
     *
     * @param id Идентификатор процесса
     * @return Список объектов ProcessHistoryDto
     */
    @GetMapping("/{id}")
    public List<ProcessHistoryEntity> getHistoryById(@PathVariable Long id) {
        return processHistoryService.getHistoryById(id);
    }

    @PostMapping
    public ProcessHistoryEntity addProcessHistory() {
        return processHistoryService.addProcessHistory();
    }
}
