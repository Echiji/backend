package com.example.emailservice.model;

import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.List;

/**
 * Entité Lesson représentant une leçon dans l'application
 * 
 * Cette classe est mappée à la table "lessons" en base de données.
 * Une leçon appartient à un utilisateur et contient :
 * - Un titre
 * - Une description
 * - Une relation avec l'utilisateur qui l'a créée
 */
@Entity
@Table(name = "lessons")
public class Lesson {
    
    /**
     * Identifiant unique de la leçon
     * Généré automatiquement par la base de données
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Titre de la leçon
     */
    private String title;

    /**
     * Description détaillée de la leçon
     */
    private String description;

    /**
     * Relation Many-to-One avec l'utilisateur
     * Plusieurs leçons peuvent appartenir à un même utilisateur
     * La colonne "user_id" fait référence à l'ID de l'utilisateur
     */
    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private User user;

    /**
     * Relation Many-to-One avec le cours
     * Plusieurs leçons peuvent appartenir à un même cours
     * La colonne "course_id" fait référence à l'ID du cours
     */
    @ManyToOne
    @JoinColumn(name = "course_id")
    @JsonIgnore
    private Course course;

    @OneToMany(mappedBy = "lesson", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<Question> questions;

    /**
     * Constructeur par défaut requis par JPA
     */
    public Lesson() {
    }

    /**
     * Constructeur avec tous les paramètres
     * 
     * @param id Identifiant de la leçon
     * @param title Titre de la leçon
     * @param description Description de la leçon
     * @param user Utilisateur propriétaire de la leçon
     */
    public Lesson(Long id, String title, String description, User user, Course course) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.user = user;
        this.course = course;
    }

    // ==================== GETTERS ET SETTERS ====================
    
    /**
     * Récupère l'identifiant de la leçon
     * @return L'ID de la leçon
     */
    public Long getId() {
        return id;
    }

    /**
     * Définit l'identifiant de la leçon
     * @param id Le nouvel ID
     */
    public void setId(Long id) {
        this.id = id;
    }
    
    /**
     * Récupère le titre de la leçon
     * @return Le titre de la leçon
     */
    public String getTitle() {
        return title;
    }

    /**
     * Définit le titre de la leçon
     * @param title Le nouveau titre
     */
    public void setTitle(String title) {
        this.title = title;
    }
    
    /**
     * Récupère la description de la leçon
     * @return La description de la leçon
     */
    public String getDescription() {
        return description;
    }

    /**
     * Définit la description de la leçon
     * @param description La nouvelle description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Récupère l'utilisateur propriétaire de la leçon
     * @return L'utilisateur propriétaire
     */
    public User getUser() {
        return user;
    }

    /**
     * Définit l'utilisateur propriétaire de la leçon
     * @param user Le nouvel utilisateur propriétaire
     */
    public void setUser(User user) {
        this.user = user;
    }

    /**
     * Récupère le cours auquel appartient la leçon
     * @return Le cours propriétaire
     */
    public Course getCourse() {
        return course;
    }

    /**
     * Définit le cours auquel appartient la leçon
     * @param course Le nouveau cours propriétaire
     */
    public void setCourse(Course course) {
        this.course = course;
    }

    public Long getCourseId() {
        return course != null ? course.getId() : null;
    }
    
    public String getCourseTitle() {
        return course != null ? course.getTitle() : null;
    }

    public List<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }
}
