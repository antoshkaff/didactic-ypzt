package com.devrezaur.main.model;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "ai_recommendation")
public class AIRecommendation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "recommendation_id")
    private Long recommendationId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "model_id", nullable = false)
    private AIModel aiModel;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "question_id")
    private Question question;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Enumerated(EnumType.STRING)
    @Column(name = "recommended_action")
    private RecommendedAction recommendedAction;

    @Column(name = "explanation", columnDefinition = "TEXT")
    private String explanation;

    @Column(name = "recommendation_date", columnDefinition = "TIMESTAMP")
    private LocalDateTime recommendationDate;

    @Column(name = "importance")
    private Float importance;

    public enum RecommendedAction {
        repeat_theory, solve_more_tasks
    }

    public AIRecommendation() {
        this.recommendationDate = LocalDateTime.now();
    }

    public AIRecommendation(AIModel aiModel, User user, Question question, RecommendedAction recommendedAction, String explanation, Float importance) {
        this.aiModel = aiModel;
        this.user = user;
        this.question = question;
        this.recommendedAction = recommendedAction;
        this.explanation = explanation;
        this.importance = importance;
        this.recommendationDate = LocalDateTime.now();
    }

    public Long getRecommendationId() {
        return recommendationId;
    }

    public void setRecommendationId(Long recommendationId) {
        this.recommendationId = recommendationId;
    }

    public AIModel getAiModel() {
        return aiModel;
    }

    public void setAiModel(AIModel aiModel) {
        this.aiModel = aiModel;
    }

    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public RecommendedAction getRecommendedAction() {
        return recommendedAction;
    }

    public void setRecommendedAction(RecommendedAction recommendedAction) {
        this.recommendedAction = recommendedAction;
    }

    public String getExplanation() {
        return explanation;
    }

    public void setExplanation(String explanation) {
        this.explanation = explanation;
    }

    public LocalDateTime getRecommendationDate() {
        return recommendationDate;
    }

    public void setRecommendationDate(LocalDateTime recommendationDate) {
        this.recommendationDate = recommendationDate;
    }

    public Float getImportance() {
        return importance;
    }

    public void setImportance(Float importance) {
        this.importance = importance;
    }

    @Override
    public String toString() {
        return "AIRecommendation{" +
               "recommendationId=" + recommendationId +
               ", aiModel=" + (aiModel != null ? aiModel.getModelName() : "null") +
               ", question=" + (question != null ? question.getQuestionId() : "null") +
               ", user=" + (user != null ? user.getName() : "null") +
               ", recommendedAction=" + recommendedAction +
               ", explanation='" + explanation + '\'' +
               ", recommendationDate=" + recommendationDate +
               ", importance=" + importance +
               '}';
    }
}