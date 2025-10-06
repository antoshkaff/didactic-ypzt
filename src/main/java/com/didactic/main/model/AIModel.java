package com.devrezaur.main.model;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "ai_model")
public class AIModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "model_id")
    private Long modelId;

    @Column(name = "model_name", nullable = false, length = 255)
    private String modelName;

    @Column(name = "version", length = 50)
    private String version;

    @Column(name = "update_date")
    private LocalDateTime updateDate;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    public AIModel() {
    }

    public AIModel(String modelName, String version, LocalDateTime updateDate, String description) {
        this.modelName = modelName;
        this.version = version;
        this.updateDate = updateDate;
        this.description = description;
    }

    public Long getModelId() {
        return modelId;
    }

    public void setModelId(Long modelId) {
        this.modelId = modelId;
    }

    public String getModelName() {
        return modelName;
    }

    public void setModelName(String modelName) {
        this.modelName = modelName;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public LocalDateTime getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(LocalDateTime updateDate) {
        this.updateDate = updateDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "AIModel{" +
               "modelId=" + modelId +
               ", modelName='" + modelName + '\'' +
               ", version='" + version + '\'' +
               ", updateDate=" + updateDate +
               ", description='" + description + '\'' +
               '}';
    }
}