package com.example.emailservice.repository;

import com.example.emailservice.model.TestControle;
import com.example.emailservice.model.User;
import com.example.emailservice.model.Lesson;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

/**
 * Repository pour l'entité TestControle
 * 
 * Fournit les méthodes d'accès aux données pour les résultats de tests.
 * Étend JpaRepository pour bénéficier des opérations CRUD de base.
 */
@Repository
public interface TestControleRepository extends JpaRepository<TestControle, Long> {
    
    /**
     * Trouve tous les tests d'un utilisateur spécifique
     * 
     * @param user L'utilisateur
     * @return Liste des tests de l'utilisateur
     */
    List<TestControle> findByUser(User user);
    
    /**
     * Trouve tous les tests d'un utilisateur par son ID
     * 
     * @param userId L'ID de l'utilisateur
     * @return Liste des tests de l'utilisateur
     */
    List<TestControle> findByUserId(Long userId);
    
    /**
     * Trouve tous les tests pour une leçon spécifique
     * 
     * @param lesson La leçon
     * @return Liste des tests pour cette leçon
     */
    List<TestControle> findByLesson(Lesson lesson);
    
    /**
     * Trouve tous les tests pour une leçon par son ID
     * 
     * @param lessonId L'ID de la leçon
     * @return Liste des tests pour cette leçon
     */
    List<TestControle> findByLessonId(Long lessonId);
    
    /**
     * Trouve tous les tests d'un utilisateur pour une leçon spécifique
     * 
     * @param user L'utilisateur
     * @param lesson La leçon
     * @return Liste des tests de l'utilisateur pour cette leçon
     */
    List<TestControle> findByUserAndLesson(User user, Lesson lesson);
    
    /**
     * Trouve tous les tests d'un utilisateur pour une leçon par leurs IDs
     * 
     * @param userId L'ID de l'utilisateur
     * @param lessonId L'ID de la leçon
     * @return Liste des tests de l'utilisateur pour cette leçon
     */
    List<TestControle> findByUserIdAndLessonId(Long userId, Long lessonId);
    
    /**
     * Trouve le meilleur score d'un utilisateur pour une leçon
     * 
     * @param userId L'ID de l'utilisateur
     * @param lessonId L'ID de la leçon
     * @return Le test avec le meilleur score ou null
     */
    TestControle findTopByUserIdAndLessonIdOrderByNbBonneReponseDesc(Long userId, Long lessonId);
    
    /**
     * Compte le nombre de tests d'un utilisateur
     * 
     * @param userId L'ID de l'utilisateur
     * @return Le nombre de tests
     */
    long countByUserId(Long userId);
    
    /**
     * Compte le nombre de tests pour une leçon
     * 
     * @param lessonId L'ID de la leçon
     * @return Le nombre de tests
     */
    long countByLessonId(Long lessonId);
} 