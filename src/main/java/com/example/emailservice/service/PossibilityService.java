package com.example.emailservice.service;

import com.example.emailservice.model.Possibility;
import com.example.emailservice.model.Question;
import com.example.emailservice.repository.PossibilityRepository;
import com.example.emailservice.exception.LessonException;
import com.example.emailservice.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class PossibilityService {
    @Autowired
    private PossibilityRepository possibilityRepository;
    @Autowired
    private QuestionService questionService;
    @Autowired
    private UserService userService;

    public List<Possibility> getPossibilitiesByQuestion(Question question) {
        return possibilityRepository.findByQuestion(question);
    }

    public Possibility createPossibility(Possibility possibility) {
        // Ajoute ces logs pour débuguer :
        System.out.println("Possibility reçue: " + possibility);
        System.out.println("Question dans possibility: " + possibility.getQuestion());
        if (possibility.getQuestion() != null) {
            System.out.println("ID de la question: " + possibility.getQuestion().getId());
        }

        // Validation de l'input
        if (possibility.getQuestion() == null || possibility.getQuestion().getId() == null) {
            throw new IllegalArgumentException("L'objet Question et son ID ne peuvent pas être nuls");
        }

        // 1. Récupérer la question complète pour avoir accès à la leçon et à l'utilisateur
        Question question = questionService.getQuestion(possibility.getQuestion().getId())
            .orElseThrow(() -> new RuntimeException("Question non trouvée avec l'ID: " + possibility.getQuestion().getId()));

        // 2. Vérification de sécurité : l'utilisateur connecté est-il le propriétaire ?
        User currentUser = userService.getCurrentUser();
        if (!question.getLesson().getUser().getId().equals(currentUser.getId())) {
            throw new LessonException("Vous n'êtes pas autorisé à ajouter une possibilité à cette question.");
        }
        
        // 3. Lier la possibilité à la question complète et sauvegarder
        possibility.setQuestion(question);
        return possibilityRepository.save(possibility);
    }

    public Optional<Possibility> getPossibility(Long id) {
        return possibilityRepository.findById(id);
    }

    public Possibility updatePossibility(Long id, Possibility possibilityDetails) {
        Possibility possibility = possibilityRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Possibility not found"));

        // Vérification de sécurité
        User currentUser = userService.getCurrentUser();
        if (!possibility.getQuestion().getLesson().getUser().getId().equals(currentUser.getId())) {
            throw new LessonException("Vous n'êtes pas autorisé à modifier cette possibilité");
        }

        possibility.setValue(possibilityDetails.getValue());
        return possibilityRepository.save(possibility);
    }

    public void deletePossibility(Long id) {
        // Optionnel : ajouter une vérification de sécurité ici aussi
        possibilityRepository.deleteById(id);
    }
} 