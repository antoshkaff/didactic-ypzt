package com.devrezaur.main.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Column;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "topic")
public class Topic implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "topic_id")
    private Long topicId;

    @Column(name = "category_name")
    private String categoryName;

    @Column(unique = true, nullable = false, name = "topic_name")
    private String topicName;
    
    @Column(name = "subject_name")
    private String subjectName;


    public Topic() {}
    public Topic(String topicName, String categoryName) {
        this.topicName = topicName;
        this.categoryName = categoryName;
    }
    
    public Topic(Long id, String topicName, String categoryName) {
        this.topicId = id;
        this.topicName = topicName;
        this.categoryName = categoryName;
    }

    public Topic(String topicName) {
        this.topicName = topicName;
    }

    public Long getTopicId() {
        return topicId;
    }

    public void setTopicId(Long topicId) {
        this.topicId = topicId;
    }

    public String getTopicName() {
        return topicName;
    }

    public void setTopicName(String topicName) {
        this.topicName = topicName;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }
    
    public String getSubjectName() {
        return subjectName;
    }
    
    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    @Override
    public String toString() {
        return "Topic{" +
                "topicId=" + topicId +
                ", topicName='" + topicName + '\'' +
                ", categoryName='" + categoryName + '\'' +
                '}';
    }
}