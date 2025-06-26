package com.example.emailservice.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

/**
 * Configuration de la sécurité de l'application
 * 
 * Cette classe configure Spring Security pour :
 * - Désactiver CSRF (Cross-Site Request Forgery) pour les API REST
 * - Définir les règles d'autorisation pour les endpoints
 * - Intégrer le filtre JWT pour l'authentification
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {
    
    // Injection automatique du filtre JWT personnalisé
    @Autowired
    private JwtAuthFilter jwtAuthFilter;

    /**
     * Configuration de la chaîne de filtres de sécurité
     * 
     * Cette méthode définit :
     * - Les règles d'autorisation pour les endpoints
     * - L'intégration du filtre JWT
     * - La désactivation de CSRF
     * 
     * @param http L'objet HttpSecurity à configurer
     * @return La chaîne de filtres configurée
     * @throws Exception En cas d'erreur de configuration
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            // Désactivation de CSRF pour les API REST (stateless)
            .csrf(csrf -> csrf.disable())
            
            // Configuration des règles d'autorisation
            .authorizeHttpRequests(auth -> auth
                // Les endpoints d'authentification sont accessibles sans authentification
                .requestMatchers("/api/auth/**").permitAll()
                // Autoriser l'accès à une leçon spécifique sans authentification
                .requestMatchers("/api/lessons/*").permitAll()
                // Tous les autres endpoints nécessitent une authentification
                .anyRequest().authenticated()
            )
            
            // Ajout du filtre JWT avant le filtre d'authentification par défaut
            .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);
        
        return http.build();
    }
}
