package com.example.emailservice.service;

import com.example.emailservice.model.Question;
import com.example.emailservice.model.Questionnaire;
import com.example.emailservice.model.User;
import com.example.emailservice.model.Possibility;
import com.example.emailservice.repository.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.ArrayList;

@Service
public class QuestionService {
    @Autowired
    private QuestionRepository questionRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private QuestionnaireService questionnaireService;
    @Autowired
    private PossibilityService possibilityService;

    /**
     * Récupère les questions d'un questionnaire
     */
    public List<Question> getQuestionsByQuestionnaire(Questionnaire questionnaire) {
        return questionRepository.findByQuestionnaire(questionnaire);
    }

    /**
     * Récupère les questions par ID de questionnaire
     */
    public List<Question> getQuestionsByQuestionnaireId(Long questionnaireId) {
        return questionRepository.findByQuestionnaire_Id(questionnaireId);
    }

    /**
     * Récupère une question par son ID
     */
    public Question getQuestionById(Long id) {
        return questionRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Question not found"));
    }

    /**
     * Récupère une question optionnelle par son ID
     */
    public Optional<Question> getQuestion(Long id) {
        return questionRepository.findById(id);
    }

    /**
     * Crée une nouvelle question
     */
    public Question createQuestion(Question question) {
        Long questionnaireId = question.getQuestionnaireId();
        if (questionnaireId == null) {
            throw new RuntimeException("questionnaireId is required");
        }
        
        Questionnaire questionnaire = questionnaireService.getQuestionnaireEntityById(questionnaireId);
        User currentUser = userService.getCurrentUser();
        if (!questionnaire.getLesson().getUser().getId().equals(currentUser.getId())) {
            throw new RuntimeException("Vous n'êtes pas autorisé à ajouter une question à ce questionnaire");
        }
        Question questionToSave = new Question();
        questionToSave.setQuestion(question.getQuestion());
        questionToSave.setType(question.getType());
        questionToSave.setAnswer(question.getAnswer());
        questionToSave.setQuestionnaire(questionnaire);
        
        // Initialiser la liste des possibilités si elle est null
        if (question.getPossibilities() == null) {
            question.setPossibilities(new ArrayList<>());
        }

        // Sauvegarder d'abord la question pour obtenir son ID
        Question savedQuestion = questionRepository.save(questionToSave);

        savedQuestion.setPossibilities(new ArrayList<>());
        for (Possibility possibility : question.getPossibilities()) {
            possibility.setQuestion(savedQuestion);
            Possibility savedPossibility = possibilityService.createPossibility(possibility);
            savedQuestion.getPossibilities().add(savedPossibility);
        }

        return savedQuestion;
    }

    /**
     * Met à jour une question
     */
    public Question updateQuestion(Long id, Question question) {
        Question existingQuestion = getQuestionById(id);
        
        // Vérifier les autorisations
        User currentUser = userService.getCurrentUser();
        if (!existingQuestion.getQuestionnaire().getLesson().getUser().getId().equals(currentUser.getId())) {
            throw new RuntimeException("Vous n'êtes pas autorisé à modifier cette question");
        }
        
        // Mettre à jour les propriétés
        existingQuestion.setQuestion(question.getQuestion());
        existingQuestion.setType(question.getType());
        existingQuestion.setAnswer(question.getAnswer());
        
        // Mettre à jour les possibilités SANS remplacer la liste
        if (question.getPossibilities() != null) {
            // Vider la collection existante
            existingQuestion.getPossibilities().clear();
            // Ajouter les nouvelles possibilités
            for (Possibility p : question.getPossibilities()) {
                p.setQuestion(existingQuestion); // bien lier la possibilité à la question
                existingQuestion.getPossibilities().add(p);
            }
        }
        
        return questionRepository.save(existingQuestion);
    }

    /**
     * Supprime une question
     */
    public void deleteQuestion(Long id) {
        Question question = getQuestionById(id);
        
        // Vérifier les autorisations
        User currentUser = userService.getCurrentUser();
        if (!question.getQuestionnaire().getLesson().getUser().getId().equals(currentUser.getId())) {
            throw new RuntimeException("Vous n'êtes pas autorisé à supprimer cette question");
        }
        
        questionRepository.deleteById(id);
    }
}
