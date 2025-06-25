# API Course - Documentation

## Vue d'ensemble

L'API Course permet de gérer les cours dans l'application flash card. Un cours appartient à un utilisateur et peut contenir plusieurs leçons.

## Endpoints

### 1. Créer un cours

**POST** `/api/courses`

Crée un nouveau cours pour un utilisateur.

**Paramètres de requête :**
- `userId` (Long, requis) : ID de l'utilisateur propriétaire

**Corps de la requête :**
```json
{
  "title": "Cours de Mathématiques",
  "description": "Cours complet de mathématiques pour débutants"
}
```

**Réponse :**
- **201 Created** : Cours créé avec succès
- **400 Bad Request** : Erreur de validation ou titre déjà existant

### 2. Récupérer tous les cours

**GET** `/api/courses`

Récupère la liste de tous les cours.

**Réponse :**
- **200 OK** : Liste des cours
```json
[
  {
    "id": 1,
    "title": "Cours de Mathématiques",
    "description": "Cours complet de mathématiques pour débutants"
  }
]
```

### 3. Récupérer un cours par ID

**GET** `/api/courses/{id}`

Récupère un cours spécifique par son ID.

**Paramètres de chemin :**
- `id` (Long, requis) : ID du cours

**Réponse :**
- **200 OK** : Cours trouvé
- **404 Not Found** : Cours non trouvé

### 4. Récupérer les cours d'un utilisateur

**GET** `/api/courses/user/{userId}`

Récupère tous les cours d'un utilisateur spécifique.

**Paramètres de chemin :**
- `userId` (Long, requis) : ID de l'utilisateur

**Réponse :**
- **200 OK** : Liste des cours de l'utilisateur

### 5. Mettre à jour un cours

**PUT** `/api/courses/{id}`

Met à jour un cours existant.

**Paramètres de chemin :**
- `id` (Long, requis) : ID du cours à mettre à jour

**Corps de la requête :**
```json
{
  "title": "Nouveau titre",
  "description": "Nouvelle description"
}
```

**Réponse :**
- **200 OK** : Cours mis à jour avec succès
- **404 Not Found** : Cours non trouvé

### 6. Supprimer un cours

**DELETE** `/api/courses/{id}`

Supprime un cours et toutes ses leçons associées.

**Paramètres de chemin :**
- `id` (Long, requis) : ID du cours à supprimer

**Réponse :**
- **204 No Content** : Cours supprimé avec succès
- **404 Not Found** : Cours non trouvé

### 7. Rechercher des cours par titre

**GET** `/api/courses/search?title={title}`

Recherche des cours par titre (recherche partielle, insensible à la casse).

**Paramètres de requête :**
- `title` (String, requis) : Titre à rechercher

**Réponse :**
- **200 OK** : Liste des cours correspondants

### 8. Rechercher des cours d'un utilisateur par titre

**GET** `/api/courses/search/user/{userId}?title={title}`

Recherche des cours d'un utilisateur spécifique par titre.

**Paramètres de chemin :**
- `userId` (Long, requis) : ID de l'utilisateur

**Paramètres de requête :**
- `title` (String, requis) : Titre à rechercher

**Réponse :**
- **200 OK** : Liste des cours correspondants
- **400 Bad Request** : Utilisateur non trouvé

### 9. Vérifier l'existence d'un cours

**GET** `/api/courses/{id}/exists`

Vérifie si un cours existe.

**Paramètres de chemin :**
- `id` (Long, requis) : ID du cours

**Réponse :**
- **200 OK** : `true` si le cours existe, `false` sinon

### 10. Vérifier la propriété d'un cours

**GET** `/api/courses/{courseId}/owner/{userId}`

Vérifie si un utilisateur est propriétaire d'un cours.

**Paramètres de chemin :**
- `courseId` (Long, requis) : ID du cours
- `userId` (Long, requis) : ID de l'utilisateur

**Réponse :**
- **200 OK** : `true` si l'utilisateur est propriétaire, `false` sinon

## Modèle de données

### Course

```json
{
  "id": 1,
  "title": "Cours de Mathématiques",
  "description": "Cours complet de mathématiques pour débutants",
  "user": {
    "id": 1,
    "username": "jerome"
  },
  "lessons": [
    {
      "id": 1,
      "title": "Addition et soustraction",
      "description": "Apprendre les bases de l'addition et de la soustraction"
    }
  ]
}
```

## Relations

- **Course** ↔ **User** : Many-to-One (Plusieurs cours peuvent appartenir à un utilisateur)
- **Course** ↔ **Lesson** : One-to-Many (Un cours peut contenir plusieurs leçons)

## Gestion des erreurs

L'API retourne les codes de statut HTTP appropriés :

- **200 OK** : Opération réussie
- **201 Created** : Ressource créée avec succès
- **204 No Content** : Ressource supprimée avec succès
- **400 Bad Request** : Données invalides ou erreur de validation
- **404 Not Found** : Ressource non trouvée

## Exemples d'utilisation

### Créer un cours
```bash
curl -X POST "http://localhost:8080/api/courses?userId=1" \
  -H "Content-Type: application/json" \
  -d '{
    "title": "Cours de Programmation",
    "description": "Apprendre les bases de la programmation"
  }'
```

### Récupérer les cours d'un utilisateur
```bash
curl -X GET "http://localhost:8080/api/courses/user/1"
```

### Mettre à jour un cours
```bash
curl -X PUT "http://localhost:8080/api/courses/1" \
  -H "Content-Type: application/json" \
  -d '{
    "title": "Cours de Programmation Avancée",
    "description": "Programmation orientée objet et design patterns"
  }'
```

### Supprimer un cours
```bash
curl -X DELETE "http://localhost:8080/api/courses/1"
``` 