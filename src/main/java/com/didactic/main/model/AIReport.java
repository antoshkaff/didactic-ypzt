package com.devrezaur.main.model;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "ai_report")
public class AIReport {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "report_id")
    private Long reportId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "model_id", nullable = false)
    private AIModel aiModel;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "report_content", columnDefinition = "TEXT")
    private String reportContent;

    @Column(name = "creation_date", columnDefinition = "TIMESTAMP")
    private LocalDateTime creationDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "report_type")
    private ReportType reportType;

    public enum ReportType {
        weekly, monthly, by_topic
    }

    public AIReport() {
        this.creationDate = LocalDateTime.now();
    }

    public AIReport(AIModel aiModel, User user, String reportContent, ReportType reportType) {
        this.aiModel = aiModel;
        this.user = user;
        this.reportContent = reportContent;
        this.reportType = reportType;
        this.creationDate = LocalDateTime.now();
    }

    public Long getReportId() {
        return reportId;
    }

    public void setReportId(Long reportId) {
        this.reportId = reportId;
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

    public String getReportContent() {
        return reportContent;
    }

    public void setReportContent(String reportContent) {
        this.reportContent = reportContent;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public ReportType getReportType() {
        return reportType;
    }

    public void setReportType(ReportType reportType) {
        this.reportType = reportType;
    }

    @Override
    public String toString() {
        return "AIReport{" +
               "reportId=" + reportId +
               ", aiModel=" + (aiModel != null ? aiModel.getModelName() : "null") +
               ", user=" + (user != null ? user.getName() : "null") +
               ", reportContent='" + reportContent + '\'' +
               ", creationDate=" + creationDate +
               ", reportType=" + reportType +
               '}';
    }
}