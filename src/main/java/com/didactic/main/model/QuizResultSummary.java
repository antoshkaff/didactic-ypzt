package com.devrezaur.main.model;

import javax.persistence.*;

@Entity
@Table(name = "quiz_result_summary")
public class QuizResultSummary {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "total_correct_questions")
    private int totalCorrect = 0;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    public QuizResultSummary() {
    }

    public QuizResultSummary(int totalCorrect, User user) {
        this.totalCorrect = totalCorrect;
        this.user = user;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getTotalCorrect() {
        return totalCorrect;
    }

    public void setTotalCorrect(int totalCorrect) {
        this.totalCorrect = totalCorrect;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "QuizResultSummary [id=" + id + ", totalCorrect=" + totalCorrect + ", username=" + (user != null ? user.getName() : "null") + "]";
    }
}