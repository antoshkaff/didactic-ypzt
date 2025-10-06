package com.devrezaur.main.repository;

import com.devrezaur.main.model.AIModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AIModelRepo extends JpaRepository<AIModel, Long> {
}