package com.test.boardmanufacturing.service;

import com.test.boardmanufacturing.entity.ProcessHistoryEntity;
import com.test.boardmanufacturing.entity.WorkflowStep;
import com.test.boardmanufacturing.repository.ProcessHistoryRepository;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ProcessHistoryService {

    ProcessHistoryRepository repository;

    public ProcessHistoryService(ProcessHistoryRepository repository) {
        this.repository = repository;
    }

    public List<ProcessHistoryEntity> getHistoryById(Long id) {
        return repository.findById(id).stream().toList();
    }

    public ProcessHistoryEntity addProcessHistory() {
        ProcessHistoryEntity entity = new ProcessHistoryEntity();
        entity.setStep(WorkflowStep.REGISTRATION);
        return repository.save(entity);
    }

}

