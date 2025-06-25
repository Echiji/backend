package com.example.emailservice.controller;

import com.example.emailservice.model.Lesson;
import com.example.emailservice.service.LessonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * Contrôleur pour la gestion des leçons
 * 
 * Ce contrôleur expose les endpoints REST pour :
 * - Récupérer les leçons d'un utilisateur
 * - Créer une nouvelle leçon
 * - Modifier une leçon existante
 * - Supprimer une leçon
 */
@RestController
@RequestMapping("/api/lessons")
@CrossOrigin(origins = "*") // Permet les requêtes CORS depuis n'importe quelle origine
public class LessonController {
    
    @Autowired
    private LessonService lessonService;
    

    @GetMapping
    public ResponseEntity<?> getLessons() {
        try {
            List<Lesson> lessons = lessonService.getLessonsByUser();
            return ResponseEntity.ok(lessons);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(Map.of("error", "Utilisateur non authentifié"));
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getLesson(@PathVariable Long id) {
        Lesson lesson = lessonService.getLessonById(id);
        return ResponseEntity.ok(lesson);
    }

    @GetMapping("/course/{courseId}")
    @CrossOrigin(origins = "*")
    public ResponseEntity<?> getLessonsByCourse(@PathVariable Long courseId) {
        try {
            List<Lesson> lessons = lessonService.getLessonsByCourse(courseId);
            return ResponseEntity.ok(lessons);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(Map.of("error", "Utilisateur non authentifié"));
        }
    }

    @PostMapping
    public ResponseEntity<Lesson> createLesson(@RequestBody Lesson lesson) {
        // course peut être null maintenant que vous avez supprimé la contrainte NOT NULL
        Lesson savedLesson = lessonService.createLesson(lesson);
        return new ResponseEntity<>(savedLesson, HttpStatus.CREATED);
    }

    @PostMapping("/courses/{courseId}")
    public ResponseEntity<Lesson> createLessonWithCourse(
        @PathVariable Long courseId,
        @RequestBody Lesson lesson
    ) {
        Lesson savedLesson = lessonService.createLessonWithCourse(courseId, lesson);
        return new ResponseEntity<>(savedLesson, HttpStatus.CREATED);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<?> updateLesson(@PathVariable Long id, @RequestBody Lesson lesson) {
        try {
            Lesson updatedLesson = lessonService.updateLesson(id, lesson);
            return ResponseEntity.ok(updatedLesson);
        } catch (RuntimeException e) {
            if (e.getMessage().contains("not found")) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("error", "Leçon non trouvée"));
            } else if (e.getMessage().contains("non autorisé")) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(Map.of("error", "Vous n'êtes pas autorisé à modifier cette leçon"));
            }
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(Map.of("error", "Utilisateur non authentifié"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(Map.of("error", "Erreur lors de la modification: " + e.getMessage()));
        }
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteLesson(@PathVariable Long id) {
        try {
            lessonService.deleteLesson(id);
            return ResponseEntity.ok().build();
        } catch (RuntimeException e) {
            if (e.getMessage().contains("not found")) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("error", "Leçon non trouvée"));
            } else if (e.getMessage().contains("non autorisé")) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(Map.of("error", "Vous n'êtes pas autorisé à supprimer cette leçon"));
            }
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(Map.of("error", "Utilisateur non authentifié"));
        }
    }
}
