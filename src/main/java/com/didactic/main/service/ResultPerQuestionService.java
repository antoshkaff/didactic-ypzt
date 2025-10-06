package com.devrezaur.main.service;

import com.devrezaur.main.model.ResultPerQuestion;
import com.devrezaur.main.model.User;
import com.devrezaur.main.model.Topic;
import com.devrezaur.main.repository.ResultPerQuestionRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ResultPerQuestionService {

    @Autowired
    private ResultPerQuestionRepo resultPerQuestionRepository;

    @Transactional
    public ResultPerQuestion saveResultPerQuestion(ResultPerQuestion result) {
        return resultPerQuestionRepository.save(result);
    }
    
    @Transactional
    public List<ResultPerQuestion> saveAll(List<ResultPerQuestion> results) {
        return resultPerQuestionRepository.saveAll(results);
    }

    public List<ResultPerQuestion> getResultsByUser(User user) {
        return resultPerQuestionRepository.findByUser(user);
    }

    public List<ResultPerQuestion> getResultsByUserAndTopic(User user, Topic topic) {
        return resultPerQuestionRepository.findByUserAndQuestion_Topic(user, topic);
    }
}