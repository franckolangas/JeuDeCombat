package model.joueur;

import java.util.Random;
import model.grille.Position;

/**
 * Implémentation de la stratégie d'un joueur agissant de manière aléatoire.
 * Cette classe permet à un joueur de prendre des actions aléatoires lors de son tour,
 * telles que tirer, activer un bouclier, placer une bombe, une mine ou se déplacer.
 */
public class StrategieJoueurAleatoire extends Joueur {

    /**
     * Constructeur de la classe StrategieJoueurAleatoire.
     *
     * @param nom      Le nom du joueur.
     * @param energie La quantité d'énergie du joueur.
     * @param position La position du joueur sur la grille.
     * @param conf     La configuration du jeu.
     */
    public StrategieJoueurAleatoire(String nom, int energie, Position position, ConfigDuJeu conf) {
        super(nom, energie, position, conf);
    }

    /**
     * Demande une action aléatoire à réaliser par le joueur.
     * Les actions possibles incluent : tirer, activer un bouclier,
     * placer une bombe, placer une mine ou se déplacer.
     */
    @Override
    public void demanderAction() {
        Random random = new Random();
        int choix = random.nextInt(5);

        switch (choix) {
            case 0:
                tireAleatoire();
                break;

            case 1:
                activerBouclier();
                System.out.println(getNom() + " active son bouclier !");
                break;

            case 2:
                placerBombe();
                break;

            case 3:
                placerMine();
                break;

            case 4:
                deplacerAleatoire();
                break;

            default:
                System.out.println(getNom() + " reste immobile.");
                break;
        }
    }

    /**
     * Tire dans une direction aléatoire.
     * Cette méthode choisit une direction aléatoire parmi "haut", "bas", "gauche", "droite"
     * et effectue un tir dans cette direction.
     */
    public void tireAleatoire() {
        Random random = new Random();
        String[] directions = { "h", "b", "g", "d" }; // h = haut, b = bas, g = gauche, d = droite
        String directionAleatoire = directions[random.nextInt(directions.length)];

        this.tirer(directionAleatoire); // Tir dans une direction aléatoire
        System.out.println(this.getNom() + " tire dans la direction : " + directionAleatoire);
    }

    /**
     * Déplace le joueur dans une direction aléatoire.
     * Cette méthode choisit une direction aléatoire parmi haut, bas, gauche, droite,
     * puis déplace le joueur dans cette direction.
     */
    public void deplacerAleatoire() {
        Random random = new Random();
        int[][] direction = {
                { -1, 0 }, // haut
                { 1, 0 }, // bas
                { 0, -1 }, // gauche
                { 0, 1 } // droite
        };
        // Choisir une direction aléatoire
        int[] directionChoisie = direction[random.nextInt(direction.length)];
        int deltaX = directionChoisie[0];
        int deltaY = directionChoisie[1];
        System.out.println(getNom() + " se déplace aléatoirement.");
        deplacer(deltaX, deltaY);
        // setEnergie(deltaY); // Optionnel, pour modifier l'énergie en fonction du déplacement
    }
}
