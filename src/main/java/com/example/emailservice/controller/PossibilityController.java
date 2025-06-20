package com.example.emailservice.controller;

import com.example.emailservice.model.Possibility;
import com.example.emailservice.model.Question;
import com.example.emailservice.service.PossibilityService;
import com.example.emailservice.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/possibilities")
@CrossOrigin(origins = "*")
public class PossibilityController {
    @Autowired
    private PossibilityService possibilityService;
    @Autowired
    private QuestionService questionService;

    @GetMapping("/questions/{questionId}")
    public ResponseEntity<List<Possibility>> getPossibilitiesByQuestion(@PathVariable Long questionId) {
        Question question = questionService.getQuestion(questionId).orElseThrow(() -> new RuntimeException("Question not found"));
        List<Possibility> possibilities = possibilityService.getPossibilitiesByQuestion(question);
        return ResponseEntity.ok(possibilities);
    }

    @PostMapping
    public ResponseEntity<Possibility> createPossibility(@RequestBody Possibility possibility) {
        // logs ici
        System.out.println("Possibility reçue: " + possibility);
        System.out.println("Question dans possibility: " + possibility.getQuestion());
        if (possibility.getQuestion() != null) {
            System.out.println("ID de la question: " + possibility.getQuestion().getId());
        }
        Possibility created = possibilityService.createPossibility(possibility);
        return ResponseEntity.ok(created);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Possibility> getPossibility(@PathVariable Long id) {
        return possibilityService.getPossibility(id)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Possibility> updatePossibility(@PathVariable Long id, @RequestBody Possibility possibility) {
        try {
            Possibility updated = possibilityService.updatePossibility(id, possibility);
            return ResponseEntity.ok(updated);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePossibility(@PathVariable Long id) {
        possibilityService.deletePossibility(id);
        return ResponseEntity.ok().build();
    }
} 