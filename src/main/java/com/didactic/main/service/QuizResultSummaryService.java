package com.devrezaur.main.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;

import com.devrezaur.main.model.QuizResultSummary;
import com.devrezaur.main.repository.QuizResultSummaryRepo;

@Service
public class QuizResultSummaryService {
	@Autowired
	private QuizResultSummaryRepo quizResultSummaryRepository;
	
	@Transactional
	public void saveQuizResultSummary(QuizResultSummary quizResultSummary) {
        quizResultSummaryRepository.save(quizResultSummary);
    }

    public List<QuizResultSummary> getScoreboard() {
        return quizResultSummaryRepository.findAll(Sort.by(Sort.Direction.DESC, "totalCorrect"));
    }
}