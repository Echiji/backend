package com.example.emailservice.controller;

import com.example.emailservice.model.User;
import com.example.emailservice.security.JwtUtil;
import com.example.emailservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

/**
 * Contrôleur pour la gestion de l'authentification
 * 
 * Ce contrôleur expose les endpoints REST pour :
 * - L'inscription d'un nouvel utilisateur
 * - La connexion d'un utilisateur existant
 * 
 * Tous les endpoints sont préfixés par "/api/auth"
 */
@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*") // Permet les requêtes CORS depuis n'importe quelle origine
public class AuthController {

    // Injection automatique du service utilisateur
    @Autowired
    private UserService userService;

    /**
     * Endpoint pour l'inscription d'un nouvel utilisateur
     * 
     * Méthode HTTP : POST
     * URL : /api/auth/register
     * 
     * @param request Map contenant "username", "password" et "email"
     * @return Réponse JSON avec l'ID et le nom d'utilisateur du nouvel utilisateur
     */
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody Map<String, String> request) {
        // Extraction des données de la requête
        String username = request.get("username");
        String password = request.get("password");
        
        // Création de l'utilisateur via le service
        User user = userService.register(username, password);
        
        // Retour de la réponse avec les informations de l'utilisateur créé
        return ResponseEntity.ok(Map.of("id", user.getId(), "username", user.getUsername()));
    }

    /**
     * Endpoint pour la connexion d'un utilisateur
     * 
     * Méthode HTTP : POST
     * URL : /api/auth/login
     * 
     * @param request Map contenant "username" et "password"
     * @return Réponse JSON avec le token JWT et les informations utilisateur si l'authentification réussit, sinon erreur 401
     */
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> request) {
        // Extraction des données de la requête
        String username = request.get("username");
        String password = request.get("password");
        
        // Tentative d'authentification via le service
        Optional<User> userOpt = userService.authenticate(username, password);
        
        if (userOpt.isPresent()) {
            // Si l'authentification réussit, génération d'un token JWT
            User user = userOpt.get();
            String token = JwtUtil.generateToken(username);
            
            // Retour du token et des informations utilisateur
            return ResponseEntity.ok(Map.of(
                "token", token,
                "user", Map.of(
                    "id", user.getId().toString(),
                    "username", user.getUsername()
                )
            ));
        }
        
        // Si l'authentification échoue, retour d'une erreur 401
        return ResponseEntity.status(401).body("Identifiants invalides");
    }
}