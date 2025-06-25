package com.example.emailservice.repository;

import com.example.emailservice.model.Course;
import com.example.emailservice.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

/**
 * Repository pour l'entité Course
 * 
 * Fournit les méthodes d'accès aux données pour les cours.
 * Étend JpaRepository pour bénéficier des opérations CRUD de base.
 */
@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {
    
    /**
     * Trouve tous les cours appartenant à un utilisateur spécifique
     * 
     * @param user L'utilisateur propriétaire des cours
     * @return Liste des cours appartenant à l'utilisateur
     */
    List<Course> findByUser(User user);
    
    /**
     * Trouve tous les cours appartenant à un utilisateur par son ID
     * 
     * @param userId L'ID de l'utilisateur
     * @return Liste des cours appartenant à l'utilisateur
     */
    List<Course> findByUserId(Long userId);
    
    /**
     * Trouve un cours par son titre et son utilisateur
     * 
     * @param title Le titre du cours
     * @param user L'utilisateur propriétaire
     * @return Le cours correspondant ou null s'il n'existe pas
     */
    Course findByTitleAndUser(String title, User user);
    
    /**
     * Vérifie si un cours existe par son titre et son utilisateur
     * 
     * @param title Le titre du cours
     * @param user L'utilisateur propriétaire
     * @return true si le cours existe, false sinon
     */
    boolean existsByTitleAndUser(String title, User user);
    
    /**
     * Trouve tous les cours contenant le titre spécifié
     * 
     * @param title Le titre à rechercher (recherche partielle)
     * @return Liste des cours contenant le titre
     */
    List<Course> findByTitleContainingIgnoreCase(String title);
    
    /**
     * Trouve tous les cours d'un utilisateur contenant le titre spécifié
     * 
     * @param title Le titre à rechercher (recherche partielle)
     * @param user L'utilisateur propriétaire
     * @return Liste des cours de l'utilisateur contenant le titre
     */
    List<Course> findByTitleContainingIgnoreCaseAndUser(String title, User user);
} 