package com.devrezaur.main.service;

import com.devrezaur.main.model.AIRecommendation;
import com.devrezaur.main.model.User;
import com.devrezaur.main.repository.AIRecommendationRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AIRecommendationService {
    @Autowired
    private AIRecommendationRepo aiRecommendationRepository;

    @Transactional
    public AIRecommendation saveAIRecommendation(AIRecommendation recommendation) {
        return aiRecommendationRepository.save(recommendation);
    }

    public List<AIRecommendation> getRecommendationsForUser(User user) {
        return aiRecommendationRepository.findByUser(user);
    }
}