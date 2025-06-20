package com.example.emailservice.repository;

import com.example.emailservice.model.Lesson;
import com.example.emailservice.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

/**
 * Repository pour la gestion des leçons en base de données
 * 
 * Cette interface étend JpaRepository pour bénéficier des opérations CRUD de base :
 * - save() : Sauvegarder une leçon
 * - findById() : Trouver une leçon par son ID
 * - findAll() : Récupérer toutes les leçons
 * - delete() : Supprimer une leçon
 * - etc.
 * 
 * Spring Data JPA génère automatiquement l'implémentation de cette interface.
 */
public interface LessonRepository extends JpaRepository<Lesson, Long> {
    
    /**
     * Trouve toutes les leçons appartenant à un utilisateur spécifique
     * 
     * Spring Data JPA génère automatiquement la requête SQL basée sur le nom de la méthode.
     * La requête sera : SELECT * FROM lessons WHERE user_id = ?
     * 
     * @param user L'utilisateur dont on veut récupérer les leçons
     * @return Une liste des leçons appartenant à cet utilisateur
     */
    List<Lesson> findByUser(User user);
}


