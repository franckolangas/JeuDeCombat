# Jeu de Stratégie - JeuDeStrategique

## Auteurs
- **Diallo Boubacar Sadio** : 22211641  
- **Diare Youssouf** : 22008756  
- **EMAM Mohamed El Mamy** : 22019076  
- **OLANGASSICKA ONDOUMBOU Franck Loick** : 22112035  

---

## Description

**JeuDeStrategique** est un projet de jeu de stratégie développé en Java, mettant en avant des mécaniques stratégiques et un système flexible pour les joueurs, avec un rendu fluide de la partie.

---

## Prérequis

- **Java Development Kit (JDK)** : version 11 ou supérieure.
- **Apache Ant** : pour automatiser les tâches de construction.

---
##
apres execution n'oubliez pas de depiler les fenetres affichants les grilles proxy des joueurs

## Installation et Compilation

1. **Cloner le projet** :
   ```bash
    svn checkout https://redmine-etu.unicaen.fr/svn/diare-diallo-olangassicka-emam
2. : cd livraison
    ant clean     # Nettoie les fichiers générés
    ant compile   # Compile le projet
    ant dist      # Génère le fichier .jar de distribution
    ant run       # Exécute le jeu
    ant javadoc   # Génère la documentation Javadoc
    java -jar dist/JeuDeStrategique-0.1.jar # Exécuter l'application sur différentes machines
