package com.devrezaur.main.service;

import com.devrezaur.main.model.Topic;
import com.devrezaur.main.repository.TopicRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TopicService {

    @Autowired
    private TopicRepo topicRepository;

    public List<Topic> getAllTopics() {
        return topicRepository.findAll();
    }

    public Optional<Topic> findTopicByName(String topicName) {
        return topicRepository.findByTopicName(topicName);
    }

    public Topic saveTopic(Topic topic) {
        return topicRepository.save(topic);
    }
}