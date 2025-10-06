package com.devrezaur.main.dto;

public class TopicPerformance {
    private String topicName;
    private double percentage;

    public TopicPerformance(String topicName, double percentage) {
        this.topicName = topicName;
        this.percentage = percentage;
    }

    public String getTopicName() {
        return topicName;
    }

    public void setTopicName(String topicName) {
        this.topicName = topicName;
    }

    public double getPercentage() {
        return percentage;
    }

    public void setPercentage(double percentage) {
        this.percentage = percentage;
    }

    public String getFormattedPercentage() {
        return String.format("%.2f%%", percentage);
    }
}