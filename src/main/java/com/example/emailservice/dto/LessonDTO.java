package com.example.emailservice.dto;

public class LessonDTO {
    private Long id;
    private String title;
    private String description;
    private Long userId;
    private Long courseId;
    private String courseTitle;
    private Integer questionCount;

    public LessonDTO() {}

    public LessonDTO(Long id, String title, String description, Long userId, Long courseId, String courseTitle, Integer questionCount) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.userId = userId;
        this.courseId = courseId;
        this.courseTitle = courseTitle;
        this.questionCount = questionCount;
    }

    // Getters et Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }

    public Long getCourseId() { return courseId; }
    public void setCourseId(Long courseId) { this.courseId = courseId; }

    public String getCourseTitle() { return courseTitle; }
    public void setCourseTitle(String courseTitle) { this.courseTitle = courseTitle; }

    public Integer getQuestionCount() { return questionCount; }
    public void setQuestionCount(Integer questionCount) { this.questionCount = questionCount; }
}
