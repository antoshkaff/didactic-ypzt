package com.devrezaur.main.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.devrezaur.main.model.QuizResultSummary;
import org.springframework.stereotype.Repository;

@Repository
public interface QuizResultSummaryRepo extends JpaRepository<QuizResultSummary, Long> {
}