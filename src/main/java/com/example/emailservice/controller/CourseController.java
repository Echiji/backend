package com.example.emailservice.controller;

import com.example.emailservice.model.Course;
import com.example.emailservice.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.emailservice.dto.CourseDTO;
import com.example.emailservice.mapper.CourseMapper;

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
    
    @Autowired
    private CourseMapper courseMapper;
    
    /**
     * Crée un nouveau cours
     * 
     * @param course Le cours à créer
     * @param userId L'ID de l'utilisateur propriétaire (paramètre de requête)
     * @return Le cours créé avec le statut 201 (Created)
     */
    @PostMapping
    public ResponseEntity<CourseDTO> createCourse(@RequestBody Course course) {
        Course savedCourse = courseService.createCourse(course);
        CourseDTO courseDTO = courseMapper.toCourseDTO(savedCourse);
        return ResponseEntity.ok(courseDTO);
    }
    
    /**
     * Récupère tous les cours
     * 
     * @return Liste de tous les cours avec le statut 200 (OK)
     */
    @GetMapping
    public ResponseEntity<List<CourseDTO>> getAllCourses() {
        List<Course> courses = courseService.getAllCourses();
        List<CourseDTO> courseDTOs = courseMapper.toCourseDTOList(courses);
        return ResponseEntity.ok(courseDTOs);
    }
    
    /**
     * Récupère un cours par son ID
     * 
     * @param id L'ID du cours
     * @return Le cours trouvé avec le statut 200 (OK) ou 404 (Not Found)
     */
    @GetMapping("/{id}")
    public ResponseEntity<CourseDTO> getCourseById(@PathVariable Long id) {
        Course course = courseService.getCourseById(id);
        if (course != null) {
            CourseDTO courseDTO = courseMapper.toCourseDTO(course);
            return ResponseEntity.ok(courseDTO);
        }
        return ResponseEntity.notFound().build();
    }
    
    /**
     * Récupère tous les cours d'un utilisateur
     * 
     * @param userId L'ID de l'utilisateur
     * @return Liste des cours de l'utilisateur avec le statut 200 (OK)
     */
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<CourseDTO>> getCoursesByUserId(@PathVariable Long userId) {
        List<Course> courses = courseService.getCoursesByUserId(userId);
        List<CourseDTO> courseDTOs = courseMapper.toCourseDTOList(courses);
        return ResponseEntity.ok(courseDTOs);
    }
    
    /**
     * Met à jour un cours existant
     * 
     * @param id L'ID du cours à mettre à jour
     * @param courseDetails Les nouvelles données du cours
     * @return Le cours mis à jour avec le statut 200 (OK) ou 404 (Not Found)
     */
    @PutMapping("/{id}")
    public ResponseEntity<CourseDTO> updateCourse(@PathVariable Long id, @RequestBody Course course) {
        Course updatedCourse = courseService.updateCourse(id, course);
        if (updatedCourse != null) {
            CourseDTO courseDTO = courseMapper.toCourseDTO(updatedCourse);
            return ResponseEntity.ok(courseDTO);
        }
        return ResponseEntity.notFound().build();
    }
    
    /**
     * Supprime un cours
     * 
     * @param id L'ID du cours à supprimer
     * @return Statut 204 (No Content) si supprimé avec succès ou 404 (Not Found)
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCourse(@PathVariable Long id) {
        courseService.deleteCourse(id);
        return ResponseEntity.ok().build();
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
            List<Course> courses = courseService.searchCoursesByTitleAndUser(title);
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