package com.example.emailservice.service;

import com.example.emailservice.dto.QuestionnaireDTO;
import com.example.emailservice.mapper.QuestionnaireMapper;
import com.example.emailservice.model.Questionnaire;
import com.example.emailservice.model.Lesson;
import com.example.emailservice.repository.QuestionnaireRepository;
import com.example.emailservice.repository.LessonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service pour gérer les opérations CRUD sur les questionnaires
 */
@Service
@Transactional
public class QuestionnaireService {
    
    private final QuestionnaireRepository questionnaireRepository;
    private final LessonRepository lessonRepository;
    private final QuestionnaireMapper questionnaireMapper;
    
    @Autowired
    public QuestionnaireService(QuestionnaireRepository questionnaireRepository,
                               LessonRepository lessonRepository,
                               QuestionnaireMapper questionnaireMapper) {
        this.questionnaireRepository = questionnaireRepository;
        this.lessonRepository = lessonRepository;
        this.questionnaireMapper = questionnaireMapper;
    }
    
    /**
     * Crée un nouveau questionnaire
     */
    public QuestionnaireDTO createQuestionnaire(QuestionnaireDTO questionnaireDTO) {
        // Vérifier que la leçon existe
        Lesson lesson = lessonRepository.findById(questionnaireDTO.getLessonId())
                .orElseThrow(() -> new RuntimeException("Leçon non trouvée avec l'ID: " + questionnaireDTO.getLessonId()));
        
        // Vérifier que le titre n'existe pas déjà pour cette leçon
        if (questionnaireRepository.existsByTitleAndLesson_Id(questionnaireDTO.getTitle(), questionnaireDTO.getLessonId())) {
            throw new RuntimeException("Un questionnaire avec ce titre existe déjà pour cette leçon");
        }
        
        // Créer l'entité
        Questionnaire questionnaire = questionnaireMapper.toEntity(questionnaireDTO);
        questionnaire.setLesson(lesson);
        
        // Définir l'ordre d'affichage si non spécifié
        if (questionnaire.getDisplayOrder() == null) {
            Integer nextOrder = questionnaireRepository.findNextDisplayOrder(questionnaireDTO.getLessonId());
            questionnaire.setDisplayOrder(nextOrder);
        }
        
        // Sauvegarder
        Questionnaire savedQuestionnaire = questionnaireRepository.save(questionnaire);
        return questionnaireMapper.toDTO(savedQuestionnaire);
    }
    
    /**
     * Récupère un questionnaire par son ID
     */
    public QuestionnaireDTO getQuestionnaireById(Long id) {
        Optional<Questionnaire> questionnaire = questionnaireRepository.findById(id);
        return questionnaire.map(questionnaireMapper::toDTO)
                .orElseThrow(() -> new RuntimeException("Questionnaire non trouvé avec l'ID: " + id));
    }
    
    /**
     * Récupère l'entité questionnaire par son ID
     */
    public Questionnaire getQuestionnaireEntityById(Long id) {
        return questionnaireRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Questionnaire non trouvé avec l'ID: " + id));
    }
    
    /**
     * Récupère tous les questionnaires d'une leçon
     */
    public List<QuestionnaireDTO> getQuestionnairesByLesson(Long lessonId) {
        List<Questionnaire> questionnaires = questionnaireRepository.findByLesson_IdOrderByDisplayOrderAsc(lessonId);
        return questionnaireMapper.toDTOList(questionnaires);
    }
    
    /**
     * Récupère tous les questionnaires actifs d'une leçon
     */
    public List<QuestionnaireDTO> getActiveQuestionnairesByLesson(Long lessonId) {
        List<Questionnaire> questionnaires = questionnaireRepository.findByLesson_IdAndIsActiveTrueOrderByDisplayOrderAsc(lessonId);
        return questionnaireMapper.toDTOList(questionnaires);
    }
    
    /**
     * Récupère un questionnaire avec ses questions
     */
    public QuestionnaireDTO getQuestionnaireWithQuestions(Long id) {
        Optional<Questionnaire> questionnaire = questionnaireRepository.findById(id);
        return questionnaire.map(questionnaireMapper::toDTO)
                .orElseThrow(() -> new RuntimeException("Questionnaire non trouvé avec l'ID: " + id));
    }
    
    /**
     * Met à jour un questionnaire
     */
    public QuestionnaireDTO updateQuestionnaire(Long id, QuestionnaireDTO questionnaireDTO) {
        Questionnaire existingQuestionnaire = questionnaireRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Questionnaire non trouvé avec l'ID: " + id));
        
        // Si aucun lessonId n'est fourni, utiliser celui existant
        if (questionnaireDTO.getLessonId() == null) {
            questionnaireDTO.setLessonId(existingQuestionnaire.getLesson().getId());
        }
        
        // Vérifier que la leçon existe si elle a changé
        if (!existingQuestionnaire.getLesson().getId().equals(questionnaireDTO.getLessonId())) {
            Lesson lesson = lessonRepository.findById(questionnaireDTO.getLessonId())
                    .orElseThrow(() -> new RuntimeException("Leçon non trouvée avec l'ID: " + questionnaireDTO.getLessonId()));
            existingQuestionnaire.setLesson(lesson);
        }
        
        // Vérifier que le titre n'existe pas déjà pour cette leçon (sauf pour ce questionnaire)
        if (!existingQuestionnaire.getTitle().equals(questionnaireDTO.getTitle()) &&
            questionnaireRepository.existsByTitleAndLesson_Id(questionnaireDTO.getTitle(), questionnaireDTO.getLessonId())) {
            throw new RuntimeException("Un questionnaire avec ce titre existe déjà pour cette leçon");
        }
        
        // Mettre à jour les champs
        existingQuestionnaire.setTitle(questionnaireDTO.getTitle());
        existingQuestionnaire.setDisplayOrder(questionnaireDTO.getDisplayOrder());
        existingQuestionnaire.setIsActive(questionnaireDTO.getIsActive() != null ? questionnaireDTO.getIsActive() : false);
        
        Questionnaire updatedQuestionnaire = questionnaireRepository.save(existingQuestionnaire);
        return questionnaireMapper.toDTO(updatedQuestionnaire);
    }
    
    /**
     * Supprime un questionnaire
     */
    public void deleteQuestionnaire(Long id) {
        if (!questionnaireRepository.existsById(id)) {
            throw new RuntimeException("Questionnaire non trouvé avec l'ID: " + id);
        }
        questionnaireRepository.deleteById(id);
    }
    
    /**
     * Active ou désactive un questionnaire
     */
    public QuestionnaireDTO toggleQuestionnaireStatus(Long id) {
        Questionnaire questionnaire = questionnaireRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Questionnaire non trouvé avec l'ID: " + id));
        
        questionnaire.setIsActive(!questionnaire.getIsActive());
        Questionnaire updatedQuestionnaire = questionnaireRepository.save(questionnaire);
        return questionnaireMapper.toDTO(updatedQuestionnaire);
    }
    
    /**
     * Récupère tous les questionnaires
     */
    public List<QuestionnaireDTO> getAllQuestionnaires() {
        List<Questionnaire> questionnaires = questionnaireRepository.findAll();
        return questionnaireMapper.toDTOList(questionnaires);
    }
} 