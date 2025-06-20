# 🚀 Guide d'utilisation Postman - Email Service API

## 📋 Prérequis

1. **Postman installé** sur votre machine
2. **Backend EmailService démarré** sur `http://localhost:8080`
3. **Base de données** en cours d'exécution

## 🔧 Configuration

### 1. Importer la Collection
1. Ouvrez Postman
2. Cliquez sur "Import"
3. Sélectionnez le fichier `EmailService_API.postman_collection.json`

### 2. Importer l'Environnement
1. Cliquez sur "Import"
2. Sélectionnez le fichier `EmailService_Environment.postman_environment.json`
3. Sélectionnez l'environnement "Email Service Environment" dans le menu déroulant

## 🧪 Tests à effectuer

### 1. Test d'Inscription (Register)

**Endpoint :** `POST {{baseUrl}}/api/auth/register`

**Headers :**
```
Content-Type: application/json
```

**Body (raw JSON) :**
```json
{
    "username": "testuser",
    "password": "password123"
}
```

**Réponse attendue :**
```json
{
    "id": 1,
    "username": "testuser"
}
```

### 2. Test de Connexion (Login)

**Endpoint :** `POST {{baseUrl}}/api/auth/login`

**Headers :**
```
Content-Type: application/json
```

**Body (raw JSON) :**
```json
{
    "username": "testuser",
    "password": "password123"
}
```

**Réponse attendue :**
```json
{
    "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..."
}
```

**Réponse en cas d'échec :**
```
"Identifiants invalides"
```

### 3. Test de Route Protégée

**Endpoint :** `GET {{baseUrl}}/api/test`

**Headers :**
```
Authorization: Bearer {{jwt_token}}
```

## 🔄 Workflow de Test

1. **Inscription** : Créez un nouvel utilisateur avec username/password
2. **Connexion** : Connectez-vous avec les mêmes credentials
3. **Route protégée** : Testez l'accès avec le token JWT

## ⚙️ Variables d'Environnement

- `{{baseUrl}}` : URL de base de l'API (http://localhost:8080)
- `{{jwt_token}}` : Token JWT (sauvegardé automatiquement)
- `{{username}}` : Nom d'utilisateur de test
- `{{password}}` : Mot de passe de test

## 🎯 Scripts Automatiques

La collection inclut un script qui :
- Extrait automatiquement le token JWT des réponses
- Sauvegarde le token dans la variable d'environnement
- Permet de réutiliser le token pour les requêtes suivantes

## 🚨 Cas d'Erreur

### Erreur 400 - Bad Request
- Vérifiez le format JSON
- Assurez-vous que `username` et `password` sont présents

### Erreur 401 - Unauthorized
- Vérifiez les credentials
- Assurez-vous que l'utilisateur existe

### Erreur 409 - Conflict
- Le username existe déjà (pour l'inscription)

## 🔍 Debugging

1. **Vérifiez les logs** du backend Spring Boot
2. **Inspectez les headers** de la requête
3. **Vérifiez la base de données** pour voir si l'utilisateur est créé

## 📝 Différences avec FlashCard API

Cette API EmailService utilise :
- `username` au lieu d'`email`
- Pas de `firstName`/`lastName`
- Structure de réponse différente pour l'inscription
- Message d'erreur en français pour l'authentification

## 🎯 Exemples de Test

### Test d'inscription réussie
```bash
curl -X POST http://localhost:8080/api/auth/register \
  -H "Content-Type: application/json" \
  -d '{"username": "newuser", "password": "mypassword"}'
```

### Test de connexion réussie
```bash
curl -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{"username": "newuser", "password": "mypassword"}'
```

### Test avec token JWT
```bash
curl -X GET http://localhost:8080/api/test \
  -H "Authorization: Bearer YOUR_JWT_TOKEN"
``` 