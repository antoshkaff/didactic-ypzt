package com.devrezaur.main.repository;

import com.devrezaur.main.model.ResultPerQuestion;
import com.devrezaur.main.model.User;
import com.devrezaur.main.model.Topic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ResultPerQuestionRepo extends JpaRepository<ResultPerQuestion, Long> {
    List<ResultPerQuestion> findByUser(User user);
    List<ResultPerQuestion> findByUserAndQuestion_Topic(User user, Topic topic);
}