package com.example.emailservice.service;

import com.example.emailservice.model.Question;
import com.example.emailservice.model.Lesson;
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
    private LessonService lessonService;
    @Autowired
    private PossibilityService possibilityService;

    /**
     * Récupère les questions d'une leçon
     */
    public List<Question> getQuestionsByLesson(Lesson lesson) {
        return questionRepository.findByLesson(lesson);
    }

    /**
     * Récupère les questions par ID de leçon
     */
    public List<Question> getQuestionsByLessonId(Long lessonId) {
        return questionRepository.findByLesson_Id(lessonId);
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
        Long lessonId = question.getLessonId();
        if (lessonId == null) {
            throw new RuntimeException("lessonId is required");
        }
        
        Lesson lesson = lessonService.getLessonById(lessonId);
        User currentUser = userService.getCurrentUser();
        if (!lesson.getUser().getId().equals(currentUser.getId())) {
            throw new RuntimeException("Vous n'êtes pas autorisé à ajouter une question à cette leçon");
        }
        Question questionToSave = new Question();
        questionToSave.setQuestion(question.getQuestion());
        questionToSave.setType(question.getType());
        questionToSave.setAnswer(question.getAnswer());
        questionToSave.setLesson(lesson);

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
        if (!existingQuestion.getLesson().getUser().getId().equals(currentUser.getId())) {
            throw new RuntimeException("Vous n'êtes pas autorisé à modifier cette question");
        }
        
        // Mettre à jour les propriétés
        existingQuestion.setQuestion(question.getQuestion());
        existingQuestion.setType(question.getType());
        existingQuestion.setAnswer(question.getAnswer());
        
        // Mettre à jour les possibilités si fournies
        if (question.getPossibilities() != null) {
            for (Possibility p : question.getPossibilities()) {
                p.setQuestion(existingQuestion);
            }
            existingQuestion.setPossibilities(question.getPossibilities());
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
        if (!question.getLesson().getUser().getId().equals(currentUser.getId())) {
            throw new RuntimeException("Vous n'êtes pas autorisé à supprimer cette question");
        }
        
        questionRepository.deleteById(id);
    }
}
