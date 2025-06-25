package com.example.emailservice.controller;

import com.example.emailservice.model.Course;
import com.example.emailservice.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Contrôleur REST pour la gestion des cours
 * 
 * Fournit les endpoints API pour créer, lire, modifier et supprimer des cours.
 * Tous les endpoints sont préfixés par "/api/courses".
 */
@RestController
@RequestMapping("/api/courses")
@CrossOrigin(origins = "*")
public class CourseController {
    
    @Autowired
    private CourseService courseService;
    
    /**
     * Crée un nouveau cours
     * 
     * @param course Le cours à créer
     * @param userId L'ID de l'utilisateur propriétaire (paramètre de requête)
     * @return Le cours créé avec le statut 201 (Created)
     */
    @PostMapping
    public ResponseEntity<Course> createCourse(@RequestBody Course course) {
        try {
            Course createdCourse = courseService.createCourse(course);
            return new ResponseEntity<>(createdCourse, HttpStatus.CREATED);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
    
    /**
     * Récupère tous les cours
     * 
     * @return Liste de tous les cours avec le statut 200 (OK)
     */
    @GetMapping
    public ResponseEntity<List<Course>> getAllCourses() {
        List<Course> courses = courseService.getAllCourses();
        return new ResponseEntity<>(courses, HttpStatus.OK);
    }
    
    /**
     * Récupère un cours par son ID
     * 
     * @param id L'ID du cours
     * @return Le cours trouvé avec le statut 200 (OK) ou 404 (Not Found)
     */
    @GetMapping("/{id}")
    public ResponseEntity<Course> getCourseById(@PathVariable Long id) {
        Course course = courseService.getCourseById(id);
        if (course != null) {
            return new ResponseEntity<>(course, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    
    /**
     * Récupère tous les cours d'un utilisateur
     * 
     * @param userId L'ID de l'utilisateur
     * @return Liste des cours de l'utilisateur avec le statut 200 (OK)
     */
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Course>> getCoursesByUserId(@PathVariable Long userId) {
        List<Course> courses = courseService.getCoursesByUserId(userId);
        return new ResponseEntity<>(courses, HttpStatus.OK);
    }
    
    /**
     * Met à jour un cours existant
     * 
     * @param id L'ID du cours à mettre à jour
     * @param courseDetails Les nouvelles données du cours
     * @return Le cours mis à jour avec le statut 200 (OK) ou 404 (Not Found)
     */
    @PutMapping("/{id}")
    public ResponseEntity<Course> updateCourse(@PathVariable Long id, @RequestBody Course courseDetails) {
        try {
            Course updatedCourse = courseService.updateCourse(id, courseDetails);
            return new ResponseEntity<>(updatedCourse, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    
    /**
     * Supprime un cours
     * 
     * @param id L'ID du cours à supprimer
     * @return Statut 204 (No Content) si supprimé avec succès ou 404 (Not Found)
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCourse(@PathVariable Long id) {
        try {
            courseService.deleteCourse(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    
    /**
     * Recherche des cours par titre
     * 
     * @param title Le titre à rechercher
     * @return Liste des cours contenant le titre avec le statut 200 (OK)
     */
    @GetMapping("/search")
    public ResponseEntity<List<Course>> searchCoursesByTitle(@RequestParam String title) {
        List<Course> courses = courseService.searchCoursesByTitle(title);
        return new ResponseEntity<>(courses, HttpStatus.OK);
    }
    
    /**
     * Recherche des cours d'un utilisateur par titre
     * 
     * @param title Le titre à rechercher
     * @param userId L'ID de l'utilisateur
     * @return Liste des cours de l'utilisateur contenant le titre avec le statut 200 (OK)
     */
    @GetMapping("/search/user/{userId}")
    public ResponseEntity<List<Course>> searchCoursesByTitleAndUser(
            @RequestParam String title, 
            @PathVariable Long userId) {
        try {
            List<Course> courses = courseService.searchCoursesByTitleAndUser(title, userId);
            return new ResponseEntity<>(courses, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
    
    /**
     * Vérifie si un cours existe
     * 
     * @param id L'ID du cours
     * @return true si le cours existe, false sinon avec le statut 200 (OK)
     */
    @GetMapping("/{id}/exists")
    public ResponseEntity<Boolean> courseExists(@PathVariable Long id) {
        boolean exists = courseService.courseExists(id);
        return new ResponseEntity<>(exists, HttpStatus.OK);
    }
    
    /**
     * Vérifie si un utilisateur est propriétaire d'un cours
     * 
     * @param courseId L'ID du cours
     * @param userId L'ID de l'utilisateur
     * @return true si l'utilisateur est propriétaire, false sinon avec le statut 200 (OK)
     */
    @GetMapping("/{courseId}/owner/{userId}")
    public ResponseEntity<Boolean> isUserOwnerOfCourse(
            @PathVariable Long courseId, 
            @PathVariable Long userId) {
        boolean isOwner = courseService.isUserOwnerOfCourse(courseId, userId);
        return new ResponseEntity<>(isOwner, HttpStatus.OK);
    }
} 