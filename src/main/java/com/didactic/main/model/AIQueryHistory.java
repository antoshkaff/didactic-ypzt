package com.devrezaur.main.model;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "ai_query_history")
public class AIQueryHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_query")
    private Long idQuery;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "model_id", nullable = false)
    private AIModel aiModel;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "query_time", columnDefinition = "TIMESTAMP")
    private LocalDateTime queryTime;

    @Column(name = "user_query_text", columnDefinition = "TEXT")
    private String userQueryText;

    @Column(name = "ai_response", columnDefinition = "TEXT")
    private String aiResponse;

    public AIQueryHistory() {
        this.queryTime = LocalDateTime.now();
    }

    public AIQueryHistory(AIModel aiModel, User user, String userQueryText, String aiResponse) {
        this.aiModel = aiModel;
        this.user = user;
        this.userQueryText = userQueryText;
        this.aiResponse = aiResponse;
        this.queryTime = LocalDateTime.now();
    }

    public Long getIdQuery() {
        return idQuery;
    }

    public void setIdQuery(Long idQuery) {
        this.idQuery = idQuery;
    }

    public AIModel getAiModel() {
        return aiModel;
    }

    public void setAiModel(AIModel aiModel) {
        this.aiModel = aiModel;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public LocalDateTime getQueryTime() {
        return queryTime;
    }

    public void setQueryTime(LocalDateTime queryTime) {
        this.queryTime = queryTime;
    }

    public String getUserQueryText() {
        return userQueryText;
    }

    public void setUserQueryText(String userQueryText) {
        this.userQueryText = userQueryText;
    }

    public String getAiResponse() {
        return aiResponse;
    }

    public void setAiResponse(String aiResponse) {
        this.aiResponse = aiResponse;
    }

    @Override
    public String toString() {
        return "AIQueryHistory{" +
               "idQuery=" + idQuery +
               ", aiModel=" + (aiModel != null ? aiModel.getModelName() : "null") +
               ", user=" + (user != null ? user.getName() : "null") +
               ", queryTime=" + queryTime +
               ", userQueryText='" + userQueryText + '\'' +
               ", aiResponse='" + aiResponse + '\'' +
               '}';
    }
}