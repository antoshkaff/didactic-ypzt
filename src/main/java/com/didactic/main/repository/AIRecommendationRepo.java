package com.devrezaur.main.repository;

import com.devrezaur.main.model.AIRecommendation;
import com.devrezaur.main.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AIRecommendationRepo extends JpaRepository<AIRecommendation, Long> {
    List<AIRecommendation> findByUser(User user);
}