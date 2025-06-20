package com.example.emailservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Classe principale de l'application EmailService
 * 
 * Cette classe est le point d'entrée de l'application Spring Boot.
 * L'annotation @SpringBootApplication combine :
 * - @Configuration : Marque la classe comme source de définitions de beans
 * - @EnableAutoConfiguration : Active l'auto-configuration Spring Boot
 * - @ComponentScan : Active le scan des composants dans le package courant
 */
@SpringBootApplication
public class EmailServiceApplication {
    
    /**
     * Méthode principale qui démarre l'application Spring Boot
     * 
     * @param args Arguments de ligne de commande passés à l'application
     */
    public static void main(String[] args) {
        // Démarre l'application Spring Boot avec la classe courante
        SpringApplication.run(EmailServiceApplication.class, args);
    }
} 