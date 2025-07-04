package com.example.emailservice.controller;

import com.example.emailservice.dto.QuestionnaireDTO;
import com.example.emailservice.service.QuestionnaireService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller REST pour gérer les questionnaires
 */
@RestController
@RequestMapping("/api/questionnaires")
@CrossOrigin(origins = "*")
public class QuestionnaireController {
    
    private final QuestionnaireService questionnaireService;
    
    @Autowired
    public QuestionnaireController(QuestionnaireService questionnaireService) {
        this.questionnaireService = questionnaireService;
    }
    
    /**
     * Crée un nouveau questionnaire
     * POST /api/questionnaires
     */
    @PostMapping
    public ResponseEntity<QuestionnaireDTO> createQuestionnaire(@RequestBody QuestionnaireDTO questionnaireDTO) {
        try {
            QuestionnaireDTO createdQuestionnaire = questionnaireService.createQuestionnaire(questionnaireDTO);
            return new ResponseEntity<>(createdQuestionnaire, HttpStatus.CREATED);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
    
    /**
     * Récupère tous les questionnaires
     * GET /api/questionnaires
     */
    @GetMapping
    public ResponseEntity<List<QuestionnaireDTO>> getAllQuestionnaires() {
        try {
            List<QuestionnaireDTO> questionnaires = questionnaireService.getAllQuestionnaires();
            return new ResponseEntity<>(questionnaires, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    /**
     * Récupère un questionnaire par son ID
     * GET /api/questionnaires/{id}
     */
    @GetMapping("/{id}")
    public ResponseEntity<QuestionnaireDTO> getQuestionnaireById(@PathVariable Long id) {
        try {
            QuestionnaireDTO questionnaire = questionnaireService.getQuestionnaireById(id);
            return new ResponseEntity<>(questionnaire, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    
    /**
     * Récupère un questionnaire avec ses questions
     * GET /api/questionnaires/{id}/with-questions
     */
    @GetMapping("/{id}/with-questions")
    public ResponseEntity<QuestionnaireDTO> getQuestionnaireWithQuestions(@PathVariable Long id) {
        try {
            QuestionnaireDTO questionnaire = questionnaireService.getQuestionnaireWithQuestions(id);
            return new ResponseEntity<>(questionnaire, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    
    /**
     * Récupère tous les questionnaires d'une leçon
     * GET /api/questionnaires/lesson/{lessonId}
     */
    @GetMapping("/lesson/{lessonId}")
    public ResponseEntity<List<QuestionnaireDTO>> getQuestionnairesByLesson(@PathVariable Long lessonId) {
        try {
            List<QuestionnaireDTO> questionnaires = questionnaireService.getQuestionnairesByLesson(lessonId);
            return new ResponseEntity<>(questionnaires, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    /**
     * Récupère tous les questionnaires actifs d'une leçon
     * GET /api/questionnaires/lesson/{lessonId}/active
     */
    @GetMapping("/lesson/{lessonId}/active")
    public ResponseEntity<List<QuestionnaireDTO>> getActiveQuestionnairesByLesson(@PathVariable Long lessonId) {
        try {
            List<QuestionnaireDTO> questionnaires = questionnaireService.getActiveQuestionnairesByLesson(lessonId);
            return new ResponseEntity<>(questionnaires, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    /**
     * Met à jour un questionnaire
     * PUT /api/questionnaires/{id}
     */
    @PutMapping("/{id}")
    public ResponseEntity<QuestionnaireDTO> updateQuestionnaire(@PathVariable Long id, @RequestBody QuestionnaireDTO questionnaireDTO) {
        try {
            QuestionnaireDTO updatedQuestionnaire = questionnaireService.updateQuestionnaire(id, questionnaireDTO);
            return new ResponseEntity<>(updatedQuestionnaire, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
    
    /**
     * Active ou désactive un questionnaire
     * PATCH /api/questionnaires/{id}/toggle-status
     */
    @PatchMapping("/{id}/toggle-status")
    public ResponseEntity<QuestionnaireDTO> toggleQuestionnaireStatus(@PathVariable Long id) {
        try {
            QuestionnaireDTO questionnaire = questionnaireService.toggleQuestionnaireStatus(id);
            return new ResponseEntity<>(questionnaire, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    
    /**
     * Supprime un questionnaire
     * DELETE /api/questionnaires/{id}
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteQuestionnaire(@PathVariable Long id) {
        try {
            questionnaireService.deleteQuestionnaire(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
} 