package com.example.emailservice.service;

import com.example.emailservice.model.Possibility;
import com.example.emailservice.model.Question;
import com.example.emailservice.repository.PossibilityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class PossibilityService {
    @Autowired
    private PossibilityRepository possibilityRepository;

    public List<Possibility> getPossibilitiesByQuestion(Question question) {
        return possibilityRepository.findByQuestion(question);
    }

    public Possibility createPossibility(Possibility possibility) {     
        return possibilityRepository.save(possibility);
    }

    public Optional<Possibility> getPossibility(Long id) {
        return possibilityRepository.findById(id);
    }

    public Possibility updatePossibility(Long id, Possibility possibilityDetails) {
        Possibility possibility = possibilityRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Possibility not found"));
        possibility.setValue(possibilityDetails.getValue());
        return possibilityRepository.save(possibility);
    }

    public void deletePossibility(Long id) {
        // Optionnel : ajouter une vérification de sécurité ici aussi
        possibilityRepository.deleteById(id);
    }
} 