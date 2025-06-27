package com.example.emailservice.model;

import jakarta.persistence.*;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.JoinColumn;

@Entity
@Table(name = "possibility")
public class Possibility {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "possibility")
    private String value;

    @ManyToOne
    @JoinColumn(name = "question_id")
    private Question question;

    public Possibility() {}

    public Possibility(Long id, String value, Question question) {
        this.id = id;
        this.value = value;
        this.question = question;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getValue() { return value; }
    public void setValue(String value) { this.value = value; }

    public Question getQuestion() { return question; }
    public void setQuestion(Question question) { this.question = question; }
}
