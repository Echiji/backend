package com.example.emailservice.model;

import jakarta.persistence.*;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "question")
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String question;
    @Column(name = "type_question")
    private String type; // 'multiple' ou 'text'
    private String answer;

    @ManyToOne
    @JoinColumn(name = "lesson_id")
    private Lesson lesson;

    @OneToMany(mappedBy = "question", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Possibility> possibilities;

    public Question() {}

    public Question(Long id, String question, String type, String answer, Lesson lesson) {
        this.id = id;
        this.question = question;
        this.type = type;
        this.answer = answer;
        this.lesson = lesson;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getQuestion() { return question; }
    public void setQuestion(String question) { this.question = question; }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public String getAnswer() { return answer; }
    public void setAnswer(String answer) { this.answer = answer; }

    public List<Possibility> getPossibilities() { return possibilities; }
    public void setPossibilities(List<Possibility> possibilities) {
        this.possibilities = possibilities;
        if (possibilities != null) {
            for (Possibility p : possibilities) {
                p.setQuestion(this);
            }
        }
    }

    public Lesson getLesson() { return lesson; }
    public void setLesson(Lesson lesson) { this.lesson = lesson; }
}
