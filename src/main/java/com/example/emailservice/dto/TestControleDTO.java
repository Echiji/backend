package com.example.emailservice.dto;

import java.time.LocalDateTime;

public class TestControleDTO {
    private Long id;
    private Integer nbBonneReponse;
    private Integer nbQuestion;
    private LocalDateTime dateCreation;
    private Long lessonId;
    private String lessonTitle;
    private Long userId;
    private String username;
    private Integer pourcentageReussite;

    public TestControleDTO() {}

    public TestControleDTO(Long id, Integer nbBonneReponse, Integer nbQuestion, LocalDateTime dateCreation, 
                          Long lessonId, String lessonTitle, Long userId, String username, Integer pourcentageReussite) {
        this.id = id;
        this.nbBonneReponse = nbBonneReponse;
        this.nbQuestion = nbQuestion;
        this.dateCreation = dateCreation;
        this.lessonId = lessonId;
        this.lessonTitle = lessonTitle;
        this.userId = userId;
        this.username = username;
        this.pourcentageReussite = pourcentageReussite;
    }

    // Getters et Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Integer getNbBonneReponse() { return nbBonneReponse; }
    public void setNbBonneReponse(Integer nbBonneReponse) { this.nbBonneReponse = nbBonneReponse; }

    public Integer getNbQuestion() { return nbQuestion; }
    public void setNbQuestion(Integer nbQuestion) { this.nbQuestion = nbQuestion; }

    public LocalDateTime getDateCreation() { return dateCreation; }
    public void setDateCreation(LocalDateTime dateCreation) { this.dateCreation = dateCreation; }

    public Long getLessonId() { return lessonId; }
    public void setLessonId(Long lessonId) { this.lessonId = lessonId; }

    public String getLessonTitle() { return lessonTitle; }
    public void setLessonTitle(String lessonTitle) { this.lessonTitle = lessonTitle; }

    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public Integer getPourcentageReussite() { return pourcentageReussite; }
    public void setPourcentageReussite(Integer pourcentageReussite) { this.pourcentageReussite = pourcentageReussite; }
}
