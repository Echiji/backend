package com.example.emailservice.dto;

import java.time.LocalDateTime;

public class TestControleDTO {
    private Long id;
    private Integer nbBonneReponse;
    private Integer nbQuestion;
    private LocalDateTime dateCreation;
    private Long questionnaireId;
    private String questionnaireTitle;
    private Long userId;
    private String username;
    private Integer pourcentageReussite;

    public TestControleDTO() {}

    public TestControleDTO(Long id, Integer nbBonneReponse, Integer nbQuestion, LocalDateTime dateCreation, 
                          Long questionnaireId, String questionnaireTitle, Long userId, String username, Integer pourcentageReussite) {
        this.id = id;
        this.nbBonneReponse = nbBonneReponse;
        this.nbQuestion = nbQuestion;
        this.dateCreation = dateCreation;
        this.questionnaireId = questionnaireId;
        this.questionnaireTitle = questionnaireTitle;
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

    public Long getQuestionnaireId() { return questionnaireId; }
    public void setQuestionnaireId(Long questionnaireId) { this.questionnaireId = questionnaireId; }

    public String getQuestionnaireTitle() { return questionnaireTitle; }
    public void setQuestionnaireTitle(String questionnaireTitle) { this.questionnaireTitle = questionnaireTitle; }

    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public Integer getPourcentageReussite() { return pourcentageReussite; }
    public void setPourcentageReussite(Integer pourcentageReussite) { this.pourcentageReussite = pourcentageReussite; }
}
