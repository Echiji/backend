package com.example.emailservice.service;

import com.example.emailservice.model.TestControle;
import com.example.emailservice.model.User;
import com.example.emailservice.model.Lesson;
import com.example.emailservice.repository.TestControleRepository;
import com.example.emailservice.repository.UserRepository;
import com.example.emailservice.repository.LessonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

/**
 * Service pour la gestion des résultats de tests
 * 
 * Ce service gère la logique métier pour :
 * - Créer un nouveau résultat de test
 * - Récupérer les résultats d'un utilisateur
 * - Récupérer les statistiques
 */
@Service
public class TestControleService {
    
    @Autowired
    private TestControleRepository testControleRepository;
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private LessonRepository lessonRepository;
    
    /**
     * Crée un nouveau résultat de test
     * 
     * @param testControle Le résultat à créer
     * @return Le résultat créé avec son ID
     */
    public TestControle createTestControle(TestControle testControle) {
        if (testControle.getUser() == null || testControle.getLesson() == null) {
            throw new IllegalArgumentException("L'utilisateur et la leçon sont obligatoires");
        }
        
        if (testControle.getNbBonneReponse() == null || testControle.getNbQuestion() == null) {
            throw new IllegalArgumentException("Le nombre de bonnes réponses et le nombre de questions sont obligatoires");
        }
        
        if (testControle.getNbBonneReponse() < 0 || testControle.getNbQuestion() < 0) {
            throw new IllegalArgumentException("Les nombres ne peuvent pas être négatifs");
        }
        
        if (testControle.getNbBonneReponse() > testControle.getNbQuestion()) {
            throw new IllegalArgumentException("Le nombre de bonnes réponses ne peut pas dépasser le nombre de questions");
        }
        
        return testControleRepository.save(testControle);
    }
    
    /**
     * Crée un nouveau résultat de test avec les IDs
     * 
     * @param nbBonneReponse Nombre de bonnes réponses
     * @param nbQuestion Nombre total de questions
     * @param lessonId ID de la leçon
     * @param userId ID de l'utilisateur
     * @return Le résultat créé
     */
    public TestControle createTestControle(Integer nbBonneReponse, Integer nbQuestion, Long lessonId, Long userId) {
        User user = userRepository.findById(userId)
            .orElseThrow(() -> new IllegalArgumentException("Utilisateur non trouvé avec l'ID: " + userId));
        
        Lesson lesson = lessonRepository.findById(lessonId)
            .orElseThrow(() -> new IllegalArgumentException("Leçon non trouvée avec l'ID: " + lessonId));
        
        TestControle testControle = new TestControle(nbBonneReponse, nbQuestion, lesson, user);
        return createTestControle(testControle);
    }
    
    /**
     * Récupère un résultat de test par son ID
     * 
     * @param id L'ID du résultat
     * @return Le résultat trouvé ou null
     */
    public Optional<TestControle> getTestControleById(Long id) {
        return testControleRepository.findById(id);
    }
    
    /**
     * Récupère tous les résultats d'un utilisateur
     * 
     * @param userId L'ID de l'utilisateur
     * @return Liste des résultats de l'utilisateur
     */
    public List<TestControle> getTestControlesByUserId(Long userId) {
        return testControleRepository.findByUserId(userId);
    }
    
    /**
     * Récupère tous les résultats pour une leçon
     * 
     * @param lessonId L'ID de la leçon
     * @return Liste des résultats pour cette leçon
     */
    public List<TestControle> getTestControlesByLessonId(Long lessonId) {
        return testControleRepository.findByLessonId(lessonId);
    }
    
    /**
     * Récupère tous les résultats d'un utilisateur pour une leçon
     * 
     * @param userId L'ID de l'utilisateur
     * @param lessonId L'ID de la leçon
     * @return Liste des résultats
     */
    public List<TestControle> getTestControlesByUserIdAndLessonId(Long userId, Long lessonId) {
        return testControleRepository.findByUserIdAndLessonId(userId, lessonId);
    }
    
    /**
     * Récupère le meilleur score d'un utilisateur pour une leçon
     * 
     * @param userId L'ID de l'utilisateur
     * @param lessonId L'ID de la leçon
     * @return Le meilleur résultat ou null
     */
    public TestControle getBestScoreByUserIdAndLessonId(Long userId, Long lessonId) {
        return testControleRepository.findTopByUserIdAndLessonIdOrderByNbBonneReponseDesc(userId, lessonId);
    }
    
    /**
     * Récupère tous les résultats
     * 
     * @return Liste de tous les résultats
     */
    public List<TestControle> getAllTestControles() {
        return testControleRepository.findAll();
    }
    
    /**
     * Supprime un résultat de test
     * 
     * @param id L'ID du résultat à supprimer
     */
    public void deleteTestControle(Long id) {
        testControleRepository.deleteById(id);
    }
    
    /**
     * Calcule la moyenne des scores d'un utilisateur
     * 
     * @param userId L'ID de l'utilisateur
     * @return La moyenne des pourcentages de réussite (arrondie aux unités)
     */
    public Integer getAverageScoreByUserId(Long userId) {
        List<TestControle> tests = getTestControlesByUserId(userId);
        if (tests.isEmpty()) {
            return 0;
        }
        
        double totalPercentage = tests.stream()
            .mapToDouble(TestControle::getPourcentageReussite)
            .sum();
        
        return (int) Math.round(totalPercentage / tests.size());
    }
    
    /**
     * Calcule la moyenne des scores pour une leçon
     * 
     * @param lessonId L'ID de la leçon
     * @return La moyenne des pourcentages de réussite (arrondie aux unités)
     */
    public Integer getAverageScoreByLessonId(Long lessonId) {
        List<TestControle> tests = getTestControlesByLessonId(lessonId);
        if (tests.isEmpty()) {
            return 0;
        }
        
        double totalPercentage = tests.stream()
            .mapToDouble(TestControle::getPourcentageReussite)
            .sum();
        
        return (int) Math.round(totalPercentage / tests.size());
    }
}
