package com.example.emailservice.controller;

import com.example.emailservice.model.Lesson;
import com.example.emailservice.service.LessonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.emailservice.dto.LessonDTO;
import com.example.emailservice.mapper.LessonMapper;


import java.util.List;

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
    
    @Autowired
    private LessonMapper lessonMapper;
    
    @GetMapping
    public ResponseEntity<List<LessonDTO>> getAllLessons() {
        List<Lesson> lessons = lessonService.getAllLessons();
        List<LessonDTO> lessonDTOs = lessonMapper.toLessonDTOList(lessons);
        return ResponseEntity.ok(lessonDTOs);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<LessonDTO> getLessonById(@PathVariable Long id) {
        Lesson lesson = lessonService.getLessonById(id);
        if (lesson != null) {
            LessonDTO lessonDTO = lessonMapper.toLessonDTO(lesson);
            return ResponseEntity.ok(lessonDTO);
        }
        return ResponseEntity.notFound().build();
    }
    
    @GetMapping("/course/{courseId}")
    public ResponseEntity<List<LessonDTO>> getLessonsByCourseId(@PathVariable Long courseId) {
        List<Lesson> lessons = lessonService.getLessonsByCourseId(courseId);
        List<LessonDTO> lessonDTOs = lessonMapper.toLessonDTOList(lessons);
        return ResponseEntity.ok(lessonDTOs);
    }
    
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<LessonDTO>> getLessonsByUserId(@PathVariable Long userId) {
        List<Lesson> lessons = lessonService.getLessonsByUserId(userId);
        List<LessonDTO> lessonDTOs = lessonMapper.toLessonDTOList(lessons);
        return ResponseEntity.ok(lessonDTOs);
    }
    
    @PostMapping
    public ResponseEntity<LessonDTO> createLesson(@RequestBody Lesson lesson) {
        Lesson savedLesson = lessonService.createLesson(lesson);
        LessonDTO lessonDTO = lessonMapper.toLessonDTO(savedLesson);
        return ResponseEntity.ok(lessonDTO);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<LessonDTO> updateLesson(@PathVariable Long id, @RequestBody Lesson lesson) {
        Lesson updatedLesson = lessonService.updateLesson(id, lesson);
        if (updatedLesson != null) {
            LessonDTO lessonDTO = lessonMapper.toLessonDTO(updatedLesson);
            return ResponseEntity.ok(lessonDTO);
        }
        return ResponseEntity.notFound().build();
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLesson(@PathVariable Long id) {
        lessonService.deleteLesson(id);
        return ResponseEntity.ok().build();
    }
}
