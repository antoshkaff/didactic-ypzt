package com.devrezaur.main.service;

import com.devrezaur.main.model.Question;
import com.devrezaur.main.model.Topic;
import com.devrezaur.main.repository.QuestionRepo;
import com.devrezaur.main.repository.TopicRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class QuestionService {

    @Autowired
    private QuestionRepo questionRepository;

    @Autowired
    private TopicRepo topicRepository;

    public List<Question> getAllQuestions() {
        return questionRepository.findAll();
    }

    public List<Question> getQuestionsByTopic(String topicName) {
        Optional<Topic> topicOpt = topicRepository.findByTopicName(topicName);
        return topicOpt.map(questionRepository::findByTopic).orElseGet(Collections::emptyList);
    }

    public void saveQuestion(Question question) {
        questionRepository.save(question);
    }

    public void saveAllQuestions(List<Question> questions) {
        questionRepository.saveAll(questions);
    }

    public List<Question> selectRandomQuestions(List<Topic> topics, int limit) {
        List<Question> picked = new ArrayList<>(limit);
        Set<Long> seenIds = new HashSet<>();

        for (Topic t : topics) {
            if (picked.size() >= limit) break;

            List<Question> byTopic = questionRepository.findByTopic(t);
            if (byTopic == null || byTopic.isEmpty()) continue;

            Collections.shuffle(byTopic);
            for (Question q : byTopic) {
                Long id = q.getQuestionId();
                if (id != null && seenIds.add(id)) {
                    picked.add(q);
                    break; 
                }
            }
        }


        if (picked.size() < limit) {
            List<Question> all = new ArrayList<>(questionRepository.findAll());
           
            all.removeIf(q -> q.getQuestionId() != null && seenIds.contains(q.getQuestionId()));
            Collections.shuffle(all);

            for (Question q : all) {
                if (picked.size() >= limit) break;
                Long id = q.getQuestionId();
                if (id == null || seenIds.add(id)) {
                    picked.add(q);
                }
            }
        }

        Collections.shuffle(picked);
        return picked;
    }
}
