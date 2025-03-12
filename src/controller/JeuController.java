package controller;

import model.joueur.ConfigDuJeu;

/**
 * Contrôleur principal du jeu.
 * Cette classe sert d'intermédiaire entre le modèle (ConfigDuJeu) et la logique de jeu.
 * Elle est responsable de la gestion du tour de jeu et de l'interaction avec le modèle pour 
 * mettre à jour l'état du jeu à chaque tour.
 */
public class JeuController {
    private ConfigDuJeu modele;

    /**
     * Constructeur du contrôleur du jeu.
     * Initialise le contrôleur avec un modèle de jeu spécifié (ConfigDuJeu).
     * 
     * @param modele Le modèle de jeu qui contient la logique du jeu et l'état de la partie.
     */
    public JeuController(ConfigDuJeu modele) {
        this.modele = modele;
    }

    /**
     * Démarre un tour de jeu.
     * Cette méthode déclenche la logique pour jouer un tour dans le modèle.
     * Elle appelle la méthode `jouerPartie()` du modèle pour effectuer les actions du tour.
     */
    public void startTour() {
        modele.jouerPartie(); // Mise à jour du modèle
    }
}

