package com.devrezaur.main.dto;

import java.util.List;
import java.util.stream.Collectors;

public class SubjectPerformance {
    private String subjectName;
    private double averagePercentage;
    private List<CategoryPerformance> categories;

    public SubjectPerformance(String subjectName, double averagePercentage, List<CategoryPerformance> categories) {
        this.subjectName = subjectName;
        this.averagePercentage = averagePercentage;
        this.categories = categories;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public double getAveragePercentage() {
        return averagePercentage;
    }

    public void setAveragePercentage(double averagePercentage) {
        this.averagePercentage = averagePercentage;
    }

    public List<CategoryPerformance> getCategories() {
        return categories;
    }

    public void setCategories(List<CategoryPerformance> categories) {
        this.categories = categories;
    }

    public String getFormattedAveragePercentage() {
        return String.format("%.2f%%", averagePercentage);
    }
}