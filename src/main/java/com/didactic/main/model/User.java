package com.devrezaur.main.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Column;
import java.time.LocalDateTime;

@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idUser;
    
    @Column(unique = true)
    private String name;
    private String password;
    private String email;

    public enum PreparationLevel {
        BEGINNER, INTERMEDIATE, ADVANCED
    }

    private PreparationLevel preparationLevel;
    private String lastTopic;
    private LocalDateTime registrationDate;

    public User() {
        this.registrationDate = LocalDateTime.now();
    }

    public User(String name, String password, String email) {
        this.name = name;
        this.password = password;
        this.email = email;
        this.registrationDate = LocalDateTime.now();
    }

    public Long getIdUser() {
        return idUser;
    }

    public void setIdUser(Long idUser) {
        this.idUser = idUser;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public PreparationLevel getPreparationLevel() {
        return preparationLevel;
    }

    public void setPreparationLevel(PreparationLevel preparationLevel) {
        this.preparationLevel = preparationLevel;
    }

    public String getLastTopic() {
        return lastTopic;
    }

    public void setLastTopic(String lastTopic) {
        this.lastTopic = lastTopic;
    }

    public LocalDateTime getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(LocalDateTime registrationDate) {
        this.registrationDate = registrationDate;
    }

    @Override
    public String toString() {
        return "User{" +
               "idUser=" + idUser +
               ", name='" + name + '\'' +
               ", password='[PROTECTED]'" +
               ", email='" + email + '\'' +
               ", lastTopic=" + (lastTopic != null ? lastTopic : "null") +
               ", preparationLevel=" + preparationLevel +
               ", registrationDate=" + registrationDate +
               '}';
    }
}