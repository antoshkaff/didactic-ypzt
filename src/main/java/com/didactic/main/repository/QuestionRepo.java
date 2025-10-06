package com.devrezaur.main.repository;

import com.devrezaur.main.model.Question;
import com.devrezaur.main.model.Topic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionRepo extends JpaRepository<Question, Long> {
    List<Question> findByTopic(Topic topic);
}