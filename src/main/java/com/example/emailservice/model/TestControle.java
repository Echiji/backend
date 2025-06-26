package com.example.emailservice.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

/**
 * Entité TestControle représentant les résultats d'une leçon
 * 
 * Cette classe est mappée à la table "test_controle" en base de données.
 * Elle contient les informations sur les résultats d'un utilisateur pour une leçon :
 * - Identifiant unique
 * - Nombre de bonnes réponses
 * - Nombre total de questions
 * - Référence vers la leçon
 * - Référence vers l'utilisateur
 * - Date de création
 */
@Entity
@Table(name = "test_controle")
public class TestControle {
    
    /**
     * Identifiant unique du test
     * Généré automatiquement par la base de données
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Nombre de bonnes réponses obtenues
     */
    @Column(name = "nb_bonne_reponse")
    private Integer nbBonneReponse;

    /**
     * Nombre total de questions dans la leçon
     */
    @Column(name = "nb_question")
    private Integer nbQuestion;

    /**
     * Date de création du test
     */
    @Column(name = "date_creation")
    private LocalDateTime dateCreation;

    /**
     * Référence vers la leçon
     */
    @ManyToOne
    @JoinColumn(name = "lesson_id")
    private Lesson lesson;

    /**
     * Référence vers l'utilisateur
     */
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    /**
     * Constructeur par défaut requis par JPA
     */
    public TestControle() {
        this.dateCreation = LocalDateTime.now();
    }

    /**
     * Constructeur avec paramètres
     */
    public TestControle(Integer nbBonneReponse, Integer nbQuestion, Lesson lesson, User user) {
        this.nbBonneReponse = nbBonneReponse;
        this.nbQuestion = nbQuestion;
        this.lesson = lesson;
        this.user = user;
        this.dateCreation = LocalDateTime.now();
    }

    // ==================== GETTERS ET SETTERS ====================
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getNbBonneReponse() {
        return nbBonneReponse;
    }

    public void setNbBonneReponse(Integer nbBonneReponse) {
        this.nbBonneReponse = nbBonneReponse;
    }

    public Integer getNbQuestion() {
        return nbQuestion;
    }

    public void setNbQuestion(Integer nbQuestion) {
        this.nbQuestion = nbQuestion;
    }

    public LocalDateTime getDateCreation() {
        return dateCreation;
    }

    public void setDateCreation(LocalDateTime dateCreation) {
        this.dateCreation = dateCreation;
    }

    public Lesson getLesson() {
        return lesson;
    }

    public void setLesson(Lesson lesson) {
        this.lesson = lesson;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    /**
     * Calcule le pourcentage de réussite
     * @return Le pourcentage de bonnes réponses (arrondi aux unités)
     */
    public Integer getPourcentageReussite() {
        if (nbQuestion == null || nbQuestion == 0) {
            return 0;
        }
        // Calcul en double puis arrondi vers l'entier le plus proche
        return (int) Math.round((double) nbBonneReponse / nbQuestion * 100);
    }

    @Override
    public String toString() {
        return "TestControle{" +
                "id=" + id +
                ", nbBonneReponse=" + nbBonneReponse +
                ", nbQuestion=" + nbQuestion +
                ", dateCreation=" + dateCreation +
                ", lesson=" + (lesson != null ? lesson.getId() : null) +
                ", user=" + (user != null ? user.getId() : null) +
                '}';
    }
} 