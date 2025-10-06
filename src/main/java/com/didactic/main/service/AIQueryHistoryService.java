package com.devrezaur.main.service;

import com.devrezaur.main.model.AIQueryHistory;
import com.devrezaur.main.model.User;
import com.devrezaur.main.repository.AIQueryHistoryRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AIQueryHistoryService {
    @Autowired
    private AIQueryHistoryRepo aiQueryHistoryRepository;

    @Transactional
    public AIQueryHistory saveAIQueryHistory(AIQueryHistory history) {
        return aiQueryHistoryRepository.save(history);
    }

    public List<AIQueryHistory> getQueryHistoryForUser(User user) {
        return aiQueryHistoryRepository.findByUser(user);
    }
}