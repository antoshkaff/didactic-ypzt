package com.devrezaur.main.repository;

import com.devrezaur.main.model.AIQueryHistory;
import com.devrezaur.main.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AIQueryHistoryRepo extends JpaRepository<AIQueryHistory, Long> {
    List<AIQueryHistory> findByUser(User user);
}