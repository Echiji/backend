package com.example.emailservice.dto;

import java.util.List;

/**
 * DTO (Data Transfer Object) pour l'entité Questionnaire
 * 
 * Cette classe est utilisée pour transférer les données du questionnaire
 * entre les couches controller et service, sans exposer directement l'entité JPA.
 */
public class QuestionnaireDTO {
    
    private Long id;
    private String title;
    private Long lessonId;
    private String lessonTitle;
    private List<QuestionDTO> questions;
    private Integer displayOrder;
    private Boolean isActive;

    // Constructeur par défaut
    public QuestionnaireDTO() {
    }

    // Constructeur avec paramètres
    public QuestionnaireDTO(Long id, String title, Long lessonId) {
        this.id = id;
        this.title = title;
        this.lessonId = lessonId;
    }

    // ==================== GETTERS ET SETTERS ====================
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Long getLessonId() {
        return lessonId;
    }

    public void setLessonId(Long lessonId) {
        this.lessonId = lessonId;
    }

    public String getLessonTitle() {
        return lessonTitle;
    }

    public void setLessonTitle(String lessonTitle) {
        this.lessonTitle = lessonTitle;
    }

    public List<QuestionDTO> getQuestions() {
        return questions;
    }

    public void setQuestions(List<QuestionDTO> questions) {
        this.questions = questions;
    }

    public Integer getDisplayOrder() {
        return displayOrder;
    }

    public void setDisplayOrder(Integer displayOrder) {
        this.displayOrder = displayOrder;
    }

    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive != null ? isActive : false;
    }
} 