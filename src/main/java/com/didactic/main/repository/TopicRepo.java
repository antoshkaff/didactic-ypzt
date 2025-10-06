package com.devrezaur.main.repository;

import com.devrezaur.main.model.Topic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TopicRepo extends JpaRepository<Topic, Long> {
    Optional<Topic> findByTopicName(String topicName);
}