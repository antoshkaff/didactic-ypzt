package com.devrezaur.main.repository;

import com.devrezaur.main.model.AIReport;
import com.devrezaur.main.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AIReportRepo extends JpaRepository<AIReport, Long> {
    List<AIReport> findByUser(User user);
}