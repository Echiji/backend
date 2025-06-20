package com.example.emailservice.model;

import jakarta.persistence.*;

/**
 * Entité User représentant un utilisateur dans l'application
 * 
 * Cette classe est mappée à la table "app_user" en base de données.
 * Elle contient les informations de base d'un utilisateur :
 * - Identifiant unique
 * - Nom d'utilisateur (unique)
 * - Mot de passe (hashé)
 */
@Entity
@Table(name = "app_user")
public class User {
    
    /**
     * Identifiant unique de l'utilisateur
     * Généré automatiquement par la base de données
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Nom d'utilisateur unique
     * Contrainte d'unicité en base de données
     */
    @Column(unique = true)
    private String username;

    /**
     * Mot de passe de l'utilisateur
     * Doit être hashé avant d'être stocké en base
     */
    private String password;

    /**
     * Constructeur par défaut requis par JPA
     */
    public User() {
    }

    // ==================== GETTERS ET SETTERS ====================
    
    /**
     * Récupère l'identifiant de l'utilisateur
     * @return L'ID de l'utilisateur
     */
    public Long getId() {
        return id;
    }

    /**
     * Définit l'identifiant de l'utilisateur
     * @param id Le nouvel ID
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Récupère le nom d'utilisateur
     * @return Le nom d'utilisateur
     */
    public String getUsername() {
        return username;
    }

    /**
     * Définit le nom d'utilisateur
     * @param username Le nouveau nom d'utilisateur
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Récupère le mot de passe
     * @return Le mot de passe (hashé)
     */
    public String getPassword() {
        return password;
    }

    /**
     * Définit le mot de passe
     * @param password Le nouveau mot de passe (doit être hashé)
     */
    public void setPassword(String password) {
        this.password = password;
    }
}
