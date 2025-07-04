package com.example.emailservice.model;

import jakarta.persistence.*;
import java.util.List;

/**
 * Entité Questionnaire représentant un groupe de questions dans l'application
 * 
 * Cette classe est mappée à la table "questionnaires" en base de données.
 * Un questionnaire appartient à une leçon et contient :
 * - Un titre
 * - Une description
 * - Une liste de questions
 * - Une relation avec la leçon qui le contient
 */
@Entity
@Table(name = "questionnaires")
public class Questionnaire {
    
    /**
     * Identifiant unique du questionnaire
     * Généré automatiquement par la base de données
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Titre du questionnaire
     */
    private String title;

    /**
     * Relation Many-to-One avec la leçon
     * Plusieurs questionnaires peuvent appartenir à une même leçon
     * La colonne "lesson_id" fait référence à l'ID de la leçon
     */
    @ManyToOne
    @JoinColumn(name = "lesson_id")
    private Lesson lesson;

    /**
     * Relation One-to-Many avec les questions
     * Un questionnaire peut contenir plusieurs questions
     */
    @OneToMany(mappedBy = "questionnaire", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Question> questions;

    /**
     * Ordre d'affichage du questionnaire dans la leçon
     */
    @Column(name = "display_order")
    private Integer displayOrder;

    /**
     * Indique si le questionnaire est actif
     */
    @Column(name = "is_active")
    private Boolean isActive = false;

    /**
     * Constructeur par défaut requis par JPA
     */
    public Questionnaire() {
    }

    /**
     * Constructeur avec paramètres
     */
    public Questionnaire(String title, Lesson lesson) {
        this.title = title;
        this.lesson = lesson;
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


    public Lesson getLesson() {
        return lesson;
    }

    public void setLesson(Lesson lesson) {
        this.lesson = lesson;
    }

    public List<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(List<Question> questions) {
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
        this.isActive = isActive;
    }

    // Méthodes utilitaires
    public Long getLessonId() {
        return lesson != null ? lesson.getId() : null;
    }

    public void setLessonId(Long lessonId) {
        // Cette méthode sera utilisée pour désérialiser le JSON
        // La leçon sera récupérée dans le service
        this.lessonId = lessonId;
    }

    // Champ temporaire pour stocker l'ID de la leçon pendant la désérialisation
    @Transient
    private Long lessonId;
} 