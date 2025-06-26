package com.example.emailservice.controller;

import com.example.emailservice.model.TestControle;
import com.example.emailservice.service.TestControleService;
import com.example.emailservice.dto.ProfileDTO;
import com.example.emailservice.mapper.ProfileMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Contrôleur REST pour la gestion des résultats de tests
 * 
 * Fournit les endpoints API pour créer, lire et analyser les résultats de tests.
 * Tous les endpoints sont préfixés par "/api/test-controle".
 */
@RestController
@RequestMapping("/api/test-controle")
@CrossOrigin(origins = "*")
public class TestControleController {
    
    @Autowired
    private TestControleService testControleService;

    @Autowired
    private ProfileMapper profileMapper;
    
    /**
     * Crée un nouveau résultat de test
     * 
     * @param request Map contenant les données du test
     * @return Le résultat créé avec le statut 201 (Created)
     */
    @PostMapping
    public ResponseEntity<TestControle> createTestControle(@RequestBody Map<String, Object> request) {
        try {
            Integer nbBonneReponse = (Integer) request.get("nbBonneReponse");
            Integer nbQuestion = (Integer) request.get("nbQuestion");
            Long lessonId = Long.valueOf(request.get("lessonId").toString());
            Long userId = Long.valueOf(request.get("userId").toString());
            
            TestControle testControle = testControleService.createTestControle(
                nbBonneReponse, nbQuestion, lessonId, userId
            );
            
            return new ResponseEntity<>(testControle, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    /**
     * Récupère un résultat de test par son ID
     * 
     * @param id L'ID du résultat
     * @return Le résultat trouvé avec le statut 200 (OK) ou 404 (Not Found)
     */
    @GetMapping("/{id}")
    public ResponseEntity<TestControle> getTestControleById(@PathVariable Long id) {
        System.out.println("=== CONTROLLER DEBUG ===");
        System.out.println("Recherche TestControle avec ID: " + id);
        
        Optional<TestControle> result = testControleService.getTestControleById(id);
        
        if (result.isPresent()) {
            TestControle testControle = result.get();
            System.out.println("TestControle trouvé:");
            System.out.println("- nbBonneReponse: " + testControle.getNbBonneReponse());
            System.out.println("- nbQuestion: " + testControle.getNbQuestion());
            System.out.println("- pourcentageReussite: " + testControle.getPourcentageReussite());
            System.out.println("- Calcul manuel: " + ((double)testControle.getNbBonneReponse() / testControle.getNbQuestion()) * 100);
            
            return new ResponseEntity<>(testControle, HttpStatus.OK);
        } else {
            System.out.println("TestControle non trouvé pour ID: " + id);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    
    /**
     * Récupère tous les résultats d'un utilisateur
     * 
     * @param userId L'ID de l'utilisateur
     * @return Liste des résultats avec le statut 200 (OK)
     */
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<TestControle>> getTestControlesByUserId(@PathVariable Long userId) {
        List<TestControle> testControles = testControleService.getTestControlesByUserId(userId);
        return new ResponseEntity<>(testControles, HttpStatus.OK);
    }
    
    /**
     * Récupère tous les résultats pour une leçon
     * 
     * @param lessonId L'ID de la leçon
     * @return Liste des résultats avec le statut 200 (OK)
     */
    @GetMapping("/lesson/{lessonId}")
    public ResponseEntity<List<TestControle>> getTestControlesByLessonId(@PathVariable Long lessonId) {
        List<TestControle> testControles = testControleService.getTestControlesByLessonId(lessonId);
        return new ResponseEntity<>(testControles, HttpStatus.OK);
    }
    
    /**
     * Récupère tous les résultats d'un utilisateur pour une leçon
     * 
     * @param userId L'ID de l'utilisateur
     * @param lessonId L'ID de la leçon
     * @return Liste des résultats avec le statut 200 (OK)
     */
    @GetMapping("/user/{userId}/lesson/{lessonId}")
    public ResponseEntity<List<TestControle>> getTestControlesByUserIdAndLessonId(
            @PathVariable Long userId, 
            @PathVariable Long lessonId) {
        List<TestControle> testControles = testControleService.getTestControlesByUserIdAndLessonId(userId, lessonId);
        return new ResponseEntity<>(testControles, HttpStatus.OK);
    }
    
    /**
     * Récupère le meilleur score d'un utilisateur pour une leçon
     * 
     * @param userId L'ID de l'utilisateur
     * @param lessonId L'ID de la leçon
     * @return Le meilleur résultat avec le statut 200 (OK) ou 404 (Not Found)
     */
    @GetMapping("/user/{userId}/lesson/{lessonId}/best")
    public ResponseEntity<TestControle> getBestScoreByUserIdAndLessonId(
            @PathVariable Long userId, 
            @PathVariable Long lessonId) {
        TestControle bestScore = testControleService.getBestScoreByUserIdAndLessonId(userId, lessonId);
        if (bestScore != null) {
            return new ResponseEntity<>(bestScore, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    
    /**
     * Récupère tous les résultats
     * 
     * @return Liste de tous les résultats avec le statut 200 (OK)
     */
    @GetMapping
    public ResponseEntity<List<TestControle>> getAllTestControles() {
        List<TestControle> testControles = testControleService.getAllTestControles();
        return new ResponseEntity<>(testControles, HttpStatus.OK);
    }
    
    /**
     * Calcule la moyenne des scores d'un utilisateur
     * 
     * @param userId L'ID de l'utilisateur
     * @return La moyenne avec le statut 200 (OK)
     */
    @GetMapping("/user/{userId}/average")
    public ResponseEntity<Map<String, Object>> getAverageScoreByUserId(@PathVariable Long userId) {
        Integer average = testControleService.getAverageScoreByUserId(userId);
        Map<String, Object> response = Map.of(
            "userId", userId,
            "averageScore", average
        );
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    
    /**
     * Calcule la moyenne des scores pour une leçon
     * 
     * @param lessonId L'ID de la leçon
     * @return La moyenne avec le statut 200 (OK)
     */
    @GetMapping("/lesson/{lessonId}/average")
    public ResponseEntity<Map<String, Object>> getAverageScoreByLessonId(@PathVariable Long lessonId) {
        Integer average = testControleService.getAverageScoreByLessonId(lessonId);
        Map<String, Object> response = Map.of(
            "lessonId", lessonId,
            "averageScore", average
        );
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    
    /**
     * Supprime un résultat de test
     * 
     * @param id L'ID du résultat à supprimer
     * @return Statut 200 (OK) si supprimé avec succès
     */
    @DeleteMapping("/{testControleId}")
    public ResponseEntity<Void> deleteTestControle(@PathVariable Long testControleId) {
        testControleService.deleteTestControle(testControleId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/isPerfect/{testControleId}")
    public ResponseEntity<Boolean> isPerfect(@PathVariable Long testControleId) {
        TestControle testControle = testControleService.getTestControleById(testControleId).orElse(null);
        if (testControle == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(testControleService.isPerfect(testControle), HttpStatus.OK);
    }

} 