package com.example.emailservice.service;

import com.example.emailservice.model.TestControle;
import com.example.emailservice.model.User;
    import com.example.emailservice.model.Questionnaire;
import com.example.emailservice.repository.TestControleRepository;
import com.example.emailservice.repository.UserRepository;
import com.example.emailservice.repository.QuestionnaireRepository;
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
    private QuestionnaireRepository questionnaireRepository;
    
    /**
     * Crée un nouveau résultat de test
     * 
     * @param testControle Le résultat à créer
     * @return Le résultat créé avec son ID
     */
    public TestControle createTestControle(TestControle testControle) {
        System.out.println("=== SERVICE BACKEND DEBUG ===");
        System.out.println("Création TestControle:");
        System.out.println("- nbBonneReponse: " + testControle.getNbBonneReponse());
        System.out.println("- nbQuestion: " + testControle.getNbQuestion());
        System.out.println("- pourcentageReussite avant sauvegarde: " + testControle.getPourcentageReussite());
        
        if (testControle.getUser() == null || testControle.getQuestionnaire() == null) {
            throw new IllegalArgumentException("L'utilisateur et le questionnaire sont obligatoires");
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
        
        TestControle saved = testControleRepository.save(testControle);
        System.out.println("- pourcentageReussite après sauvegarde: " + saved.getPourcentageReussite());
        
        return saved;
    }
    
    /**
     * Crée un nouveau résultat de test avec les IDs
     * 
     * @param nbBonneReponse Nombre de bonnes réponses
     * @param nbQuestion Nombre total de questions
     * @param questionnaireId ID du questionnaire
     * @param userId ID de l'utilisateur
     * @return Le résultat créé
     */
    public TestControle createTestControle(Integer nbBonneReponse, Integer nbQuestion, Long questionnaireId, Long userId) {
        User user = userRepository.findById(userId)
            .orElseThrow(() -> new IllegalArgumentException("Utilisateur non trouvé avec l'ID: " + userId));
        
        Questionnaire questionnaire = questionnaireRepository.findById(questionnaireId)
            .orElseThrow(() -> new IllegalArgumentException("Questionnaire non trouvé avec l'ID: " + questionnaireId));
        
        TestControle testControle = new TestControle(nbBonneReponse, nbQuestion, questionnaire, user);
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
     * @param questionnaireId L'ID du questionnaire
     * @return Liste des résultats pour ce questionnaire
     */
    public List<TestControle> getTestControlesByQuestionnaireId(Long questionnaireId) {
        return testControleRepository.findByQuestionnaireId(questionnaireId);
    }
    
    /**
     * Récupère tous les résultats d'un utilisateur pour une leçon
     * 
     * @param userId L'ID de l'utilisateur
     * @param questionnaireId L'ID du questionnaire
     * @return Liste des résultats
     */
    public List<TestControle> getTestControlesByUserIdAndQuestionnaireId(Long userId, Long questionnaireId) {
        return testControleRepository.findByUserIdAndQuestionnaireId(userId, questionnaireId);
    }
    
    /**
     * Récupère le meilleur score d'un utilisateur pour une leçon
     * 
     * @param userId L'ID de l'utilisateur
    * @param questionnaireId L'ID du questionnaire
     * @return Le meilleur résultat ou null
     */
    public TestControle getBestScoreByUserIdAndQuestionnaireId(Long userId, Long questionnaireId) {
        return testControleRepository.findTopByUserIdAndQuestionnaireIdOrderByNbBonneReponseDesc(userId, questionnaireId);
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
        //calculer la moyenne des pourcentages de réussite
        double totalPercentage = tests.stream()
            .mapToDouble(TestControle::getPourcentageReussite)
            .sum();
            
        return (int) Math.round(totalPercentage / tests.size());
    }
    
    /**
     * Calcule la moyenne des scores pour une leçon
     * 
     * @param questionnaireId L'ID du questionnaire
     * @return La moyenne des pourcentages de réussite (arrondie aux unités)
     */
    public Integer getAverageScoreByQuestionnaireId(Long questionnaireId) {
        List<TestControle> tests = getTestControlesByQuestionnaireId(questionnaireId);
        if (tests.isEmpty()) {
            return 0;
        }
        
        double totalPercentage = tests.stream()
            .mapToDouble(TestControle::getPourcentageReussite)
            .sum();
        
        return (int) Math.round(totalPercentage / tests.size());
    }
    public boolean isPerfect(TestControle testControle) {
        if (testControle == null) {
            return false;
        }
        
        // Vérifier que le nombre de bonnes réponses est égal au nombre de questions
        // ET que le pourcentage de réussite est de 100%
        return testControle.getNbBonneReponse() != null && 
               testControle.getNbQuestion() != null &&
               testControle.getNbBonneReponse().equals(testControle.getNbQuestion()) &&
               testControle.getPourcentageReussite() == 100;
    }
}
