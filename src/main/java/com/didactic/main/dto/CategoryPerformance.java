package com.devrezaur.main.dto;

import java.util.List;
import java.util.stream.Collectors;

public class CategoryPerformance {
    private String categoryName;
    private double averagePercentage;
    private List<TopicPerformance> topics;

    public CategoryPerformance(String categoryName, double averagePercentage, List<TopicPerformance> topics) {
        this.categoryName = categoryName;
        this.averagePercentage = averagePercentage;
        this.topics = topics;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public double getAveragePercentage() {
        return averagePercentage;
    }

    public void setAveragePercentage(double averagePercentage) {
        this.averagePercentage = averagePercentage;
    }

    public List<TopicPerformance> getTopics() {
        return topics;
    }

    public void setTopics(List<TopicPerformance> topics) {
        this.topics = topics;
    }

    public String getFormattedAveragePercentage() {
        return String.format("%.2f%%", averagePercentage);
    }
}