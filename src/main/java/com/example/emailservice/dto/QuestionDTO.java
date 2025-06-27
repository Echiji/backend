package com.example.emailservice.dto;

import java.util.List;

public class QuestionDTO {
    private Long id;
    private String question;
    private String type;
    private String answer;
    private Long lessonId;
    private List<PossibilityDTO> possibilities;

    public QuestionDTO() {}

    public QuestionDTO(Long id, String question, String type, String answer, Long lessonId, List<PossibilityDTO> possibilities) {
        this.id = id;
        this.question = question;
        this.type = type;
        this.answer = answer;
        this.lessonId = lessonId;
        this.possibilities = possibilities;
    }

    // Getters et Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getQuestion() { return question; }
    public void setQuestion(String question) { this.question = question; }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public String getAnswer() { return answer; }
    public void setAnswer(String answer) { this.answer = answer; }

    public Long getLessonId() { return lessonId; }
    public void setLessonId(Long lessonId) { this.lessonId = lessonId; }

    public List<PossibilityDTO> getPossibilities() { return possibilities; }
    public void setPossibilities(List<PossibilityDTO> possibilities) { this.possibilities = possibilities; }
}
