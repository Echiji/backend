package com.example.emailservice.service;

import com.example.emailservice.model.Course;
import com.example.emailservice.model.User;
import com.example.emailservice.repository.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

/**
 * Service pour la gestion des cours
 * 
 * Fournit les méthodes métier pour créer, lire, modifier et supprimer des cours.
 * Gère la logique métier et les validations.
 */
@Service
public class CourseService {
    
    @Autowired
    private CourseRepository courseRepository;
    
    @Autowired
    private UserService userService;
    
    /**
     * Crée un nouveau cours
     * 
     * @param course Le cours à créer
     * @param userId L'ID de l'utilisateur propriétaire
     * @return Le cours créé
     * @throws RuntimeException si l'utilisateur n'existe pas ou si le titre existe déjà
     */
    public Course createCourse(Course course) {
        User user = userService.getCurrentUser();
        course.setUser(user);
        // Vérifier que le titre n'existe pas déjà pour cet utilisateur
        if (courseRepository.existsByTitleAndUser(course.getTitle(), user)) {
            throw new RuntimeException("Un cours avec ce titre existe déjà pour cet utilisateur");
        }
        
        return courseRepository.save(course);
    }
    
    /**
     * Récupère tous les cours
     * 
     * @return Liste de tous les cours
     */
    public List<Course> getAllCourses() {
        return courseRepository.findAll();
    }
    
    /**
     * Récupère un cours par son ID
     * 
     * @param id L'ID du cours
     * @return Le cours trouvé ou null
     */
    public Course getCourseById(Long id) {
        Optional<Course> courseOptional = courseRepository.findById(id);
        return courseOptional.orElse(null);
    }
    
    /**
     * Récupère tous les cours d'un utilisateur
     * 
     * @param userId L'ID de l'utilisateur
     * @return Liste des cours de l'utilisateur
     */
    public List<Course> getCoursesByUserId(Long userId) {
        return courseRepository.findByUserId(userId);
    }
    
    /**
     * Met à jour un cours existant
     * 
     * @param id L'ID du cours à mettre à jour
     * @param courseDetails Les nouvelles données du cours
     * @return Le cours mis à jour
     * @throws RuntimeException si le cours n'existe pas
     */
    public Course updateCourse(Long id, Course courseDetails) {
        Optional<Course> courseOptional = courseRepository.findById(id);
        if (courseOptional.isEmpty()) {
            throw new RuntimeException("Cours non trouvé avec l'ID: " + id);
        }
        
        Course existingCourse = courseOptional.get();
        
        // Mettre à jour les champs
        existingCourse.setTitle(courseDetails.getTitle());
        existingCourse.setTypeCourse(courseDetails.getTypeCourse());
        
        // Vérifier que le nouveau titre n'existe pas déjà pour cet utilisateur
        if (!existingCourse.getTitle().equals(courseDetails.getTitle()) &&
            courseRepository.existsByTitleAndUser(courseDetails.getTitle(), existingCourse.getUser())) {
            throw new RuntimeException("Un cours avec ce titre existe déjà pour cet utilisateur");
        }
        
        return courseRepository.save(existingCourse);
    }
    
    /**
     * Supprime un cours
     * 
     * @param id L'ID du cours à supprimer
     * @throws RuntimeException si le cours n'existe pas
     */
    public void deleteCourse(Long id) {
        Optional<Course> courseOptional = courseRepository.findById(id);
        if (courseOptional.isEmpty()) {
            throw new RuntimeException("Cours non trouvé avec l'ID: " + id);
        }
        
        courseRepository.deleteById(id);
    }
    
    /**
     * Recherche des cours par titre
     * 
     * @param title Le titre à rechercher
     * @return Liste des cours contenant le titre
     */
    public List<Course> searchCoursesByTitle(String title) {
        return courseRepository.findByTitleContainingIgnoreCase(title);
    }
    
    /**
     * Recherche des cours d'un utilisateur par titre
     * 
     * @param title Le titre à rechercher
     * @param userId L'ID de l'utilisateur
     * @return Liste des cours de l'utilisateur contenant le titre
     */
    public List<Course> searchCoursesByTitleAndUser(String title) {
        User user = userService.getCurrentUser();
        return courseRepository.findByTitleContainingIgnoreCaseAndUser(title, user);
    }
    
    /**
     * Vérifie si un cours existe
     * 
     * @param id L'ID du cours
     * @return true si le cours existe, false sinon
     */
    public boolean courseExists(Long id) {
        return courseRepository.existsById(id);
    }
    
    /**
     * Vérifie si un utilisateur est propriétaire d'un cours
     * 
     * @param courseId L'ID du cours
     * @param userId L'ID de l'utilisateur
     * @return true si l'utilisateur est propriétaire, false sinon
     */
    public boolean isUserOwnerOfCourse(Long courseId, Long userId) {
        Optional<Course> courseOptional = courseRepository.findById(courseId);
        if (courseOptional.isEmpty()) {
            return false;
        }
        
        Course course = courseOptional.get();
        return course.getUser().getId().equals(userId);
    }
} 