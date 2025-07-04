package com.example.emailservice.model;

import jakarta.persistence.*;
import java.util.List;

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
    @JoinColumn(name = "questionnaire_id")
    private Questionnaire questionnaire;

    @OneToMany(mappedBy = "question", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Possibility> possibilities;

    // Champ temporaire pour stocker l'ID du questionnaire pendant la désérialisation
    @Transient
    private Long questionnaireId;

    public Question() {}

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
    }

    public Questionnaire getQuestionnaire() { return questionnaire; }
    public void setQuestionnaire(Questionnaire questionnaire) { this.questionnaire = questionnaire; }

    public Long getQuestionnaireId() {
        // Si questionnaireId temporaire est défini, on l'utilise (pour la désérialisation)
        if (this.questionnaireId != null) {
            return this.questionnaireId;
        }
        // Sinon, on utilise l'ID du questionnaire lié
        return questionnaire != null ? questionnaire.getId() : null;
    }
    
    public void setQuestionnaireId(Long questionnaireId) {
        // Cette méthode sera utilisée pour désérialiser le JSON
        // Le questionnaire sera récupéré dans le service
        this.questionnaireId = questionnaireId;
    }
}
