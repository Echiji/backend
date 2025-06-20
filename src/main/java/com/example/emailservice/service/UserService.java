package com.example.emailservice.service;

import com.example.emailservice.model.User;
import com.example.emailservice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

/**
 * Service de gestion des utilisateurs
 * 
 * Cette classe contient la logique métier pour les opérations sur les utilisateurs :
 * - Inscription d'un nouvel utilisateur
 * - Authentification d'un utilisateur
 * - Récupération de l'utilisateur connecté
 */
@Service
public class UserService {
    
    // Injection automatique du repository pour accéder à la base de données
    @Autowired
    private UserRepository userRepository;

    /**
     * Encodeur de mots de passe utilisant l'algorithme BCrypt
     * Permet de hasher les mots de passe de manière sécurisée
     */
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    /**
     * Enregistre un nouvel utilisateur
     * 
     * @param username Le nom d'utilisateur choisi
     * @param password Le mot de passe en clair (sera hashé)
     * @return L'utilisateur créé et sauvegardé en base
     */
    public User register(String username, String password) {
        // Création d'un nouvel utilisateur
        User user = new User();
        user.setUsername(username);
        
        // Hashage du mot de passe avant de le stocker
        user.setPassword(passwordEncoder.encode(password));
        
        // Sauvegarde en base de données
        return userRepository.save(user);
    }

    /**
     * Authentifie un utilisateur avec son nom d'utilisateur et mot de passe
     * 
     * @param username Le nom d'utilisateur
     * @param password Le mot de passe en clair
     * @return Un Optional contenant l'utilisateur si l'authentification réussit, sinon Optional.empty()
     */
    public Optional<User> authenticate(String username, String password) {
        // Recherche de l'utilisateur par son nom d'utilisateur
        Optional<User> userOpt = userRepository.findByUsername(username);
        System.out.println("User found: " + userOpt.isPresent());
        
        // Vérification du mot de passe si l'utilisateur existe
        if (userOpt.isPresent() && passwordEncoder.matches(password, userOpt.get().getPassword())) {
            return userOpt;
        }
        
        System.out.println("User not found");
        return Optional.empty();
    }

    /**
     * Récupère l'utilisateur actuellement connecté
     * 
     * @return L'utilisateur connecté
     * @throws RuntimeException Si aucun utilisateur n'est connecté ou si l'utilisateur n'existe plus en base
     */
    public User getCurrentUser() {
        // Récupération du nom d'utilisateur depuis le contexte de sécurité Spring
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        
        // Recherche de l'utilisateur en base de données
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }
}
