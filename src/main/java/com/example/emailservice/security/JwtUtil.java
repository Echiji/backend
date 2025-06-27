package com.example.emailservice.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import java.security.Key;
import java.util.Date;

/**
 * Utilitaire pour la gestion des tokens JWT (JSON Web Tokens)
 * 
 * Cette classe fournit les méthodes pour :
 * - Générer des tokens JWT pour l'authentification
 * - Extraire le nom d'utilisateur depuis un token JWT
 * 
 * Les tokens JWT permettent de maintenir l'état d'authentification
 * côté client sans stocker d'informations sensibles.
 */
public class JwtUtil {
    
    /**
     * Clé secrète utilisée pour signer et vérifier les tokens JWT
     * ATTENTION : En production, cette clé devrait être stockée dans une variable d'environnement
     */
    private static final String SECRET_KEY = "mySecretKey1234567890123456789012345678901234567890";
    private static final Key key = Keys.hmacShaKeyFor(SECRET_KEY.getBytes());

    /**
     * Génère un token JWT pour un utilisateur
     * 
     * Le token contient :
     * - Le nom d'utilisateur (subject)
     * - La date de création (issuedAt)
     * - La date d'expiration (24 heures après création)
     * - La signature HMAC-SHA256
     * 
     * @param username Le nom d'utilisateur à inclure dans le token
     * @return Le token JWT généré sous forme de chaîne
     */
    public static String generateToken(String username) {
        return Jwts.builder()
                .setSubject(username) // Nom d'utilisateur dans le token
                .setIssuedAt(new Date()) // Date de création
                .setExpiration(new Date(System.currentTimeMillis() + 86400000)) // Expiration dans 24h (86400000 ms)
                .signWith(key, SignatureAlgorithm.HS256) // Signature avec la clé secrète
                .compact(); // Génération du token final
    }

    /**
     * Extrait le nom d'utilisateur depuis un token JWT
     * 
     * Cette méthode :
     * - Vérifie la signature du token
     * - Vérifie que le token n'est pas expiré
     * - Extrait le nom d'utilisateur (subject)
     * 
     * @param token Le token JWT à analyser
     * @return Le nom d'utilisateur extrait du token
     * @throws Exception Si le token est invalide, expiré ou malformé
     */
    public static String extractUsername(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key) // Utilisation de la clé secrète pour vérifier la signature
                .build()
                .parseClaimsJws(token) // Parse et vérifie le token
                .getBody() // Récupère le contenu du token
                .getSubject(); // Extrait le nom d'utilisateur
                
    }
}
