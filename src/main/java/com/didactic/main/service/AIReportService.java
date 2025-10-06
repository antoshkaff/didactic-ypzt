package com.devrezaur.main.service;

import com.devrezaur.main.model.AIReport;
import com.devrezaur.main.model.User;
import com.devrezaur.main.repository.AIReportRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AIReportService {
    @Autowired
    private AIReportRepo aiReportRepository;

    @Transactional
    public AIReport saveAIReport(AIReport report) {
        return aiReportRepository.save(report);
    }

    public List<AIReport> getReportsForUser(User user) {
        return aiReportRepository.findByUser(user);
    }
}