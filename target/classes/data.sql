-- ==================== SCRIPT D'INITIALISATION DE LA BASE DE DONNÉES ====================
-- Ce fichier contient les requêtes SQL pour créer les tables et insérer des données de test
-- Il est exécuté automatiquement au démarrage de l'application si spring.jpa.hibernate.ddl-auto=none

-- ==================== CRÉATION DE LA TABLE UTILISATEURS ====================
-- Table pour stocker les informations des utilisateurs de l'application
CREATE TABLE IF NOT EXISTS app_user (
    id SERIAL PRIMARY KEY,                    -- Identifiant unique auto-incrémenté
    username VARCHAR(255) UNIQUE NOT NULL,    -- Nom d'utilisateur unique
    password VARCHAR(255) NOT NULL            -- Mot de passe hashé (BCrypt)
);

-- ==================== INSERTION D'UTILISATEURS DE TEST ====================
-- Ajout d'utilisateurs avec des mots de passe hashés en BCrypt
-- Le mot de passe hashé correspond à "password" encodé avec BCrypt
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

-- ==================== INSERTION DE COURSES DE TEST ====================
-- Ajout de cours de test associés aux utilisateurs
INSERT INTO courses (title, type_course, user_id) VALUES ('Cours de Mathématiques', 'math', 1);
INSERT INTO courses (title, type_course, user_id) VALUES ('Cours de Français', 'language', 1);
INSERT INTO courses (title, type_course, user_id) VALUES ('Cours d''Histoire', 'history', 2);

-- ==================== CRÉATION DE LA TABLE LEÇONS ====================
-- Table pour stocker les leçons créées par les utilisateurs
CREATE TABLE IF NOT EXISTS lessons (
    id SERIAL PRIMARY KEY,                    -- Identifiant unique auto-incrémenté
    title VARCHAR(255) NOT NULL,              -- Titre de la leçon
    description VARCHAR(255) NOT NULL,
    user_id INT NOT NULL,                     -- Référence vers l'utilisateur propriétaire
    course_id INT,                            -- Référence vers le cours (optionnel)
    FOREIGN KEY (user_id) REFERENCES app_user(id) ON DELETE CASCADE,  -- Contrainte de clé étrangère avec CASCADE
    FOREIGN KEY (course_id) REFERENCES courses(id) ON DELETE CASCADE  -- Contrainte de clé étrangère avec CASCADE
);

-- ==================== INSERTION DE LEÇONS DE TEST ====================
-- Ajout de leçons de test associées aux utilisateurs
INSERT INTO lessons (title, description, user_id, course_id) VALUES ('Lesson 1', 'Description 1', 1, 1);
INSERT INTO lessons (title, description, user_id, course_id) VALUES ('Lesson 2', 'Description 2', 1, 1);
INSERT INTO lessons (title, description, user_id, course_id) VALUES ('Lesson 3', 'Description 3', 2, 3);

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

-- ==================== INSERTION DE QUESTIONS DE TEST ====================
INSERT INTO question (question, type_question, answer, lesson_id) VALUES ( 'What is the capital of France?', 'multiple', 'Paris', 1);
INSERT INTO question (question, type_question, answer, lesson_id) VALUES ( 'What is the capital of Germany?', 'multiple', 'Berlin', 1);
INSERT INTO question (question, type_question, answer, lesson_id) VALUES ( 'What is the capital of Italy?', 'text', 'Rome', 1);

INSERT INTO possibility (possibility, question_id) VALUES ( 'Paris', 1);
INSERT INTO possibility (possibility, question_id) VALUES ( 'Berlin', 1);
INSERT INTO possibility (possibility, question_id) VALUES ( 'Rome', 1);

INSERT INTO possibility (possibility, question_id) VALUES ( 'Paris', 2);
INSERT INTO possibility (possibility, question_id) VALUES ( 'Berlin', 2);

-- ==================== CRÉATION DE LA TABLE TEST_CONTROLE ====================
-- Table pour stocker les résultats des tests de leçons
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

-- ==================== INSERTION DE TESTS DE TEST ====================
-- Ajout de quelques résultats de test pour les tests
INSERT INTO test_controle (nb_bonne_reponse, nb_question, lesson_id, user_id) VALUES (2, 3, 1, 1);
INSERT INTO test_controle (nb_bonne_reponse, nb_question, lesson_id, user_id) VALUES (3, 3, 1, 1);
INSERT INTO test_controle (nb_bonne_reponse, nb_question, lesson_id, user_id) VALUES (1, 3, 1, 2);
