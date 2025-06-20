# 🧪 Guide de Test - API des Leçons

## 🚀 Démarrage rapide

### 1. Vérifier que le backend est en cours d'exécution
```bash
cd backend
docker-compose ps
```

Si les conteneurs ne sont pas en cours d'exécution :
```bash
docker-compose up -d
```

### 2. Importer la collection Postman
1. Ouvrir Postman
2. Cliquer sur "Import"
3. Sélectionner le fichier `Lessons_API.postman_collection.json`
4. La collection apparaîtra dans votre workspace

## 📋 Étapes de test

### Étape 1 : Authentification
1. **Exécuter** : `1. Authentication > Login User`
2. **Vérifier** : 
   - Status code : `200 OK`
   - Response contient un `token` JWT
   - Le token est automatiquement sauvegardé dans les variables

### Étape 2 : Tester les opérations CRUD

#### 2.1 Récupérer les leçons
1. **Exécuter** : `2. Lessons CRUD > Get All Lessons`
2. **Vérifier** :
   - Status code : `200 OK`
   - Response : Liste des leçons de l'utilisateur connecté

#### 2.2 Créer une leçon
1. **Exécuter** : `2. Lessons CRUD > Create Lesson`
2. **Vérifier** :
   - Status code : `201 Created`
   - Response : Nouvelle leçon avec ID généré
   - L'ID est automatiquement sauvegardé

#### 2.3 Modifier une leçon
1. **Exécuter** : `2. Lessons CRUD > Update Lesson`
2. **Vérifier** :
   - Status code : `200 OK`
   - Response : Leçon mise à jour

#### 2.4 Supprimer une leçon
1. **Exécuter** : `2. Lessons CRUD > Delete Lesson`
2. **Vérifier** :
   - Status code : `200 OK`

### Étape 3 : Tester la sécurité

#### 3.1 Accès sans authentification
1. **Exécuter** : `3. Error Testing > Get Lessons Without Auth`
2. **Vérifier** :
   - Status code : `401 Unauthorized`

#### 3.2 Modification d'une leçon d'un autre utilisateur
1. **Exécuter** : `3. Error Testing > Update Other User Lesson`
2. **Vérifier** :
   - Status code : `403 Forbidden`
   - Message d'erreur approprié

#### 3.3 Modification d'une leçon inexistante
1. **Exécuter** : `3. Error Testing > Update Non-existent Lesson`
2. **Vérifier** :
   - Status code : `404 Not Found`

## 🔍 Vérifications importantes

### ✅ Ce qui doit fonctionner
- [ ] Authentification JWT
- [ ] Récupération des leçons de l'utilisateur connecté
- [ ] Création de nouvelles leçons
- [ ] Modification de ses propres leçons
- [ ] Suppression de ses propres leçons
- [ ] Association automatique à l'utilisateur connecté

### ❌ Ce qui doit être bloqué
- [ ] Accès sans token JWT
- [ ] Modification des leçons d'autres utilisateurs
- [ ] Suppression des leçons d'autres utilisateurs
- [ ] Accès à des leçons inexistantes

## 🐛 Dépannage

### Problème : "401 Unauthorized"
- Vérifier que le token JWT est valide
- Refaire l'authentification si nécessaire

### Problème : "403 Forbidden"
- Vérifier que vous essayez de modifier vos propres leçons
- L'ID de leçon doit appartenir à l'utilisateur connecté

### Problème : "404 Not Found"
- Vérifier que l'ID de leçon existe
- Utiliser un ID valide de vos propres leçons

### Problème : Backend ne répond pas
```bash
# Vérifier les logs
docker-compose logs app

# Redémarrer si nécessaire
docker-compose restart app
```

## 📊 Données de test

Les utilisateurs de test dans `data.sql` :
- **jerome** / password (ID: 1) - Possède les leçons 1 et 2
- **user** / password (ID: 2) - Possède la leçon 3

## 🎯 Tests automatisés

Pour des tests plus avancés, vous pouvez également utiliser :

### cURL
```bash
# Login
curl -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{"username":"jerome","password":"password"}'

# Get lessons (avec le token obtenu)
curl -X GET http://localhost:8080/api/lessons \
  -H "Authorization: Bearer YOUR_JWT_TOKEN"
```

### Tests unitaires
```bash
cd backend
mvn test
```

## 🚀 Prochaines étapes

Une fois les tests validés, vous pouvez :
1. Intégrer avec votre frontend Vue.js
2. Ajouter des fonctionnalités avancées (recherche, filtres, etc.)
3. Implémenter des tests automatisés complets 