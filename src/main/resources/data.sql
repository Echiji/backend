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

-- Données de test pour les cours
INSERT INTO courses (title, type_course, user_id) VALUES ('Programmation Java', 'DEVELOPPEMENT', 1);
INSERT INTO courses (title, type_course, user_id) VALUES ('Base de données SQL', 'DEVELOPPEMENT', 1);
INSERT INTO courses (title, type_course, user_id) VALUES ('Spring Boot Framework', 'DEVELOPPEMENT', 2);

CREATE TABLE IF NOT EXISTS lessons (
    id SERIAL PRIMARY KEY,                    -- Identifiant unique auto-incrémenté
    title VARCHAR(255) NOT NULL,              -- Titre de la leçon
    description TEXT NOT NULL,
    user_id INT NOT NULL,                     -- Référence vers l'utilisateur propriétaire
    course_id INT,                            -- Référence vers le cours (optionnel)
    FOREIGN KEY (user_id) REFERENCES app_user(id) ON DELETE CASCADE,  -- Contrainte de clé étrangère avec CASCADE
    FOREIGN KEY (course_id) REFERENCES courses(id) ON DELETE CASCADE  -- Contrainte de clé étrangère avec CASCADE
);

-- Données de test pour les leçons
INSERT INTO lessons (title, description, user_id, course_id) VALUES ('Introduction à Java', 'Apprendre les bases de la programmation Java', 1, 1);
INSERT INTO lessons (title, description, user_id, course_id) VALUES ('Variables et types de données', 'Comprendre les différents types de variables en Java', 1, 1);
INSERT INTO lessons (title, description, user_id, course_id) VALUES ('Introduction aux bases de données', 'Concepts fondamentaux des bases de données relationnelles', 1, 2);

-- ==================== CRÉATION DE LA TABLE QUESTIONNAIRES ====================
CREATE TABLE IF NOT EXISTS questionnaires (
    id SERIAL PRIMARY KEY,                    -- Identifiant unique auto-incrémenté
    title VARCHAR(255) NOT NULL,              -- Titre du questionnaire
    lesson_id INT NOT NULL,                   -- Référence vers la leçon
    display_order INT DEFAULT 0,              -- Ordre d'affichage dans la leçon
    is_active BOOLEAN DEFAULT FALSE,          -- Indique si le questionnaire est actif (false par défaut)
    FOREIGN KEY (lesson_id) REFERENCES lessons(id) ON DELETE CASCADE  -- Contrainte de clé étrangère avec CASCADE
);

-- Données de test pour les questionnaires
INSERT INTO questionnaires (title, lesson_id, display_order, is_active) VALUES ('Quiz Java - Niveau débutant', 1, 1, true);
INSERT INTO questionnaires (title, lesson_id, display_order, is_active) VALUES ('Quiz Variables Java', 2, 1, true);
INSERT INTO questionnaires (title, lesson_id, display_order, is_active) VALUES ('Quiz SQL - Concepts de base', 3, 1, false);

-- ==================== CRÉATION DE LA TABLE QUESTIONS ====================
CREATE TABLE IF NOT EXISTS question (
    id SERIAL PRIMARY KEY,
    question VARCHAR(255) NOT NULL,
    type_question VARCHAR(50) NOT NULL,
    answer TEXT NOT NULL,
    questionnaire_id INT NOT NULL,
    FOREIGN KEY (questionnaire_id) REFERENCES questionnaires(id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS possibility (
    id SERIAL PRIMARY KEY,
    possibility VARCHAR(255) NOT NULL,
    question_id INT NOT NULL,
    FOREIGN KEY (question_id) REFERENCES question(id) ON DELETE CASCADE
);


CREATE TABLE IF NOT EXISTS test_controle (
    id SERIAL PRIMARY KEY,
    nb_bonne_reponse INT NOT NULL,
    nb_question INT NOT NULL,
    date_creation TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    questionnaire_id INT NOT NULL,
    user_id INT NOT NULL,
    FOREIGN KEY (questionnaire_id) REFERENCES questionnaires(id) ON DELETE CASCADE,
    FOREIGN KEY (user_id) REFERENCES app_user(id) ON DELETE CASCADE
);

-- Ajout du questionnaire facile
INSERT INTO questionnaires (title, lesson_id, display_order, is_active)
VALUES ('Quiz Java - Facile', 1, 2, true);

-- Ajout du questionnaire difficile
INSERT INTO questionnaires (title, lesson_id, display_order, is_active)
VALUES ('Quiz Java - Difficile', 1, 3, true);

-- Récupérer les IDs générés (adapte-les si besoin)
-- SELECT id FROM questionnaires WHERE title = 'Quiz Java - Facile' AND lesson_id = 1;
-- SELECT id FROM questionnaires WHERE title = 'Quiz Java - Difficile' AND lesson_id = 1;

-- Questions pour le quiz facile (remplace 4 par l'ID réel du quiz facile)
INSERT INTO question (question, type_question, answer, questionnaire_id) VALUES
('Quel mot-clé permet de définir une classe en Java ?', 'text', 'class', 4),
('Quel est le type pour une chaîne de caractères ?', 'text', 'String', 4),
('Que va afficher ce code ? System.out.println(2 + 3);', 'text', '5', 4);

-- Questions pour le quiz difficile (remplace 5 par l'ID réel du quiz difficile)
INSERT INTO question (question, type_question, answer, questionnaire_id) VALUES
('Quelle est la sortie de ce code ? for(int i=0;i<3;i++) { System.out.print(i); }', 'text', '012', 5),
('Complétez : public static ____ addition(int a, int b) { return a + b; }', 'text', 'int', 5),
('Quel est le résultat de : int[] t = {1,2,3}; System.out.println(t.length);', 'text', '3', 5);

-- ==================== COURS VUE 3 ====================
INSERT INTO courses (title, type_course, user_id) VALUES ('Développement Vue 3', 'DEVELOPPEMENT', 1);

-- Leçon Vue 3
INSERT INTO lessons (title, description, user_id, course_id)
VALUES ('Introduction à Vue 3', 'Découvrir les bases du framework Vue 3 et sa composition API.', 1,
        (SELECT id FROM courses WHERE title = 'Développement Vue 3'));

-- Questionnaire Vue 3 - Facile
INSERT INTO questionnaires (title, lesson_id, display_order, is_active)
VALUES ('Quiz Vue 3 - Facile',
        (SELECT id FROM lessons WHERE title = 'Introduction à Vue 3' AND course_id = (SELECT id FROM courses WHERE title = 'Développement Vue 3')), 1, true);

-- Questionnaire Vue 3 - Difficile
INSERT INTO questionnaires (title, lesson_id, display_order, is_active)
VALUES ('Quiz Vue 3 - Difficile',
        (SELECT id FROM lessons WHERE title = 'Introduction à Vue 3' AND course_id = (SELECT id FROM courses WHERE title = 'Développement Vue 3')), 2, true);

-- Questions Vue 3 - Facile
INSERT INTO question (question, type_question, answer, questionnaire_id) VALUES
('Quel mot-clé permet de créer un composant dans Vue 3 ?', 'text', 'defineComponent', (SELECT id FROM questionnaires WHERE title = 'Quiz Vue 3 - Facile')),
('Quelle directive permet de faire une boucle ?', 'qcm', 'v-for', (SELECT id FROM questionnaires WHERE title = 'Quiz Vue 3 - Facile')),
('Quelle API introduit la réactivité dans Vue 3 ?', 'qcm', 'Composition API', (SELECT id FROM questionnaires WHERE title = 'Quiz Vue 3 - Facile'));

-- Questions Vue 3 - Difficile
INSERT INTO question (question, type_question, answer, questionnaire_id) VALUES
('Quelle fonction permet de créer une référence réactive ?', 'text', 'ref', (SELECT id FROM questionnaires WHERE title = 'Quiz Vue 3 - Difficile')),
('Comment accéder aux props dans un setup() ?', 'qcm', 'defineProps', (SELECT id FROM questionnaires WHERE title = 'Quiz Vue 3 - Difficile')),
('Quelle fonction permet de créer un watcher ?', 'qcm', 'watch', (SELECT id FROM questionnaires WHERE title = 'Quiz Vue 3 - Difficile'));

-- Possibilités pour les questions QCM Vue 3 - Facile
INSERT INTO possibility (possibility, question_id) VALUES
('v-for', (SELECT id FROM question WHERE question = 'Quelle directive permet de faire une boucle ?' AND questionnaire_id = (SELECT id FROM questionnaires WHERE title = 'Quiz Vue 3 - Facile'))),
('v-if', (SELECT id FROM question WHERE question = 'Quelle directive permet de faire une boucle ?' AND questionnaire_id = (SELECT id FROM questionnaires WHERE title = 'Quiz Vue 3 - Facile'))),
('v-bind', (SELECT id FROM question WHERE question = 'Quelle directive permet de faire une boucle ?' AND questionnaire_id = (SELECT id FROM questionnaires WHERE title = 'Quiz Vue 3 - Facile'))),
('Composition API', (SELECT id FROM question WHERE question = 'Quelle API introduit la réactivité dans Vue 3 ?' AND questionnaire_id = (SELECT id FROM questionnaires WHERE title = 'Quiz Vue 3 - Facile'))),
('Options API', (SELECT id FROM question WHERE question = 'Quelle API introduit la réactivité dans Vue 3 ?' AND questionnaire_id = (SELECT id FROM questionnaires WHERE title = 'Quiz Vue 3 - Facile'))),
('Template API', (SELECT id FROM question WHERE question = 'Quelle API introduit la réactivité dans Vue 3 ?' AND questionnaire_id = (SELECT id FROM questionnaires WHERE title = 'Quiz Vue 3 - Facile')));

-- Possibilités pour les questions QCM Vue 3 - Difficile
INSERT INTO possibility (possibility, question_id) VALUES
('defineProps', (SELECT id FROM question WHERE question = 'Comment accéder aux props dans un setup() ?' AND questionnaire_id = (SELECT id FROM questionnaires WHERE title = 'Quiz Vue 3 - Difficile'))),
('defineEmits', (SELECT id FROM question WHERE question = 'Comment accéder aux props dans un setup() ?' AND questionnaire_id = (SELECT id FROM questionnaires WHERE title = 'Quiz Vue 3 - Difficile'))),
('defineExpose', (SELECT id FROM question WHERE question = 'Comment accéder aux props dans un setup() ?' AND questionnaire_id = (SELECT id FROM questionnaires WHERE title = 'Quiz Vue 3 - Difficile'))),
('watch', (SELECT id FROM question WHERE question = 'Quelle fonction permet de créer un watcher ?' AND questionnaire_id = (SELECT id FROM questionnaires WHERE title = 'Quiz Vue 3 - Difficile'))),
('watchEffect', (SELECT id FROM question WHERE question = 'Quelle fonction permet de créer un watcher ?' AND questionnaire_id = (SELECT id FROM questionnaires WHERE title = 'Quiz Vue 3 - Difficile'))),
('computed', (SELECT id FROM question WHERE question = 'Quelle fonction permet de créer un watcher ?' AND questionnaire_id = (SELECT id FROM questionnaires WHERE title = 'Quiz Vue 3 - Difficile')));

-- ==================== COURS SQL ====================
INSERT INTO courses (title, type_course, user_id) VALUES ('SQL pour débutants', 'DEVELOPPEMENT', 1);

-- Leçon SQL
INSERT INTO lessons (title, description, user_id, course_id)
VALUES ('Introduction à SQL', 'Apprendre à manipuler les bases de données relationnelles avec SQL.', 1,
        (SELECT id FROM courses WHERE title = 'SQL pour débutants'));

-- Questionnaire SQL - Facile
INSERT INTO questionnaires (title, lesson_id, display_order, is_active)
VALUES ('Quiz SQL - Facile',
        (SELECT id FROM lessons WHERE title = 'Introduction à SQL' AND course_id = (SELECT id FROM courses WHERE title = 'SQL pour débutants')), 1, true);

-- Questionnaire SQL - Difficile
INSERT INTO questionnaires (title, lesson_id, display_order, is_active)
VALUES ('Quiz SQL - Difficile',
        (SELECT id FROM lessons WHERE title = 'Introduction à SQL' AND course_id = (SELECT id FROM courses WHERE title = 'SQL pour débutants')), 2, true);

-- Questions SQL - Facile
INSERT INTO question (question, type_question, answer, questionnaire_id) VALUES
('Quel mot-clé permet de sélectionner toutes les colonnes dune table ?', 'text', 'SELECT *', (SELECT id FROM questionnaires WHERE title = 'Quiz SQL - Facile')),
('Quelle commande permet d ajouter une nouvelle ligne ?', 'qcm', 'INSERT INTO', (SELECT id FROM questionnaires WHERE title = 'Quiz SQL - Facile')),
('Quel mot-clé permet de filtrer les résultats ?', 'qcm', 'WHERE', (SELECT id FROM questionnaires WHERE title = 'Quiz SQL - Facile'));

-- Questions SQL - Difficile
INSERT INTO question (question, type_question, answer, questionnaire_id) VALUES
('Quelle clause permet de grouper les résultats ?', 'text', 'GROUP BY', (SELECT id FROM questionnaires WHERE title = 'Quiz SQL - Difficile')),
('Quelle fonction permet de compter les lignes ?', 'qcm', 'COUNT', (SELECT id FROM questionnaires WHERE title = 'Quiz SQL - Difficile')),
('Quel type de jointure retourne toutes les lignes des deux tables ?', 'qcm', 'FULL OUTER JOIN', (SELECT id FROM questionnaires WHERE title = 'Quiz SQL - Difficile'));

-- Possibilités pour les questions QCM SQL - Facile
INSERT INTO possibility (possibility, question_id) VALUES
('INSERT INTO', (SELECT id FROM question WHERE question = 'Quelle commande permet d ajouter une nouvelle ligne ?' AND questionnaire_id = (SELECT id FROM questionnaires WHERE title = 'Quiz SQL - Facile'))),
('UPDATE', (SELECT id FROM question WHERE question = 'Quelle commande permet d ajouter une nouvelle ligne ?' AND questionnaire_id = (SELECT id FROM questionnaires WHERE title = 'Quiz SQL - Facile'))),
('DELETE', (SELECT id FROM question WHERE question = 'Quelle commande permet d ajouter une nouvelle ligne ?' AND questionnaire_id = (SELECT id FROM questionnaires WHERE title = 'Quiz SQL - Facile'))),
('WHERE', (SELECT id FROM question WHERE question = 'Quel mot-clé permet de filtrer les résultats ?' AND questionnaire_id = (SELECT id FROM questionnaires WHERE title = 'Quiz SQL - Facile'))),
('ORDER BY', (SELECT id FROM question WHERE question = 'Quel mot-clé permet de filtrer les résultats ?' AND questionnaire_id = (SELECT id FROM questionnaires WHERE title = 'Quiz SQL - Facile'))),
('GROUP BY', (SELECT id FROM question WHERE question = 'Quel mot-clé permet de filtrer les résultats ?' AND questionnaire_id = (SELECT id FROM questionnaires WHERE title = 'Quiz SQL - Facile')));

-- Possibilités pour les questions QCM SQL - Difficile
INSERT INTO possibility (possibility, question_id) VALUES
('COUNT', (SELECT id FROM question WHERE question = 'Quelle fonction permet de compter les lignes ?' AND questionnaire_id = (SELECT id FROM questionnaires WHERE title = 'Quiz SQL - Difficile'))),
('SUM', (SELECT id FROM question WHERE question = 'Quelle fonction permet de compter les lignes ?' AND questionnaire_id = (SELECT id FROM questionnaires WHERE title = 'Quiz SQL - Difficile'))),
('AVG', (SELECT id FROM question WHERE question = 'Quelle fonction permet de compter les lignes ?' AND questionnaire_id = (SELECT id FROM questionnaires WHERE title = 'Quiz SQL - Difficile'))),
('FULL OUTER JOIN', (SELECT id FROM question WHERE question = 'Quel type de jointure retourne toutes les lignes des deux tables ?' AND questionnaire_id = (SELECT id FROM questionnaires WHERE title = 'Quiz SQL - Difficile'))),
('INNER JOIN', (SELECT id FROM question WHERE question = 'Quel type de jointure retourne toutes les lignes des deux tables ?' AND questionnaire_id = (SELECT id FROM questionnaires WHERE title = 'Quiz SQL - Difficile'))),
('LEFT JOIN', (SELECT id FROM question WHERE question = 'Quel type de jointure retourne toutes les lignes des deux tables ?' AND questionnaire_id = (SELECT id FROM questionnaires WHERE title = 'Quiz SQL - Difficile')));

-- ==================== COURS PYTHON ====================
INSERT INTO courses (title, type_course, user_id) VALUES ('Programmation Python', 'DEVELOPPEMENT', 1);

-- Leçon Python
INSERT INTO lessons (title, description, user_id, course_id)
VALUES ('Introduction à Python', 'Découvrir la syntaxe et les bases du langage Python.', 1,
        (SELECT id FROM courses WHERE title = 'Programmation Python'));

-- Questionnaire Python - Facile
INSERT INTO questionnaires (title, lesson_id, display_order, is_active)
VALUES ('Quiz Python - Facile',
        (SELECT id FROM lessons WHERE title = 'Introduction à Python' AND course_id = (SELECT id FROM courses WHERE title = 'Programmation Python')), 1, true);

-- Questionnaire Python - Difficile
INSERT INTO questionnaires (title, lesson_id, display_order, is_active)
VALUES ('Quiz Python - Difficile',
        (SELECT id FROM lessons WHERE title = 'Introduction à Python' AND course_id = (SELECT id FROM courses WHERE title = 'Programmation Python')), 2, true);

-- Questions Python - Facile
INSERT INTO question (question, type_question, answer, questionnaire_id) VALUES
('Quel mot-clé permet de définir une fonction en Python ?', 'text', 'def', (SELECT id FROM questionnaires WHERE title = 'Quiz Python - Facile')),
('Quelle structure permet de répéter une action ?', 'qcm', 'for', (SELECT id FROM questionnaires WHERE title = 'Quiz Python - Facile')),
('Comment afficher du texte à l écran ?', 'qcm', 'print', (SELECT id FROM questionnaires WHERE title = 'Quiz Python - Facile'));

-- Questions Python - Difficile
INSERT INTO question (question, type_question, answer, questionnaire_id) VALUES
('Quelle méthode permet de créer une liste de nombres ?', 'text', 'range', (SELECT id FROM questionnaires WHERE title = 'Quiz Python - Difficile')),
('Quel type de données est immuable ?', 'qcm', 'tuple', (SELECT id FROM questionnaires WHERE title = 'Quiz Python - Difficile')),
('Quelle fonction permet de filtrer une liste ?', 'qcm', 'filter', (SELECT id FROM questionnaires WHERE title = 'Quiz Python - Difficile'));

-- Possibilités pour les questions QCM Python - Facile
INSERT INTO possibility (possibility, question_id) VALUES
('for', (SELECT id FROM question WHERE question = 'Quelle structure permet de répéter une action ?' AND questionnaire_id = (SELECT id FROM questionnaires WHERE title = 'Quiz Python - Facile'))),
('if', (SELECT id FROM question WHERE question = 'Quelle structure permet de répéter une action ?' AND questionnaire_id = (SELECT id FROM questionnaires WHERE title = 'Quiz Python - Facile'))),
('while', (SELECT id FROM question WHERE question = 'Quelle structure permet de répéter une action ?' AND questionnaire_id = (SELECT id FROM questionnaires WHERE title = 'Quiz Python - Facile'))),
('print', (SELECT id FROM question WHERE question = 'Comment afficher du texte à l écran ?' AND questionnaire_id = (SELECT id FROM questionnaires WHERE title = 'Quiz Python - Facile'))),
('echo', (SELECT id FROM question WHERE question = 'Comment afficher du texte à l écran ?' AND questionnaire_id = (SELECT id FROM questionnaires WHERE title = 'Quiz Python - Facile'))),
('write', (SELECT id FROM question WHERE question = 'Comment afficher du texte à l écran ?' AND questionnaire_id = (SELECT id FROM questionnaires WHERE title = 'Quiz Python - Facile')));

-- Possibilités pour les questions QCM Python - Difficile
INSERT INTO possibility (possibility, question_id) VALUES
('tuple', (SELECT id FROM question WHERE question = 'Quel type de données est immuable ?' AND questionnaire_id = (SELECT id FROM questionnaires WHERE title = 'Quiz Python - Difficile'))),
('list', (SELECT id FROM question WHERE question = 'Quel type de données est immuable ?' AND questionnaire_id = (SELECT id FROM questionnaires WHERE title = 'Quiz Python - Difficile'))),
('dict', (SELECT id FROM question WHERE question = 'Quel type de données est immuable ?' AND questionnaire_id = (SELECT id FROM questionnaires WHERE title = 'Quiz Python - Difficile'))),
('filter', (SELECT id FROM question WHERE question = 'Quelle fonction permet de filtrer une liste ?' AND questionnaire_id = (SELECT id FROM questionnaires WHERE title = 'Quiz Python - Difficile'))),
('map', (SELECT id FROM question WHERE question = 'Quelle fonction permet de filtrer une liste ?' AND questionnaire_id = (SELECT id FROM questionnaires WHERE title = 'Quiz Python - Difficile'))),
('reduce', (SELECT id FROM question WHERE question = 'Quelle fonction permet de filtrer une liste ?' AND questionnaire_id = (SELECT id FROM questionnaires WHERE title = 'Quiz Python - Difficile')));

-- ==================== COURS PYTHON COMPLET ====================
-- Questionnaire Python - Introduction complète
INSERT INTO questionnaires (title, lesson_id, display_order, is_active)
VALUES ('Quiz Python - Introduction complète',
        (SELECT id FROM lessons WHERE title = 'Introduction à Python' AND course_id = (SELECT id FROM courses WHERE title = 'Programmation Python')), 3, true);

-- Questions Python - Introduction complète (15 questions mélangées)
INSERT INTO question (question, type_question, answer, questionnaire_id) VALUES
-- Questions texte (8 questions)
('Quel est le nom du créateur de Python ?', 'text', 'Guido van Rossum', (SELECT id FROM questionnaires WHERE title = 'Quiz Python - Introduction complète')),
('En quelle année Python a-t-il été créé ?', 'text', '1989', (SELECT id FROM questionnaires WHERE title = 'Quiz Python - Introduction complète')),
('Quel mot-clé permet de définir une fonction en Python ?', 'text', 'def', (SELECT id FROM questionnaires WHERE title = 'Quiz Python - Introduction complète')),
('Quel mot-clé permet de définir une classe en Python ?', 'text', 'class', (SELECT id FROM questionnaires WHERE title = 'Quiz Python - Introduction complète')),
('Quelle commande permet d installer des packages Python ?', 'text', 'pip', (SELECT id FROM questionnaires WHERE title = 'Quiz Python - Introduction complète')),
('Quel type de données représente un nombre entier en Python ?', 'text', 'int', (SELECT id FROM questionnaires WHERE title = 'Quiz Python - Introduction complète')),
('Quel type de données représente une chaîne de caractères en Python ?', 'text', 'str', (SELECT id FROM questionnaires WHERE title = 'Quiz Python - Introduction complète')),
('Quelle fonction permet de créer une liste de nombres ?', 'text', 'range', (SELECT id FROM questionnaires WHERE title = 'Quiz Python - Introduction complète')),

-- Questions QCM (7 questions)
('D où vient le nom "Python" ?', 'qcm', 'Monty Python', (SELECT id FROM questionnaires WHERE title = 'Quiz Python - Introduction complète')),
('Quel domaine utilise Python pour l apprentissage automatique ?', 'qcm', 'Intelligence artificielle', (SELECT id FROM questionnaires WHERE title = 'Quiz Python - Introduction complète')),
('Quelle structure permet de répéter une action en Python ?', 'qcm', 'for', (SELECT id FROM questionnaires WHERE title = 'Quiz Python - Introduction complète')),
('Comment afficher du texte à l écran en Python ?', 'qcm', 'print', (SELECT id FROM questionnaires WHERE title = 'Quiz Python - Introduction complète')),
('Quel type de données est immuable en Python ?', 'qcm', 'tuple', (SELECT id FROM questionnaires WHERE title = 'Quiz Python - Introduction complète')),
('Quelle bibliothèque est utilisée pour la science des données ?', 'qcm', 'Pandas', (SELECT id FROM questionnaires WHERE title = 'Quiz Python - Introduction complète')),
('Quel framework web est mentionné dans le cours ?', 'qcm', 'Django', (SELECT id FROM questionnaires WHERE title = 'Quiz Python - Introduction complète'));

-- Possibilités pour les questions QCM Python - Introduction complète
INSERT INTO possibility (possibility, question_id) VALUES
-- D'où vient le nom "Python" ?
('Monty Python', (SELECT id FROM question WHERE question = 'D où vient le nom "Python" ?' AND questionnaire_id = (SELECT id FROM questionnaires WHERE title = 'Quiz Python - Introduction complète'))),
('Le serpent', (SELECT id FROM question WHERE question = 'D où vient le nom "Python" ?' AND questionnaire_id = (SELECT id FROM questionnaires WHERE title = 'Quiz Python - Introduction complète'))),
('La mythologie grecque', (SELECT id FROM question WHERE question = 'D où vient le nom "Python" ?' AND questionnaire_id = (SELECT id FROM questionnaires WHERE title = 'Quiz Python - Introduction complète'))),

-- Quel domaine utilise Python pour l'apprentissage automatique ?
('Intelligence artificielle', (SELECT id FROM question WHERE question = 'Quel domaine utilise Python pour l apprentissage automatique ?' AND questionnaire_id = (SELECT id FROM questionnaires WHERE title = 'Quiz Python - Introduction complète'))),
('Développement web', (SELECT id FROM question WHERE question = 'Quel domaine utilise Python pour l apprentissage automatique ?' AND questionnaire_id = (SELECT id FROM questionnaires WHERE title = 'Quiz Python - Introduction complète'))),
('Robotique', (SELECT id FROM question WHERE question = 'Quel domaine utilise Python pour l apprentissage automatique ?' AND questionnaire_id = (SELECT id FROM questionnaires WHERE title = 'Quiz Python - Introduction complète'))),

-- Quelle structure permet de répéter une action en Python ?
('for', (SELECT id FROM question WHERE question = 'Quelle structure permet de répéter une action en Python ?' AND questionnaire_id = (SELECT id FROM questionnaires WHERE title = 'Quiz Python - Introduction complète'))),
('if', (SELECT id FROM question WHERE question = 'Quelle structure permet de répéter une action en Python ?' AND questionnaire_id = (SELECT id FROM questionnaires WHERE title = 'Quiz Python - Introduction complète'))),
('while', (SELECT id FROM question WHERE question = 'Quelle structure permet de répéter une action en Python ?' AND questionnaire_id = (SELECT id FROM questionnaires WHERE title = 'Quiz Python - Introduction complète'))),

-- Comment afficher du texte à l'écran en Python ?
('print', (SELECT id FROM question WHERE question = 'Comment afficher du texte à l écran en Python ?' AND questionnaire_id = (SELECT id FROM questionnaires WHERE title = 'Quiz Python - Introduction complète'))),
('echo', (SELECT id FROM question WHERE question = 'Comment afficher du texte à l écran en Python ?' AND questionnaire_id = (SELECT id FROM questionnaires WHERE title = 'Quiz Python - Introduction complète'))),
('write', (SELECT id FROM question WHERE question = 'Comment afficher du texte à l écran en Python ?' AND questionnaire_id = (SELECT id FROM questionnaires WHERE title = 'Quiz Python - Introduction complète'))),

-- Quel type de données est immuable en Python ?
('tuple', (SELECT id FROM question WHERE question = 'Quel type de données est immuable en Python ?' AND questionnaire_id = (SELECT id FROM questionnaires WHERE title = 'Quiz Python - Introduction complète'))),
('list', (SELECT id FROM question WHERE question = 'Quel type de données est immuable en Python ?' AND questionnaire_id = (SELECT id FROM questionnaires WHERE title = 'Quiz Python - Introduction complète'))),
('dict', (SELECT id FROM question WHERE question = 'Quel type de données est immuable en Python ?' AND questionnaire_id = (SELECT id FROM questionnaires WHERE title = 'Quiz Python - Introduction complète'))),

-- Quelle bibliothèque est utilisée pour la science des données ?
('Pandas', (SELECT id FROM question WHERE question = 'Quelle bibliothèque est utilisée pour la science des données ?' AND questionnaire_id = (SELECT id FROM questionnaires WHERE title = 'Quiz Python - Introduction complète'))),
('NumPy', (SELECT id FROM question WHERE question = 'Quelle bibliothèque est utilisée pour la science des données ?' AND questionnaire_id = (SELECT id FROM questionnaires WHERE title = 'Quiz Python - Introduction complète'))),
('Matplotlib', (SELECT id FROM question WHERE question = 'Quelle bibliothèque est utilisée pour la science des données ?' AND questionnaire_id = (SELECT id FROM questionnaires WHERE title = 'Quiz Python - Introduction complète'))),

-- Quel framework web est mentionné dans le cours ?
('Django', (SELECT id FROM question WHERE question = 'Quel framework web est mentionné dans le cours ?' AND questionnaire_id = (SELECT id FROM questionnaires WHERE title = 'Quiz Python - Introduction complète'))),
('Flask', (SELECT id FROM question WHERE question = 'Quel framework web est mentionné dans le cours ?' AND questionnaire_id = (SELECT id FROM questionnaires WHERE title = 'Quiz Python - Introduction complète'))),
('FastAPI', (SELECT id FROM question WHERE question = 'Quel framework web est mentionné dans le cours ?' AND questionnaire_id = (SELECT id FROM questionnaires WHERE title = 'Quiz Python - Introduction complète')));
