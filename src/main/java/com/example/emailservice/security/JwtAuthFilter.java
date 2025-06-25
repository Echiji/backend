package com.example.emailservice.security;

import com.example.emailservice.model.User;
import com.example.emailservice.repository.UserRepository;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;
import java.util.Optional;

/**
 * Filtre d'authentification JWT
 * 
 * Ce filtre intercepte toutes les requêtes HTTP et :
 * - Extrait le token JWT de l'en-tête Authorization
 * - Valide le token et extrait le nom d'utilisateur
 * - Vérifie que l'utilisateur existe en base de données
 * - Configure l'authentification Spring Security si le token est valide
 * 
 * Ce filtre s'exécute une fois par requête grâce à OncePerRequestFilter.
 */
@Component
public class JwtAuthFilter extends OncePerRequestFilter {

    // Injection automatique du repository utilisateur
    @Autowired
    private UserRepository userRepository;

    /**
     * Méthode principale du filtre qui traite chaque requête
     * 
     * @param request La requête HTTP entrante
     * @param response La réponse HTTP sortante
     * @param filterChain La chaîne de filtres à continuer
     * @throws ServletException En cas d'erreur de servlet
     * @throws IOException En cas d'erreur d'entrée/sortie
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        
        // Récupération de l'en-tête Authorization
        String authHeader = request.getHeader("Authorization");
        String token = null;
        String username = null;

        System.out.println("=== JWT Filter Start ===");
        System.out.println("Request URI: " + request.getRequestURI());
        System.out.println("Authorization header: " + authHeader);

        // Vérification de la présence et du format de l'en-tête Authorization
        if (StringUtils.hasText(authHeader) && authHeader.startsWith("Bearer ")) {
            // Extraction du token (suppression du préfixe "Bearer ")
            token = authHeader.substring(7);
            
            try {
                // Extraction du nom d'utilisateur depuis le token JWT
                username = JwtUtil.extractUsername(token);
                System.out.println("Username from JWT: " + username);
            } catch (JwtException e) {
                // En cas d'erreur JWT (token expiré, invalide, etc.)
                System.out.println("JWT Exception: " + e.getMessage());
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                return;
            }
        } else {
            System.out.println("No valid Authorization header found");
        }

        // Si un nom d'utilisateur a été extrait et qu'aucune authentification n'est déjà configurée
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            System.out.println("Setting up authentication for username: " + username);
            // Recherche de l'utilisateur en base de données
            Optional<User> userOpt = userRepository.findByUsername(username);
            System.out.println("User in DB: " + userOpt.isPresent());
            
            if (userOpt.isPresent()) {
                // Création du token d'authentification Spring Security
                UsernamePasswordAuthenticationToken authToken =
                        new UsernamePasswordAuthenticationToken(userOpt.get().getUsername(), null, Collections.emptyList());
                
                // Ajout des détails de la requête au token d'authentification
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                
                // Configuration de l'authentification dans le contexte de sécurité
                SecurityContextHolder.getContext().setAuthentication(authToken);
                System.out.println("Authentication set successfully");
            }
        } else {
            if (username == null) {
                System.out.println("Username is null, skipping authentication setup");
            } else {
                System.out.println("Authentication already exists, skipping setup");
            }
        }

        System.out.println("=== JWT Filter End ===");

        // Continuation de la chaîne de filtres
        filterChain.doFilter(request, response);
    }
}
