package com.example.emailservice.repository;

import com.example.emailservice.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

/**
 * Repository pour la gestion des utilisateurs en base de données
 * 
 * Cette interface étend JpaRepository pour bénéficier des opérations CRUD de base :
 * - save() : Sauvegarder un utilisateur
 * - findById() : Trouver un utilisateur par son ID
 * - findAll() : Récupérer tous les utilisateurs
 * - delete() : Supprimer un utilisateur
 * - etc.
 * 
 * Spring Data JPA génère automatiquement l'implémentation de cette interface.
 */
public interface UserRepository extends JpaRepository<User, Long> {
    
    /**
     * Trouve un utilisateur par son nom d'utilisateur
     * 
     * Spring Data JPA génère automatiquement la requête SQL basée sur le nom de la méthode.
     * La requête sera : SELECT * FROM app_user WHERE username = ?
     * 
     * @param username Le nom d'utilisateur à rechercher
     * @return Un Optional contenant l'utilisateur s'il existe, sinon Optional.empty()
     */
    Optional<User> findByUsername(String username);
}
