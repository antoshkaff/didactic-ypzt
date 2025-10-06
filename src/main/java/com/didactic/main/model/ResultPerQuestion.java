package com.devrezaur.main.model;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "result_per_question")
public class ResultPerQuestion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "result_id")
    private Long resultId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "question_id", nullable = false)
    private Question question;

    @Column(name = "result_correct", nullable = false)
    private Boolean resultCorrect;

    @Column(name = "answer_time")
    private LocalDateTime answerTime;

    public ResultPerQuestion() {
        this.answerTime = LocalDateTime.now();
    }

    public ResultPerQuestion(User user, Question question, Boolean resultCorrect) {
        this.user = user;
        this.question = question;
        this.resultCorrect = resultCorrect;
        this.answerTime = LocalDateTime.now();
    }

    public Long getResultId() {
        return resultId;
    }

    public void setResultId(Long resultId) {
        this.resultId = resultId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }

    public Boolean getResultCorrect() {
        return resultCorrect;
    }

    public void setResultCorrect(Boolean resultCorrect) {
        this.resultCorrect = resultCorrect;
    }

    public LocalDateTime getAnswerTime() {
        return answerTime;
    }

    public void setAnswerTime(LocalDateTime answerTime) {
        this.answerTime = answerTime;
    }

    @Override
    public String toString() {
        return "ResultPerQuestion{" +
               "resultId=" + resultId +
               ", user=" + (user != null ? user.getName() : "null") +
               ", question=" + (question != null ? question.getQuestionId() : "null") +
               ", resultCorrect=" + resultCorrect +
               ", answerTime=" + answerTime +
               '}';
    }
}