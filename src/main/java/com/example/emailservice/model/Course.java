package com.example.emailservice.model;

import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.List;

/**
 * Entité Course représentant un cours dans l'application
 * 
 * Cette classe est mappée à la table "courses" en base de données.
 * Un cours appartient à un utilisateur et contient plusieurs leçons.
 * Un cours contient :
 * - Un titre
 * - Une description
 * - Une relation avec l'utilisateur qui l'a créé
 * - Une liste de leçons
 */
@Entity
@Table(name = "courses")
public class Course {
    
    /**
     * Identifiant unique du cours
     * Généré automatiquement par la base de données
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Titre du cours
     */
    private String title;

    /**
     * Type du cours (ex: math, language, ...)
     */
    private String typeCourse;

    /**
     * Relation Many-to-One avec l'utilisateur
     * Plusieurs cours peuvent appartenir à un même utilisateur
     * La colonne "user_id" fait référence à l'ID de l'utilisateur
     */
    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private User user;

    /**
     * Relation One-to-Many avec les leçons
     * Un cours peut contenir plusieurs leçons
     * CascadeType.ALL permet de supprimer les leçons quand le cours est supprimé
     */
    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Lesson> lessons;

    /**
     * Constructeur par défaut requis par JPA
     */
    public Course() {
    }

    /**
     * Constructeur avec tous les paramètres
     * 
     * @param id Identifiant du cours
     * @param title Titre du cours
     * @param typeCourse Type du cours
     * @param user Utilisateur propriétaire du cours
     */
    public Course(Long id, String title, String typeCourse, User user) {
        this.id = id;
        this.title = title;
        this.typeCourse = typeCourse;
        this.user = user;
    }

    // ==================== GETTERS ET SETTERS ====================
    
    /**
     * Récupère l'identifiant du cours
     * @return L'ID du cours
     */
    public Long getId() {
        return id;
    }

    /**
     * Définit l'identifiant du cours
     * @param id Le nouvel ID
     */
    public void setId(Long id) {
        this.id = id;
    }
    
    /**
     * Récupère le titre du cours
     * @return Le titre du cours
     */
    public String getTitle() {
        return title;
    }

    /**
     * Définit le titre du cours
     * @param title Le nouveau titre
     */
    public void setTitle(String title) {
        this.title = title;
    }
    
    /**
     * Récupère le type du cours
     * @return Le type du cours
     */
    public String getTypeCourse() {
        return typeCourse;
    }

    /**
     * Définit le type du cours
     * @param typeCourse Le nouveau type
     */
    public void setTypeCourse(String typeCourse) {
        this.typeCourse = typeCourse;
    }

    /**
     * Récupère l'utilisateur propriétaire du cours
     * @return L'utilisateur propriétaire
     */
    public User getUser() {
        return user;
    }

    /**
     * Définit l'utilisateur propriétaire du cours
     * @param user Le nouvel utilisateur propriétaire
     */
    public void setUser(User user) {
        this.user = user;
    }

    /**
     * Récupère la liste des leçons du cours
     * @return La liste des leçons
     */
    public List<Lesson> getLessons() {
        return lessons;
    }

    /**
     * Définit la liste des leçons du cours
     * @param lessons La nouvelle liste de leçons
     */
    public void setLessons(List<Lesson> lessons) {
        this.lessons = lessons;
    }
} 