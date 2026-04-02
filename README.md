# Youtubr_Recommander
## 📋 Description

YouTube Recommander est une application de simulation de plateforme vidéo inspirée de YouTube, développée en Java. Elle permet aux utilisateurs de créer des comptes (standard ou premium), de gérer des vidéos, des chaînes, des commentaires et d'interagir avec du contenu via des likes et des abonnements.

Le système intègre un algorithme de pertinence qui analyse l'engagement des utilisateurs pour recommander du contenu populaire.
🎯 Fonctionnalités principales
👤 Gestion des utilisateurs

    Inscription : Création de comptes utilisateurs standard ou premium
    Connexion/Déconnexion : Authentification sécurisée avec email et mot de passe
    Profils utilisateurs : Affichage des informations personnelles et statistiques
    Utilisateurs Premium : Options de souscription avec 3 formules (2,05/mois,5,57/mois,5,57/mois, 12,89$/mois)
    Persistance des données : Sauvegarde automatique dans des fichiers texte

## 🎥 Gestion des vidéos

    Création de vidéos : Titre, description, auteur, résolution
    Recherche de vidéos : Par titre
    Suppression de vidéos
    Likes : Système de likes pour les vidéos
    Statistiques : Nombre de vues, likes, commentaires
    Score de pertinence : Calcul automatique basé sur l'engagement

## 💬 Système de commentaires

    Ajout de commentaires : Sur n'importe quelle vidéo
    Likes sur commentaires : Système d'engagement
    Affichage : Liste complète des commentaires avec métadonnées

## 📺 Gestion des chaînes

    Création de chaînes : Avec nom et propriétaire
    Recherche de chaînes
    Abonnements : Suivre/Ne plus suivre des chaînes
    Publication de vidéos : Poster des vidéos dans une chaîne
    Statistiques : Nombre d'abonnés, nombre de vidéos

## 📊 Analyse de pertinence

    Score de pertinence : Formule = (Likes × 2) + (Commentaires × 5) + (Likes des commentaires)
    Classification :
        Très Pertinente : Score > 100
        Pertinente : Score > 50
        Intéressante : Score > 10
        Peu de retours : Score ≤ 10

## 🗂️ Architecture du projet
```
youtube-recommander/
│
├── channel/
│   ├── Channel.java          # Classe représentant une chaîne
│   └── ChannelBd.java         # Gestion de la base de données des chaînes
│
├── user/
│   ├── User.java              # Classe utilisateur standard
│   ├── PremiumUser.java       # Classe utilisateur premium
│   ├── UserBd.java            # Gestion de la base de données des utilisateurs
│   └── Comments.java          # Classe pour les commentaires
│
├── video/
│   ├── Video.java             # Classe représentant une vidéo
│   └── VideoBd.java           # Gestion de la base de données des vidéos
│
├── payment/
│   └── Payment.java           # Classe pour les paiements
│
├── idGenerator/
│   └── IdGenerator.java       # Générateur d'identifiants uniques
│
└── youtube.java               # Classe principale avec l'interface utilisateur
```
##🔧 Prérequis

    Java JDK : Version 8 ou supérieure
    Système d'exploitation : Windows, macOS, Linux
    Terminal/Invite de commandes : Pour l'exécution

### 📦 Installation et compilation
1️⃣ Cloner ou télécharger le projet
bash

# Si vous utilisez Git
git clone <url-du-repo>
cd youtube-recommander

# Ou téléchargez et extrayez l'archive ZIP

2️⃣ Compiler le projet (Java)
bash

# Compiler tous les fichiers Java
javac -d bin channel/*.java user/*.java video/*.java payment/*.java idGenerator/*.java youtube.java

# Ou compiler depuis la racine
javac channel/*.java user/*.java video/*.java payment/*.java idGenerator/*.java youtube.java

3️⃣ Exécuter le programme (Java)
bash

# Si vous avez compilé avec l'option -d bin
java -cp bin youtube

# Sinon, depuis la racine
java youtube

🐍 Compilation et exécution en Python

Note : Ce projet est écrit en Java. Pour une version Python, une réécriture complète serait nécessaire.

Si vous souhaitez créer une version Python :
bash

# Exemple de structure pour Python
python3 main.py

🔨 Compilation et exécution en C++

Note : Ce projet est écrit en Java. Pour une version C++, une réécriture complète serait nécessaire.

Si vous souhaitez créer une version C++ :
bash

# Compilation avec g++
g++ -std=c++11 -o youtube main.cpp channel.cpp user.cpp video.cpp

# Exécution
./youtube

🚀 Utilisation
Menu principal

Au démarrage, vous verrez :

~~~ YOUTUBE RECOMMANDER ~~~

1. S'inscrire
2. Se Connecter
3. Se déconnecter
0. QUITTER

Inscription

    Choisissez l'option 1 (S'inscrire)
    Sélectionnez le type de compte :
        1 : Utilisateur standard
        2 : Utilisateur premium (avec choix de souscription)
    Remplissez les informations demandées

Connexion

    Choisissez l'option 2 (Se Connecter)
    Entrez votre email et mot de passe
    Accédez au menu utilisateur

Menu utilisateur

Après connexion, vous pouvez :

1. Poster une video
2. Rechercher une video
3. Supprimer une video
4. Créer une chaine
5. Rechercher une chaine
6. Suivre une chaine
7. Arrêter de suivre une chaine
8. Poster une video dans la chaine
9. Supprimer une video de la chaine
10. Retour au menu principal
11. Afficher mon profil
0. Fermer le Youtube Recommander

💾 Persistance des données

Le système sauvegarde automatiquement les données dans des fichiers texte :

    Utilisateurs : us001.txt, us002.txt, etc.
    Index des utilisateurs : user_index.txt
    Compteurs d'ID : counters.dat

Format des fichiers utilisateur

USERTYPE:PREMIUM
USERINFO:us001|John Doe|Male|123456789|john@email.com|password123|OPTION 1 : 2,05$/mois
VIDEOS:2
VID001|Ma première vidéo|John Doe|Description...|1080|5|100|3|2026-01-02
...
LIKES:1
VID002
COMMENTS:1
COM001|John Doe|Super vidéo!|10|2026-01-02

🧮 Algorithme de pertinence

L'application calcule un score de pertinence pour chaque vidéo :
java

Score = (Likes Vidéo × 2) + (Nombre de Commentaires × 5) + (Somme des likes des commentaires)

Classification
Score	Statut
> 100	Très Pertinente (Incontournable)
> 50	Pertinente (Populaire)
> 10	Intéressante
≤ 10	Peu de retours (Nouvelle ou niche)
🔐 Sécurité

    Les mots de passe sont stockés en clair (⚠️ À améliorer en production)
    Authentification par email/mot de passe
    Validation des entrées utilisateur
    Gestion des erreurs de fichiers

🐛 Gestion des erreurs

Le système gère :

    Erreurs de lecture/écriture de fichiers
    Utilisateurs introuvables
    Vidéos inexistantes
    Entrées invalides
    Fichiers corrompus

📈 Améliorations possibles
Sécurité

    Hachage des mots de passe (BCrypt, SHA-256)
    Système de tokens pour les sessions
    Validation robuste des entrées

Fonctionnalités

    Système de recommandations personnalisées
    Historique de visionnage
    Playlists
    Notifications
    Recherche avancée (par tags, catégories)
    Système de signalement de contenu

Performance

    Migration vers une base de données SQL
    Cache pour les recherches fréquentes
    Pagination des résultats

Interface

    Interface graphique (JavaFX, Swing)
    Application web (Spring Boot + React)
    API REST

📝 Exemple d'utilisation
java

// Créer un utilisateur
User user = User.createUser("Alice", "Female", 123456789, "alice@email.com", "pass123");

// Créer une vidéo
Video video = Video.createVideo("Tutoriel Java", "Alice", "Apprendre Java", 1080, LocalDate.now());

// Ajouter un commentaire
Comments comment = Comments.createComment("Bob", LocalDate.now(), "Excellent tutoriel!");
video.addComment(comment);

// Calculer la pertinence
double score = video.getRelevanceScore();
String status = video.getPertinenceStatus();

👥 Contributeurs

    Développeur principal : [Votre nom]
    Date de création : Janvier 2026
    Version : 1.0.0

📄 Licence

Ce projet est développé à des fins éducatives.
📞 Support

Pour toute question ou problème :

    Créez une issue sur GitHub
    Contactez l'équipe de développement
    Consultez la documentation

🙏 Remerciements

Merci d'utiliser YouTube Recommander ! N'hésitez pas à contribuer ou à suggérer des améliorations.

Dernière mise à jour : 2 janvier 2026
