CREATE TABLE IF NOT EXISTS app_user (
    id SERIAL PRIMARY KEY,                    -- Identifiant unique auto-incrémenté
    username VARCHAR(255) UNIQUE NOT NULL,    -- Nom d'utilisateur unique
    password VARCHAR(255) NOT NULL            -- Mot de passe hashé (BCrypt)
);

INSERT INTO app_user ( username, password) VALUES ( 'jerome', '$2a$10$8GIwHfQb4cOitpzvrlvgg.GkqBdI.1HzjPPqPd7xI2oq3kIMqHHs6');
INSERT INTO app_user ( username, password) VALUES ( 'user', '$2a$10$8GIwHfQb4cOitpzvrlvgg.GkqBdI.1HzjPPqPd7xI2oq3kIMqHHs6');

-- ==================== CRÉATION DE LA TABLE COURSES ====================
-- Table pour stocker les cours créés par les utilisateurs
CREATE TABLE IF NOT EXISTS courses (
    id SERIAL PRIMARY KEY,                    -- Identifiant unique auto-incrémenté
    title VARCHAR(255) NOT NULL,
    type_course VARCHAR(50) NOT NULL,
    user_id INT NOT NULL,                     -- Référence vers l'utilisateur propriétaire
    FOREIGN KEY (user_id) REFERENCES app_user(id) ON DELETE CASCADE  -- Contrainte de clé étrangère avec CASCADE
);

CREATE TABLE IF NOT EXISTS lessons (
    id SERIAL PRIMARY KEY,                    -- Identifiant unique auto-incrémenté
    title VARCHAR(255) NOT NULL,              -- Titre de la leçon
    description TEXT NOT NULL,
    user_id INT NOT NULL,                     -- Référence vers l'utilisateur propriétaire
    course_id INT,                            -- Référence vers le cours (optionnel)
    FOREIGN KEY (user_id) REFERENCES app_user(id) ON DELETE CASCADE,  -- Contrainte de clé étrangère avec CASCADE
    FOREIGN KEY (course_id) REFERENCES courses(id) ON DELETE CASCADE  -- Contrainte de clé étrangère avec CASCADE
);

-- ==================== CRÉATION DE LA TABLE QUESTIONS ====================
CREATE TABLE IF NOT EXISTS question (
    id SERIAL PRIMARY KEY,
    question VARCHAR(255) NOT NULL,
    type_question VARCHAR(50) NOT NULL,
    answer TEXT NOT NULL,
    lesson_id INT NOT NULL,
    FOREIGN KEY (lesson_id) REFERENCES lessons(id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS possibility (
    id SERIAL PRIMARY KEY,
    possibility VARCHAR(255) NOT NULL,
    question_id INT NOT NULL,
    FOREIGN KEY (question_id) REFERENCES question(id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS test_controle (
    id SERIAL PRIMARY KEY,                    -- Identifiant unique auto-incrémenté
    nb_bonne_reponse INT NOT NULL,
    nb_question INT NOT NULL,                 -- Nombre total de questions
    date_creation TIMESTAMP DEFAULT CURRENT_TIMESTAMP, -- Date de création du test
    lesson_id INT NOT NULL,                   -- Référence vers la leçon
    user_id INT NOT NULL,                     -- Référence vers l'utilisateur
    FOREIGN KEY (lesson_id) REFERENCES lessons(id) ON DELETE CASCADE,  -- Contrainte de clé étrangère avec CASCADE
    FOREIGN KEY (user_id) REFERENCES app_user(id) ON DELETE CASCADE    -- Contrainte de clé étrangère avec CASCADE
);

-- ==================== COURS API REST ====================
-- Insertion du cours API REST
INSERT INTO courses (title, type_course, user_id) VALUES ('API REST - Maîtrisez les Web Services', 'API_REST', 1);

-- Insertion des leçons du cours API REST
INSERT INTO lessons (title, description, user_id, course_id) VALUES 
('Introduction aux API REST', '# Cours complet : Développement d API REST avec Java Spring Boot

## Table des matières
1. [Introduction aux API REST](#1-introduction-aux-api-rest)
2. [Architecture Spring Boot](#2-architecture-spring-boot)
3. [Conception de base de données](#3-conception-de-base-de-données)
4. [Interface utilisateur avec Vue.js 3](#4-interface-utilisateur-avec-vuejs-3)
5. [API avec Python Flask](#5-api-avec-python-flask)

---

## 1. Introduction aux API REST

### 1.1 Qu est-ce qu une API REST ?

Une API REST (Representational State Transfer) est un style d architecture pour les systèmes distribués, particulièrement adapté aux applications web. REST définit un ensemble de contraintes et de propriétés basées sur le protocole HTTP.

**Définition :** REST est un style architectural qui utilise les standards du web (HTTP, URI, JSON) pour créer des services web évolutifs et interopérables.

### 1.2 Principes fondamentaux

#### 1.2.1 Stateless (Sans état)
Chaque requête contient toutes les informations nécessaires pour être comprise et traitée. Le serveur ne conserve aucun état entre les requêtes.

**Exemple :**
```http
GET /api/users/123 HTTP/1.1
Host: api.example.com
Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...
```

#### 1.2.2 Client-Server
Séparation claire entre le client et le serveur. Le client ne connaît pas les détails d implémentation du serveur.

#### 1.2.3 Cacheable
Les réponses peuvent être mises en cache pour améliorer les performances.

```http
HTTP/1.1 200 OK
Cache-Control: max-age=3600
ETag: "abc123"
```

#### 1.2.4 Uniform Interface
Interface uniforme pour toutes les interactions :
- **Identification des ressources** : URI
- **Manipulation des ressources** : HTTP methods
- **Messages auto-descriptifs** : Headers et body
- **HATEOAS** : Hypermedia as the Engine of Application State

#### 1.2.5 Layered System
Architecture en couches permettant la scalabilité et la sécurité.

#### 1.2.6 Code on Demand
Possibilité d envoyer du code exécutable (optionnel).

### 1.3 Méthodes HTTP

#### 1.3.1 GET
- **Usage** : Récupérer des données
- **Idempotent** : Oui
- **Sécurisé** : Oui
- **Exemple** : `GET /api/users`

```http
GET /api/users HTTP/1.1
Host: api.example.com
Accept: application/json
```

#### 1.3.2 POST
- **Usage** : Créer une nouvelle ressource
- **Idempotent** : Non
- **Sécurisé** : Non
- **Exemple** : `POST /api/users`

```http
POST /api/users HTTP/1.1
Host: api.example.com
Content-Type: application/json

{
  "username": "john_doe",
  "email": "john@example.com",
  "password": "secure123"
}
```

#### 1.3.3 PUT
- **Usage** : Mettre à jour une ressource complète
- **Idempotent** : Oui
- **Sécurisé** : Non
- **Exemple** : `PUT /api/users/123`

```http
PUT /api/users/123 HTTP/1.1
Host: api.example.com
Content-Type: application/json

{
  "username": "john_doe",
  "email": "john.updated@example.com",
  "firstName": "John",
  "lastName": "Doe"
}
```

#### 1.3.4 PATCH
- **Usage** : Mettre à jour partiellement une ressource
- **Idempotent** : Non
- **Sécurisé** : Non
- **Exemple** : `PATCH /api/users/123`

```http
PATCH /api/users/123 HTTP/1.1
Host: api.example.com
Content-Type: application/json

{
  "email": "john.new@example.com"
}
```

#### 1.3.5 DELETE
- **Usage** : Supprimer une ressource
- **Idempotent** : Oui
- **Sécurisé** : Non
- **Exemple** : `DELETE /api/users/123`

```http
DELETE /api/users/123 HTTP/1.1
Host: api.example.com
Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...
```

### 1.4 Codes de statut HTTP

#### 1.4.1 2xx - Succès
- **200 OK** : Requête réussie
- **201 Created** : Ressource créée
- **204 No Content** : Succès sans contenu

#### 1.4.2 4xx - Erreur client
- **400 Bad Request** : Requête malformée
- **401 Unauthorized** : Authentification requise
- **403 Forbidden** : Accès interdit
- **404 Not Found** : Ressource non trouvée
- **409 Conflict** : Conflit de données

#### 1.4.3 5xx - Erreur serveur
- **500 Internal Server Error** : Erreur interne
- **502 Bad Gateway** : Erreur de passerelle
- **503 Service Unavailable** : Service indisponible

### 1.5 Bonnes pratiques

#### 1.5.1 Structure des URLs
```
GET    /api/users          # Liste des utilisateurs
GET    /api/users/123      # Utilisateur spécifique
POST   /api/users          # Créer un utilisateur
PUT    /api/users/123      # Mettre à jour un utilisateur
DELETE /api/users/123      # Supprimer un utilisateur
```

#### 1.5.2 Headers importants
- `Content-Type: application/json`
- `Accept: application/json`
- `Authorization: Bearer <token>`
- `Cache-Control: max-age=3600`

#### 1.5.3 Format des réponses
```json
{
  "success": true,
  "data": {
    "id": 123,
    "name": "John Doe",
    "email": "john@example.com"
  },
  "message": "Utilisateur récupéré avec succès"
}
```

### 1.6 Exemple pratique

#### 1.6.1 Requête GET
```bash
curl -X GET \
  http://localhost:8080/api/users \
  -H  Accept: application/json  \
  -H  Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9... 
```

#### 1.6.2 Réponse
```json
{
  "success": true,
  "data": [
    {
      "id": 1,
      "name": "Alice",
      "email": "alice@example.com"
    },
    {
      "id": 2,
      "name": "Bob",
      "email": "bob@example.com"
    }
  ],
  "message": "Utilisateurs récupérés avec succès"
}
```

---

## 2. Architecture Spring Boot

### 2.1 Introduction à Spring Boot

Spring Boot est un framework Java qui simplifie le développement d applications Spring en fournissant une configuration automatique et des starters prêts à l emploi.

**Avantages :**
- Configuration automatique
- Serveur embarqué
- Starters prêts à l emploi
- Monitoring intégré
- Déploiement simplifié

### 2.2 Structure du projet

```
src/main/java/com/example/api/
├── controller/     # Contrôleurs REST
├── service/        # Logique métier
├── repository/     # Accès aux données
├── model/          # Entités JPA
├── dto/           # Objets de transfert
├── mapper/        # Mappers (MapStruct)
├── config/        # Configuration
├── exception/     # Gestion d erreurs
└── security/      # Sécurité
```

### 2.3 Configuration initiale

#### 2.3.1 pom.xml - Dépendances essentielles
```xml
<dependencies>
    <!-- Spring Boot Starter Web -->
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-web</artifactId>
    </dependency>
    
    <!-- Spring Boot Starter Data JPA -->
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-data-jpa</artifactId>
    </dependency>
    
    <!-- Spring Boot Starter Validation -->
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-validation</artifactId>
    </dependency>
    
    <!-- Spring Boot Starter Security -->
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-security</artifactId>
    </dependency>
    
    <!-- PostgreSQL Driver -->
    <dependency>
        <groupId>org.postgresql</groupId>
        <artifactId>postgresql</artifactId>
        <scope>runtime</scope>
    </dependency>
    
    <!-- Lombok -->
    <dependency>
        <groupId>org.projectlombok</groupId>
        <artifactId>lombok</artifactId>
        <optional>true</optional>
    </dependency>
    
    <!-- MapStruct -->
    <dependency>
        <groupId>org.mapstruct</groupId>
        <artifactId>mapstruct</artifactId>
        <version>1.5.5.Final</version>
    </dependency>
</dependencies>
```

#### 2.3.2 application.properties
```properties
# Configuration de la base de données
spring.datasource.url=jdbc:postgresql://localhost:5432/rest_api_db
spring.datasource.username=postgres
spring.datasource.password=password
spring.datasource.driver-class-name=org.postgresql.Driver

# Configuration JPA
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.PostgreSQLDialect
spring.jpa.properties.hibernate.format_sql=true

# Configuration du serveur
server.port=8080
server.servlet.context-path=/api

# Configuration des logs
logging.level.com.example.api=DEBUG
logging.pattern.console=%d{yyyy-MM-dd HH:mm:ss} - %msg%n

# Configuration JWT
app.jwt.secret=your-secret-key-here
app.jwt.expiration=86400000
```

### 2.4 Modèle de données

#### 2.4.1 Entité User
```java
package com.example.api.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotBlank(message = "Le nom d utilisateur est obligatoire")
    @Size(min = 3, max = 50, message = "Le nom d utilisateur doit contenir entre 3 et 50 caractères")
    @Column(unique = true, nullable = false)
    private String username;
    
    @NotBlank(message = "L email est obligatoire")
    @Email(message = "Format d email invalide")
    @Column(unique = true, nullable = false)
    private String email;
    
    @NotBlank(message = "Le mot de passe est obligatoire")
    @Size(min = 6, message = "Le mot de passe doit contenir au moins 6 caractères")
    private String password;
    
    @Column(name = "first_name")
    private String firstName;
    
    @Column(name = "last_name")
    private String lastName;
    
    @Column(name = "is_active")
    private Boolean isActive = true;
    
    @Column(name = "created_at")
    private LocalDateTime createdAt;
    
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    
    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }
    
    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}
```

#### 2.4.2 Entité Product
```java
package com.example.api.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "products")
@Data
public class Product {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotBlank(message = "Le nom du produit est obligatoire")
    @Column(nullable = false)
    private String name;
    
    @Column(columnDefinition = "TEXT")
    private String description;
    
    @NotNull(message = "Le prix est obligatoire")
    @DecimalMin(value = "0.01", message = "Le prix doit être supérieur à 0")
    @Column(nullable = false)
    private BigDecimal price;
    
    @Column(name = "stock_quantity")
    private Integer stockQuantity = 0;
    
    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;
    
    @ManyToOne
    @JoinColumn(name = "created_by")
    private User createdBy;
    
    @Column(name = "is_active")
    private Boolean isActive = true;
    
    @Column(name = "created_at")
    private LocalDateTime createdAt;
    
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    
    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }
    
    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}
```

### 2.5 DTO (Data Transfer Objects)

#### 2.5.1 UserDTO
```java
package com.example.api.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UserDTO {
    private Long id;
    
    @NotBlank(message = "Le nom d utilisateur est obligatoire")
    @Size(min = 3, max = 50, message = "Le nom d utilisateur doit contenir entre 3 et 50 caractères")
    private String username;
    
    @NotBlank(message = "L email est obligatoire")
    @Email(message = "Format d email invalide")
    private String email;
    
    @NotBlank(message = "Le mot de passe est obligatoire")
    @Size(min = 6, message = "Le mot de passe doit contenir au moins 6 caractères")
    private String password;
    
    private String firstName;
    private String lastName;
    private Boolean isActive;
}
```

#### 2.5.2 ApiResponse
```java
package com.example.api.dto;

import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApiResponse<T> {
    private boolean success;
    private String message;
    private T data;
    
    public static <T> ApiResponse<T> success(T data, String message) {
        return new ApiResponse<>(true, message, data);
    }
    
    public static <T> ApiResponse<T> error(String message) {
        return new ApiResponse<>(false, message, null);
    }
    
    public static <T> ApiResponse<T> error(String message, T data) {
        return new ApiResponse<>(false, message, data);
    }
}
```

### 2.6 Repository

#### 2.6.1 UserRepository
```java
package com.example.api.repository;

import com.example.api.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    
    Optional<User> findByUsername(String username);
    
    Optional<User> findByEmail(String email);
    
    boolean existsByUsername(String username);
    
    boolean existsByEmail(String email);
    
    @Query("SELECT u FROM User u WHERE u.isActive = true")
    List<User> findAllActive();
    
    @Query("SELECT u FROM User u WHERE u.username LIKE %:keyword% OR u.email LIKE %:keyword%")
    List<User> findByUsernameOrEmailContaining(@Param("keyword") String keyword);
    
    @Query("SELECT u FROM User u WHERE u.createdAt >= :startDate")
    List<User> findUsersCreatedAfter(@Param("startDate") LocalDateTime startDate);
}
```

### 2.7 Service

#### 2.7.1 UserService
```java
package com.example.api.service;

import com.example.api.dto.UserDTO;
import com.example.api.model.User;
import com.example.api.repository.UserRepository;
import com.example.api.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class UserService {
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @Autowired
    private UserMapper userMapper;
    
    public List<UserDTO> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(userMapper::toUserDTO)
                .collect(Collectors.toList());
    }
    
    public Optional<UserDTO> getUserById(Long id) {
        return userRepository.findById(id)
                .map(userMapper::toUserDTO);
    }
    
    public UserDTO createUser(UserDTO userDTO) {
        // Validation
        if (userRepository.existsByUsername(userDTO.getUsername())) {
            throw new RuntimeException("Nom d utilisateur déjà utilisé");
        }
        
        if (userRepository.existsByEmail(userDTO.getEmail())) {
            throw new RuntimeException("Email déjà utilisé");
        }
        
        // Création de l utilisateur
        User user = userMapper.toUser(userDTO);
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        user.setIsActive(true);
        
        User savedUser = userRepository.save(user);
        return userMapper.toUserDTO(savedUser);
    }
    
    public UserDTO updateUser(Long id, UserDTO userDTO) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));
        
        // Mise à jour des champs
        user.setUsername(userDTO.getUsername());
        user.setEmail(userDTO.getEmail());
        user.setFirstName(userDTO.getFirstName());
        user.setLastName(userDTO.getLastName());
        
        // Mise à jour du mot de passe si fourni
        if (userDTO.getPassword() != null && !userDTO.getPassword().isEmpty()) {
            user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        }
        
        User updatedUser = userRepository.save(user);
        return userMapper.toUserDTO(updatedUser);
    }
    
    public void deleteUser(Long id) {
        if (!userRepository.existsById(id)) {
            throw new RuntimeException("Utilisateur non trouvé");
        }
        userRepository.deleteById(id);
    }
    
    public void deactivateUser(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));
        user.setIsActive(false);
        userRepository.save(user);
    }
    
    public List<UserDTO> searchUsers(String keyword) {
        return userRepository.findByUsernameOrEmailContaining(keyword)
                .stream()
                .map(userMapper::toUserDTO)
                .collect(Collectors.toList());
    }
}
```

### 2.8 Contrôleur REST

#### 2.8.1 UserController
```java
package com.example.api.controller;

import com.example.api.dto.ApiResponse;
import com.example.api.dto.UserDTO;
import com.example.api.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@CrossOrigin(origins = "*")
public class UserController {
    
    @Autowired
    private UserService userService;
    
    @GetMapping
    public ResponseEntity<ApiResponse<List<UserDTO>>> getAllUsers() {
        List<UserDTO> users = userService.getAllUsers();
        return ResponseEntity.ok(ApiResponse.success(users, "Utilisateurs récupérés avec succès"));
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<UserDTO>> getUserById(@PathVariable Long id) {
        return userService.getUserById(id)
                .map(user -> ResponseEntity.ok(ApiResponse.success(user, "Utilisateur récupéré avec succès")))
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(ApiResponse.error("Utilisateur non trouvé")));
    }
    
    @PostMapping
    public ResponseEntity<ApiResponse<UserDTO>> createUser(@Valid @RequestBody UserDTO userDTO) {
        try {
            UserDTO createdUser = userService.createUser(userDTO);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(ApiResponse.success(createdUser, "Utilisateur créé avec succès"));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest()
                    .body(ApiResponse.error(e.getMessage()));
        }
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<UserDTO>> updateUser(
            @PathVariable Long id,
            @Valid @RequestBody UserDTO userDTO) {
        try {
            UserDTO updatedUser = userService.updateUser(id, userDTO);
            return ResponseEntity.ok(ApiResponse.success(updatedUser, "Utilisateur mis à jour avec succès"));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest()
                    .body(ApiResponse.error(e.getMessage()));
        }
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteUser(@PathVariable Long id) {
        try {
            userService.deleteUser(id);
            return ResponseEntity.ok(ApiResponse.success(null, "Utilisateur supprimé avec succès"));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest()
                    .body(ApiResponse.error(e.getMessage()));
        }
    }
    
    @PatchMapping("/{id}/deactivate")
    public ResponseEntity<ApiResponse<Void>> deactivateUser(@PathVariable Long id) {
        try {
            userService.deactivateUser(id);
            return ResponseEntity.ok(ApiResponse.success(null, "Utilisateur désactivé avec succès"));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest()
                    .body(ApiResponse.error(e.getMessage()));
        }
    }
    
    @GetMapping("/search")
    public ResponseEntity<ApiResponse<List<UserDTO>>> searchUsers(@RequestParam String keyword) {
        List<UserDTO> users = userService.searchUsers(keyword);
        return ResponseEntity.ok(ApiResponse.success(users, "Recherche terminée"));
    }
}
```

### 2.9 Gestion globale des exceptions

#### 2.9.1 GlobalExceptionHandler
```java
package com.example.api.exception;

import com.example.api.dto.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {
    
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<Map<String, String>>> handleValidationExceptions(
            MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        
        return ResponseEntity.badRequest()
                .body(ApiResponse.error("Erreurs de validation", errors));
    }
    
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ApiResponse<Void>> handleRuntimeException(RuntimeException ex) {
        return ResponseEntity.badRequest()
                .body(ApiResponse.error(ex.getMessage()));
    }
    
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<Void>> handleGenericException(Exception ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ApiResponse.error("Erreur interne du serveur"));
    }
}
```

### 2.10 Sécurité avec JWT

#### 2.10.1 JwtUtil
```java
package com.example.api.security;

import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtUtil {
    
    @Value("${app.jwt.secret}")
    private String secret;
    
    @Value("${app.jwt.expiration}")
    private long expiration;
    
    public String generateToken(String username) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + expiration);
        
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
    }
    
    public String getUsernameFromToken(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(secret)
                .parseClaimsJws(token)
                .getBody();
        
        return claims.getSubject();
    }
    
    public boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(secret).parseClaimsJws(token);
            return true;
        } catch (SignatureException ex) {
            // Signature invalide
            return false;
        } catch (MalformedJwtException ex) {
            // Token malformé
            return false;
        } catch (ExpiredJwtException ex) {
            // Token expiré
            return false;
        } catch (UnsupportedJwtException ex) {
            // Token non supporté
            return false;
        } catch (IllegalArgumentException ex) {
            // Claims vide
            return false;
        }
    }
}
```

#### 2.10.2 JwtAuthFilter
```java
package com.example.api.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthFilter extends OncePerRequestFilter {
    
    @Autowired
    private JwtUtil jwtUtil;
    
    @Autowired
    private CustomUserDetailsService userDetailsService;
    
    @Override
    protected void doFilterInternal(HttpServletRequest request, 
                                  HttpServletResponse response, 
                                  FilterChain filterChain) 
            throws ServletException, IOException {
        
        final String authorizationHeader = request.getHeader("Authorization");
        
        String username = null;
        String jwt = null;
        
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            jwt = authorizationHeader.substring(7);
            username = jwtUtil.getUsernameFromToken(jwt);
        }
        
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);
            
            if (jwtUtil.validateToken(jwt)) {
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                    userDetails, null, userDetails.getAuthorities());
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }
        filterChain.doFilter(request, response);
    }
}
```

### 2.11 Tests

#### 2.11.1 Tests unitaires
```java
package com.example.api.service;

import com.example.api.dto.UserDTO;
import com.example.api.model.User;
import com.example.api.repository.UserRepository;
import com.example.api.mapper.UserMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {
    
    @Mock
    private UserRepository userRepository;
    
    @Mock
    private UserMapper userMapper;
    
    @Mock
    private PasswordEncoder passwordEncoder;
    
    @InjectMocks
    private UserService userService;
    
    @Test
    void createUser_ShouldReturnUserDTO() {
        // Given
        UserDTO inputDTO = new UserDTO();
        inputDTO.setUsername("john");
        inputDTO.setEmail("john@example.com");
        inputDTO.setPassword("password123");
        
        User user = new User();
        user.setId(1L);
        user.setUsername("john");
        user.setEmail("john@example.com");
        
        UserDTO expectedDTO = new UserDTO();
        expectedDTO.setId(1L);
        expectedDTO.setUsername("john");
        expectedDTO.setEmail("john@example.com");
        
        when(userRepository.existsByUsername("john")).thenReturn(false);
        when(userRepository.existsByEmail("john@example.com")).thenReturn(false);
        when(userMapper.toUser(inputDTO)).thenReturn(user);
        when(passwordEncoder.encode("password123")).thenReturn("encodedPassword");
        when(userRepository.save(any(User.class))).thenReturn(user);
        when(userMapper.toUserDTO(user)).thenReturn(expectedDTO);
        
        // When
        UserDTO result = userService.createUser(inputDTO);
        
        // Then
        assertThat(result).isEqualTo(expectedDTO);
        assertThat(result.getId()).isEqualTo(1L);
        assertThat(result.getUsername()).isEqualTo("john");
    }
    
    @Test
    void getUserById_ShouldReturnUserDTO() {
        // Given
        Long userId = 1L;
        User user = new User();
        user.setId(userId);
        user.setUsername("john");
        
        UserDTO expectedDTO = new UserDTO();
        expectedDTO.setId(userId);
        expectedDTO.setUsername("john");
        
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(userMapper.toUserDTO(user)).thenReturn(expectedDTO);
        
        // When
        Optional<UserDTO> result = userService.getUserById(userId);
        
        // Then
        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(expectedDTO);
    }
}
```

#### 2.11.2 Tests d intégration
```java
package com.example.api.controller;

import com.example.api.dto.UserDTO;
import com.example.api.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
@AutoConfigureTestDatabase
class UserControllerIntegrationTest {
    
    @Autowired
    private WebApplicationContext webApplicationContext;
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private ObjectMapper objectMapper;
    
    private MockMvc mockMvc;
    
    @Test
    void createUser_ShouldReturnCreatedUser() throws Exception {
        // Given
        UserDTO userDTO = new UserDTO();
        userDTO.setUsername("testuser");
        userDTO.setEmail("test@example.com");
        userDTO.setPassword("password123");
        
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        
        // When & Then
        mockMvc.perform(post("/api/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.username").value("testuser"))
                .andExpect(jsonPath("$.data.email").value("test@example.com"));
    }
}
```

---

## 3. Conception de base de données

### 3.1 Principes de conception

#### 3.1.1 Normalisation
La normalisation est un processus qui organise les données pour réduire la redondance et améliorer l intégrité.

**Première forme normale (1NF) :**
- Chaque cellule contient une seule valeur
- Pas de groupes répétitifs

**Deuxième forme normale (2NF) :**
- En 1NF
- Tous les attributs non-clés dépendent entièrement de la clé primaire

**Troisième forme normale (3NF) :**
- En 2NF
- Aucune dépendance transitive

#### 3.1.2 Intégrité référentielle
Maintient la cohérence des données entre les tables liées.

#### 3.1.3 Performance
Optimise les requêtes fréquentes avec des index appropriés.

#### 3.1.4 Évolutivité
Facilite les modifications futures de la structure.

### 3.2 Types de relations

#### 3.2.1 One-to-One
Un enregistrement d une table correspond à un seul enregistrement d une autre table.

```sql
-- Exemple : User et UserProfile
CREATE TABLE users (
    id SERIAL PRIMARY KEY,
    username VARCHAR(50) UNIQUE NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL
);

CREATE TABLE user_profiles (
    id SERIAL PRIMARY KEY,
    user_id INTEGER UNIQUE REFERENCES users(id) ON DELETE CASCADE,
    bio TEXT,
    avatar_url VARCHAR(255)
);
```

#### 3.2.2 One-to-Many
Un enregistrement peut correspondre à plusieurs enregistrements d une autre table.

```sql
-- Exemple : User et Posts
CREATE TABLE users (
    id SERIAL PRIMARY KEY,
    username VARCHAR(50) UNIQUE NOT NULL
);

CREATE TABLE posts (
    id SERIAL PRIMARY KEY,
    title VARCHAR(200) NOT NULL,
    content TEXT,
    user_id INTEGER REFERENCES users(id) ON DELETE CASCADE
);
```

#### 3.2.3 Many-to-Many
Plusieurs enregistrements peuvent correspondre à plusieurs enregistrements d une autre table.

```sql
-- Exemple : Users et Roles
CREATE TABLE users (
    id SERIAL PRIMARY KEY,
    username VARCHAR(50) UNIQUE NOT NULL
);

CREATE TABLE roles (
    id SERIAL PRIMARY KEY,
    name VARCHAR(50) UNIQUE NOT NULL
);

CREATE TABLE user_roles (
    user_id INTEGER REFERENCES users(id) ON DELETE CASCADE,
    role_id INTEGER REFERENCES roles(id) ON DELETE CASCADE,
    PRIMARY KEY (user_id, role_id)
);
```

### 3.3 Schéma de base de données pour une API REST

#### 3.3.1 Tables principales
```sql
-- Table des utilisateurs
CREATE TABLE users (
    id SERIAL PRIMARY KEY,
    username VARCHAR(50) UNIQUE NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    password_hash VARCHAR(255) NOT NULL,
    first_name VARCHAR(50),
    last_name VARCHAR(50),
    is_active BOOLEAN DEFAULT true,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Table des catégories
CREATE TABLE categories (
    id SERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    description TEXT,
    parent_id INTEGER REFERENCES categories(id),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Table des produits
CREATE TABLE products (
    id SERIAL PRIMARY KEY,
    name VARCHAR(200) NOT NULL,
    description TEXT,
    price DECIMAL(10,2) NOT NULL,
    stock_quantity INTEGER DEFAULT 0,
    category_id INTEGER REFERENCES categories(id),
    created_by INTEGER REFERENCES users(id),
    is_active BOOLEAN DEFAULT true,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Table des commandes
CREATE TABLE orders (
    id SERIAL PRIMARY KEY,
    user_id INTEGER REFERENCES users(id),
    total_amount DECIMAL(10,2) NOT NULL,
    status VARCHAR(20) DEFAULT  pending ,
    shipping_address TEXT,
    billing_address TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Table des détails de commande (Many-to-Many)
CREATE TABLE order_items (
    id SERIAL PRIMARY KEY,
    order_id INTEGER REFERENCES orders(id) ON DELETE CASCADE,
    product_id INTEGER REFERENCES products(id),
    quantity INTEGER NOT NULL,
    unit_price DECIMAL(10,2) NOT NULL,
    total_price DECIMAL(10,2) NOT NULL
);

-- Table des avis
CREATE TABLE reviews (
    id SERIAL PRIMARY KEY,
    product_id INTEGER REFERENCES products(id) ON DELETE CASCADE,
    user_id INTEGER REFERENCES users(id),
    rating INTEGER CHECK (rating >= 1 AND rating <= 5),
    comment TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
```

#### 3.3.2 Index pour optimiser les performances
```sql
-- Index sur les colonnes fréquemment utilisées dans les WHERE
CREATE INDEX idx_users_email ON users(email);
CREATE INDEX idx_users_username ON users(username);
CREATE INDEX idx_products_category ON products(category_id);
CREATE INDEX idx_products_name ON products(name);
CREATE INDEX idx_orders_user ON orders(user_id);
CREATE INDEX idx_orders_status ON orders(status);
CREATE INDEX idx_reviews_product ON reviews(product_id);

-- Index composite pour les requêtes complexes
CREATE INDEX idx_products_category_active ON products(category_id, is_active);
CREATE INDEX idx_orders_user_status ON orders(user_id, status);
```

#### 3.3.3 Contraintes de validation
```sql
-- Contraintes de validation des données
ALTER TABLE products ADD CONSTRAINT check_price_positive CHECK (price > 0);
ALTER TABLE products ADD CONSTRAINT check_stock_positive CHECK (stock_quantity >= 0);
ALTER TABLE order_items ADD CONSTRAINT check_quantity_positive CHECK (quantity > 0);
ALTER TABLE reviews ADD CONSTRAINT check_rating_range CHECK (rating >= 1 AND rating <= 5);
```

### 3.4 Requêtes SQL essentielles

#### 3.4.1 Requêtes de lecture (SELECT)

**Récupération simple :**
```sql
-- Tous les utilisateurs actifs
SELECT id, username, email, first_name, last_name 
FROM users 
WHERE is_active = true;

-- Produits avec leur catégorie
SELECT p.id, p.name, p.price, c.name as category_name
FROM products p
JOIN categories c ON p.category_id = c.id
WHERE p.is_active = true;
```

**Requêtes avec agrégation :**
```sql
-- Nombre de produits par catégorie
SELECT c.name, COUNT(p.id) as product_count
FROM categories c
LEFT JOIN products p ON c.id = p.category_id
GROUP BY c.id, c.name
ORDER BY product_count DESC;

-- Prix moyen par catégorie
SELECT c.name, AVG(p.price) as average_price
FROM categories c
JOIN products p ON c.id = p.category_id
GROUP BY c.id, c.name
HAVING AVG(p.price) > 50;
```

**Requêtes complexes :**
```sql
-- Produits les mieux notés avec leur note moyenne
SELECT p.id, p.name, p.price, AVG(r.rating) as average_rating, COUNT(r.id) as review_count
FROM products p
LEFT JOIN reviews r ON p.id = r.product_id
WHERE p.is_active = true
GROUP BY p.id, p.name, p.price
HAVING COUNT(r.id) >= 3
ORDER BY average_rating DESC, review_count DESC;

-- Commandes avec détails des produits
SELECT o.id, o.total_amount, o.status, u.username,
       STRING_AGG(p.name ||   (x  || oi.quantity ||  ) ,  ,  ) as products
FROM orders o
JOIN users u ON o.user_id = u.id
JOIN order_items oi ON o.id = oi.order_id
JOIN products p ON oi.product_id = p.id
GROUP BY o.id, o.total_amount, o.status, u.username
ORDER BY o.created_at DESC;
```

#### 3.4.2 Requêtes de modification (INSERT, UPDATE, DELETE)

**Insertion de données :**
```sql
-- Insertion d un nouvel utilisateur
INSERT INTO users (username, email, password_hash, first_name, last_name)
VALUES ( john_doe ,  john@example.com ,  $2a$10$... ,  John ,  Doe )
RETURNING id;

-- Insertion multiple de catégories
INSERT INTO categories (name, description) VALUES
( Électronique ,  Produits électroniques et gadgets ),
( Vêtements ,  Vêtements et accessoires ),
( Livres ,  Livres et publications );

-- Insertion avec sous-requête
INSERT INTO products (name, description, price, category_id, created_by)
SELECT  Nouveau produit ,  Description , 29.99, c.id, u.id
FROM categories c, users u
WHERE c.name =  Électronique  AND u.username =  admin ;
```

**Mise à jour de données :**
```sql
-- Mise à jour du prix d un produit
UPDATE products 
SET price = 39.99, updated_at = CURRENT_TIMESTAMP
WHERE id = 123;

-- Mise à jour conditionnelle
UPDATE products 
SET stock_quantity = stock_quantity - 1
WHERE id = 123 AND stock_quantity > 0;

-- Mise à jour avec jointure
UPDATE products p
SET price = p.price * 1.1
FROM categories c
WHERE p.category_id = c.id AND c.name =  Électronique ;
```

**Suppression de données :**
```sql
-- Suppression logique (désactivation)
UPDATE users 
SET is_active = false, updated_at = CURRENT_TIMESTAMP
WHERE id = 123;

-- Suppression physique
DELETE FROM reviews 
WHERE product_id = 123;

-- Suppression avec condition
DELETE FROM order_items 
WHERE order_id IN (
    SELECT id FROM orders 
    WHERE status =  cancelled  AND created_at < CURRENT_TIMESTAMP - INTERVAL  30 days 
);
```

### 3.5 Requêtes pour API REST

#### 3.5.1 Pagination
```sql
-- Pagination simple
SELECT id, name, price 
FROM products 
WHERE is_active = true
ORDER BY created_at DESC
LIMIT 10 OFFSET 20;

-- Pagination avec comptage total
WITH product_count AS (
    SELECT COUNT(*) as total
    FROM products 
    WHERE is_active = true
),
product_list AS (
    SELECT id, name, price, created_at
    FROM products 
    WHERE is_active = true
    ORDER BY created_at DESC
    LIMIT 10 OFFSET 20
)
SELECT p.*, c.total
FROM product_list p, product_count c;
```

#### 3.5.2 Recherche et filtrage
```sql
-- Recherche textuelle
SELECT id, name, description, price
FROM products
WHERE is_active = true 
  AND (name ILIKE  %smartphone%  OR description ILIKE  %smartphone% )
ORDER BY 
    CASE WHEN name ILIKE  %smartphone%  THEN 1 ELSE 2 END,
    price ASC;

-- Filtrage par plage de prix
SE', 1, (SELECT id FROM courses WHERE title = 'API REST - Maîtrisez les Web Services')),
('Conception avec Java Spring Boot', 'Créer des API REST robustes avec Spring Boot et JPA', 1, (SELECT id FROM courses WHERE title = 'API REST - Maîtrisez les Web Services')),
('Base de données et SQL', 'Modéliser et interroger les données avec SQL pour vos API', 1, (SELECT id FROM courses WHERE title = 'API REST - Maîtrisez les Web Services')),
('Interface utilisateur avec Vue.js 3', 'Développer des interfaces modernes avec Vue 3, TypeScript et CSS', 1, (SELECT id FROM courses WHERE title = 'API REST - Maîtrisez les Web Services')),
('API avec Python Flask', 'Créer des API REST rapides et efficaces avec Python Flask', 1, (SELECT id FROM courses WHERE title = 'API REST - Maîtrisez les Web Services'));