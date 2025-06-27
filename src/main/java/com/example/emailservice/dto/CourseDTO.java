package com.example.emailservice.dto;

public class CourseDTO {
    private Long id;
    private String title;
    private String typeCourse;
    private Long userId;
    private Integer lessonCount;

    public CourseDTO() {}

    public CourseDTO(Long id, String title, String typeCourse, Long userId, Integer lessonCount) {
        this.id = id;
        this.title = title;
        this.typeCourse = typeCourse;
        this.userId = userId;
        this.lessonCount = lessonCount;
    }

    // Getters et Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getTypeCourse() { return typeCourse; }
    public void setTypeCourse(String typeCourse) { this.typeCourse = typeCourse; }

    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }

    public Integer getLessonCount() { return lessonCount; }
    public void setLessonCount(Integer lessonCount) { this.lessonCount = lessonCount; }
}
