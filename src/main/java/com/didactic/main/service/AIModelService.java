package com.devrezaur.main.service;

import com.devrezaur.main.model.AIModel;
import com.devrezaur.main.repository.AIModelRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class AIModelService {
    @Autowired
    private AIModelRepo aiModelRepository;

    @Transactional
    public AIModel saveAIModel(AIModel model) {
        return aiModelRepository.save(model);
    }

    public List<AIModel> getAllAIModels() {
        return aiModelRepository.findAll();
    }

    public Optional<AIModel> getAIModelById(Long id) {
        return aiModelRepository.findById(id);
    }
}