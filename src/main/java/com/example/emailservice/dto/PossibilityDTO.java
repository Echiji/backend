package com.example.emailservice.dto;

public class PossibilityDTO {

    private Long id;
    private String possibility;
    private Long questionId;

    public PossibilityDTO() {
    }

    public PossibilityDTO(Long id, String possibility, Long questionId) {
        this.id = id;
        this.possibility = possibility;
        this.questionId = questionId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPossibility() {
        return possibility;
    }

    public void setPossibility(String possibility) {
        this.possibility = possibility;
    }

    public Long getQuestionId() {
        return questionId;
    }

    public void setQuestionId(Long questionId) {
        this.questionId = questionId;
    }
} 