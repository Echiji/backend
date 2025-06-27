package com.example.emailservice.service;

import com.example.emailservice.model.Lesson;
import com.example.emailservice.model.Course;
import com.example.emailservice.model.User;
import com.example.emailservice.repository.LessonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Service de gestion des leçons
 * Cette classe contient la logique métier pour les opérations CRUD sur les leçons
 */
@Service
public class LessonService {
    
    @Autowired
    private CourseService courseService;

    // Injection automatique du repository pour accéder à la base de données
    @Autowired
    private LessonRepository lessonRepository;
    @Autowired
    private UserService userService;

    /**
     * Récupère toutes les leçons d'un utilisateur spécifique
     * @param user L'utilisateur dont on veut récupérer les leçons
     * @return Liste des leçons appartenant à cet utilisateur
     */
    public List<Lesson> getLessonsByUser() {
        User currentUser = userService.getCurrentUser();
        return lessonRepository.findByUser(currentUser);
    }

    /**
     * Crée une nouvelle leçon en base de données
     * @param lesson L'objet leçon à sauvegarder
     * @return La leçon créée avec son ID généré
     */
    public Lesson createLesson(Lesson lesson) {
        User currentUser = userService.getCurrentUser();
        lesson.setUser(currentUser);
        
        // Vérifier si courseId est fourni dans le JSON
        if (lesson.getCourseId() != null) {
            Course course = courseService.getCourseById(lesson.getCourseId());
            if (course == null) {
                throw new RuntimeException("Course not found with ID: " + lesson.getCourseId());
            }
            lesson.setCourse(course);
        } else {
            throw new RuntimeException("courseId is required");
        }
        
        return lessonRepository.save(lesson);
    }

    /**
     * Met à jour une leçon existante
     * @param id L'identifiant de la leçon à modifier
     * @param lesson Les nouvelles données de la leçon
     * @param currentUser L'utilisateur qui effectue la modification
     * @return La leçon mise à jour
     * @throws RuntimeException Si la leçon n'existe pas ou si l'utilisateur n'est pas autorisé
     */
    public Lesson updateLesson(Long id, Lesson lesson) {
        // Recherche de la leçon existante par son ID
        Optional<Lesson> existingLesson = lessonRepository.findById(id);
        
        if (existingLesson.isPresent()) {
            // Récupération de la leçon existante
            Lesson lessonToUpdate = existingLesson.get();
            User currentUser = userService.getCurrentUser();
            // Vérification que l'utilisateur est propriétaire de la leçon
            if (!lessonToUpdate.getUser().getId().equals(currentUser.getId())) {
                throw new RuntimeException("Vous n'êtes pas autorisé à modifier cette leçon");
            }
            
            // Mise à jour des propriétés de la leçon
            lessonToUpdate.setTitle(lesson.getTitle());
            lessonToUpdate.setDescription(lesson.getDescription());
            
            // Sauvegarde des modifications en base de données
            return lessonRepository.save(lessonToUpdate);
        }
        
        // Si la leçon n'existe pas, on lance une exception
        throw new RuntimeException("Lesson not found");
    }

    /**
     * Supprime une leçon de la base de données
     * @param id L'identifiant de la leçon à supprimer
     * @param currentUser L'utilisateur qui effectue la suppression
     * @throws RuntimeException Si la leçon n'existe pas ou si l'utilisateur n'est pas autorisé
     */
    public void deleteLesson(Long id) {
        User currentUser = userService.getCurrentUser();
        // Recherche de la leçon existante par son ID
        Optional<Lesson> existingLesson = lessonRepository.findById(id);
        
        if (existingLesson.isPresent()) {
            // Récupération de la leçon existante
            Lesson lessonToDelete = existingLesson.get();
            
            // Vérification que l'utilisateur est propriétaire de la leçon
            if (!lessonToDelete.getUser().getId().equals(currentUser.getId())) {
                throw new RuntimeException("Vous n'êtes pas autorisé à supprimer cette leçon");
            }
            
            // Suppression de la leçon
            lessonRepository.deleteById(id);
        } else {
            // Si la leçon n'existe pas, on lance une exception
            throw new RuntimeException("Lesson not found");
        }
    } 

    public Lesson getLessonById(Long id) {
        return lessonRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Lesson not found"));
    }

    public List<Lesson> getLessonsByCourse(Long courseId) {
        return lessonRepository.findByCourse_Id(courseId);
    }
    
    public Lesson createLessonWithCourse(Long courseId, Lesson lesson) {
        User currentUser = userService.getCurrentUser();
        Course course = courseService.getCourseById(courseId);
        lesson.setUser(currentUser);
        lesson.setCourse(course);
        return lessonRepository.save(lesson);
    }

    /**
     * Récupère toutes les leçons
     */
    public List<Lesson> getAllLessons() {
        return lessonRepository.findAll();
    }
    
    /**
     * Récupère les leçons par ID de cours
     */
    public List<Lesson> getLessonsByCourseId(Long courseId) {
        return lessonRepository.findByCourse_Id(courseId);
    }
    
    /**
     * Récupère les leçons par ID d'utilisateur
     */
    public List<Lesson> getLessonsByUserId(Long userId) {
        return lessonRepository.findByUser_Id(userId);
    }
}
