package com.example.emailservice.repository;

import com.example.emailservice.model.TestControle;
import com.example.emailservice.model.User;
import com.example.emailservice.model.Questionnaire;
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
     * Trouve tous les tests pour un questionnaire spécifique
     * 
     * @param questionnaire Le questionnaire
     * @return Liste des tests pour ce questionnaire
     */
    List<TestControle> findByQuestionnaire(Questionnaire questionnaire);
    
    /**
     * Trouve tous les tests pour un questionnaire par son ID
     * 
     * @param questionnaireId L'ID du questionnaire
     * @return Liste des tests pour ce questionnaire
     */
    List<TestControle> findByQuestionnaireId(Long questionnaireId);
    
    /**
     * Trouve tous les tests d'un utilisateur pour un questionnaire spécifique
     * 
     * @param user L'utilisateur
     * @param questionnaire Le questionnaire
     * @return Liste des tests de l'utilisateur pour ce questionnaire
     */
    List<TestControle> findByUserAndQuestionnaire(User user, Questionnaire questionnaire);
    
    /**
     * Trouve tous les tests d'un utilisateur pour un questionnaire par leurs IDs
     * 
     * @param userId L'ID de l'utilisateur
     * @param questionnaireId L'ID du questionnaire
     * @return Liste des tests de l'utilisateur pour ce questionnaire
     */
    List<TestControle> findByUserIdAndQuestionnaireId(Long userId, Long questionnaireId);
    
    /**
     * Trouve le meilleur score d'un utilisateur pour un questionnaire
     * 
     * @param userId L'ID de l'utilisateur
    * @param questionnaireId L'ID du questionnaire
     * @return Le test avec le meilleur score ou null
     */
    TestControle findTopByUserIdAndQuestionnaireIdOrderByNbBonneReponseDesc(Long userId, Long questionnaireId);
    
    /**
     * Compte le nombre de tests d'un utilisateur
     * 
     * @param userId L'ID de l'utilisateur
     * @return Le nombre de tests
     */
    long countByUserId(Long userId);
    
    /**
     * Compte le nombre de tests pour un questionnaire
     * 
     * @param questionnaireId L'ID du questionnaire
     * @return Le nombre de tests
     */
    long countByQuestionnaireId(Long questionnaireId);
} 