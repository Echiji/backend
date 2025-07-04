package com.example.emailservice.controller;

import com.example.emailservice.model.TestControle;
import com.example.emailservice.service.TestControleService;
import com.example.emailservice.dto.TestControleDTO;
import com.example.emailservice.mapper.TestControleMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

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
    private TestControleMapper testControleMapper;
    
    /**
     * Crée un nouveau résultat de test
     * 
     * @param request Map contenant les données du test
     * @return Le résultat créé avec le statut 201 (Created)
     */
    @PostMapping
    public ResponseEntity<TestControleDTO> createTestControle(@RequestBody Map<String, Object> request) {
        try {
            Integer nbBonneReponse = (Integer) request.get("nbBonneReponse");
            Integer nbQuestion = (Integer) request.get("nbQuestion");
            Long questionnaireId = Long.valueOf(request.get("questionnaireId").toString());
            Long userId = Long.valueOf(request.get("userId").toString());
            
            TestControle testControle = testControleService.createTestControle(
                nbBonneReponse, nbQuestion, questionnaireId, userId
            );
            
            TestControleDTO testControleDTO = testControleMapper.toTestControleDTO(testControle);
            return ResponseEntity.ok(testControleDTO);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
    
    /**
     * Récupère un résultat de test par son ID
     * 
     * @param id L'ID du résultat
     * @return Le résultat trouvé avec le statut 200 (OK) ou 404 (Not Found)
     */
    @GetMapping("/{id}")
    public ResponseEntity<TestControleDTO> getTestControleById(@PathVariable Long id) {
        return testControleService.getTestControleById(id)
            .map(testControle -> {
                TestControleDTO dto = testControleMapper.toTestControleDTO(testControle);
                return ResponseEntity.ok(dto);
            })
            .orElse(ResponseEntity.notFound().build());
    }
    
    /**
     * Récupère tous les résultats d'un utilisateur
     * 
     * @param userId L'ID de l'utilisateur
     * @return Liste des résultats avec le statut 200 (OK)
     */
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<TestControleDTO>> getTestControlesByUserId(@PathVariable Long userId) {
        List<TestControle> testControles = testControleService.getTestControlesByUserId(userId);
        List<TestControleDTO> dtos = testControleMapper.toTestControleDTOList(testControles);
        return ResponseEntity.ok(dtos);
    }
    
    /**
     * Récupère tous les résultats pour une leçon
     * 
     * @param lessonId L'ID de la leçon
     * @return Liste des résultats avec le statut 200 (OK)
     */
    @GetMapping("/questionnaire/{questionnaireId}")
    public ResponseEntity<List<TestControleDTO>> getTestControlesByQuestionnaireId(@PathVariable Long questionnaireId) {
        List<TestControle> testControles = testControleService.getTestControlesByQuestionnaireId(questionnaireId);
        List<TestControleDTO> dtos = testControleMapper.toTestControleDTOList(testControles);
        return ResponseEntity.ok(dtos);
    }
    
    /**
     * Récupère tous les résultats d'un utilisateur pour une leçon
     * 
     * @param userId L'ID de l'utilisateur
     * @param lessonId L'ID de la leçon
     * @return Liste des résultats avec le statut 200 (OK)
     */
    @GetMapping("/user/{userId}/questionnaire/{questionnaireId}")
    public ResponseEntity<List<TestControleDTO>> getTestControlesByUserIdAndQuestionnaireId(
            @PathVariable Long userId, 
            @PathVariable Long lessonId) {
        List<TestControle> testControles = testControleService.getTestControlesByUserIdAndQuestionnaireId(userId, lessonId);
        List<TestControleDTO> dtos = testControleMapper.toTestControleDTOList(testControles);
        return new ResponseEntity<>(dtos, HttpStatus.OK);
    }
    
    /**
     * Récupère le meilleur score d'un utilisateur pour une leçon
     * 
     * @param userId L'ID de l'utilisateur
     * @param lessonId L'ID de la leçon
     * @return Le meilleur résultat avec le statut 200 (OK) ou 404 (Not Found)
     */
    @GetMapping("/user/{userId}/questionnaire/{questionnaireId}/best")
    public ResponseEntity<TestControleDTO> getBestScoreByUserIdAndQuestionnaireId(
            @PathVariable Long userId, 
            @PathVariable Long lessonId) {
        TestControle bestScore = testControleService.getBestScoreByUserIdAndQuestionnaireId(userId, lessonId);
        if (bestScore != null) {
            TestControleDTO dto = testControleMapper.toTestControleDTO(bestScore);
            return new ResponseEntity<>(dto, HttpStatus.OK);
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
    public ResponseEntity<List<TestControleDTO>> getAllTestControles() {
        List<TestControle> testControles = testControleService.getAllTestControles();
        List<TestControleDTO> dtos = testControleMapper.toTestControleDTOList(testControles);
        return new ResponseEntity<>(dtos, HttpStatus.OK);
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
     * Calcule la moyenne des scores pour un questionnaire
     * 
        * @param questionnaireId L'ID du questionnaire
     * @return La moyenne avec le statut 200 (OK)
     */
    @GetMapping("/questionnaire/{questionnaireId}/average")
    public ResponseEntity<Map<String, Object>> getAverageScoreByQuestionnaireId(@PathVariable Long questionnaireId) {
        Integer average = testControleService.getAverageScoreByQuestionnaireId(questionnaireId);
        Map<String, Object> response = Map.of(
            "questionnaireId", questionnaireId,
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
        return ResponseEntity.ok().build();
    }

    @GetMapping("/isPerfect/{testControleId}")
    public ResponseEntity<Boolean> isPerfect(@PathVariable Long testControleId) {
        TestControle testControle = testControleService.getTestControleById(testControleId).orElse(null);
        if (testControle == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(testControleService.isPerfect(testControle));
    }

} 