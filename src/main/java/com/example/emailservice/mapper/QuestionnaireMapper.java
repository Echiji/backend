package com.example.emailservice.mapper;

import com.example.emailservice.dto.QuestionnaireDTO;
import com.example.emailservice.model.Questionnaire;
import com.example.emailservice.model.Question;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Mapper pour convertir entre l'entité Questionnaire et QuestionnaireDTO
 */
@Component
public class QuestionnaireMapper {
    
    private final QuestionMapper questionMapper;
    
    public QuestionnaireMapper(QuestionMapper questionMapper) {
        this.questionMapper = questionMapper;
    }

    /**
     * Convertit une entité Questionnaire en QuestionnaireDTO
     */
    public QuestionnaireDTO toDTO(Questionnaire questionnaire) {
        if (questionnaire == null) {
            return null;
        }

        QuestionnaireDTO dto = new QuestionnaireDTO();
        dto.setId(questionnaire.getId());
        dto.setTitle(questionnaire.getTitle());
        dto.setDisplayOrder(questionnaire.getDisplayOrder());
        dto.setIsActive(questionnaire.getIsActive());
        
        // Récupérer l'ID et le titre de la leçon
        if (questionnaire.getLesson() != null) {
            dto.setLessonId(questionnaire.getLesson().getId());
            dto.setLessonTitle(questionnaire.getLesson().getTitle());
        }
        
        // Convertir les questions si elles existent
        if (questionnaire.getQuestions() != null) {
            List<Question> questions = questionnaire.getQuestions();
            dto.setQuestions(questions.stream()
                    .map(questionMapper::toQuestionDTO)
                    .collect(Collectors.toList()));
        }
        
        return dto;
    }

    /**
     * Convertit un QuestionnaireDTO en entité Questionnaire
     */
    public Questionnaire toEntity(QuestionnaireDTO dto) {
        if (dto == null) {
            return null;
        }

        Questionnaire questionnaire = new Questionnaire();
        questionnaire.setId(dto.getId());
        questionnaire.setTitle(dto.getTitle());
        questionnaire.setDisplayOrder(dto.getDisplayOrder());
        questionnaire.setIsActive(dto.getIsActive() != null ? dto.getIsActive() : false);
        
        // Note: lessonId sera géré dans le service pour récupérer la leçon
        
        return questionnaire;
    }

    /**
     * Convertit une liste d'entités Questionnaire en liste de DTOs
     */
    public List<QuestionnaireDTO> toDTOList(List<Questionnaire> questionnaires) {
        if (questionnaires == null) {
            return null;
        }
        
        return questionnaires.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    /**
     * Convertit une liste de DTOs en liste d'entités Questionnaire
     */
    public List<Questionnaire> toEntityList(List<QuestionnaireDTO> dtos) {
        if (dtos == null) {
            return null;
        }
        
        return dtos.stream()
                .map(this::toEntity)
                .collect(Collectors.toList());
    }
} 