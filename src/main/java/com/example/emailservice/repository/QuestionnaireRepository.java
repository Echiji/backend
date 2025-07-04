package com.example.emailservice.repository;

import com.example.emailservice.model.Questionnaire;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository pour l'entité Questionnaire
 * 
 * Fournit les méthodes d'accès aux données pour les questionnaires
 */
@Repository
public interface QuestionnaireRepository extends JpaRepository<Questionnaire, Long> {
    
    /**
     * Trouve tous les questionnaires d'une leçon donnée
     */
    List<Questionnaire> findByLesson_IdOrderByDisplayOrderAsc(Long lessonId);
    
    /**
     * Trouve tous les questionnaires actifs d'une leçon donnée
     */
    List<Questionnaire> findByLesson_IdAndIsActiveTrueOrderByDisplayOrderAsc(Long lessonId);
    
    /**
     * Trouve un questionnaire par son titre et sa leçon
     */
    Questionnaire findByTitleAndLesson_Id(String title, Long lessonId);
    
    /**
     * Vérifie si un questionnaire existe par son titre et sa leçon
     */
    boolean existsByTitleAndLesson_Id(String title, Long lessonId);
    
    /**
     * Trouve le prochain ordre d'affichage disponible pour une leçon
     */
    @Query("SELECT COALESCE(MAX(q.displayOrder), 0) + 1 FROM Questionnaire q WHERE q.lesson.id = :lessonId")
    Integer findNextDisplayOrder(@Param("lessonId") Long lessonId);
    
    /**
     * Trouve tous les questionnaires avec leurs questions
     */
    @Query("SELECT DISTINCT q FROM Questionnaire q LEFT JOIN FETCH q.questions WHERE q.lesson.id = :lessonId ORDER BY q.displayOrder")
    List<Questionnaire> findByLessonIdWithQuestions(@Param("lessonId") Long lessonId);
} 