package model.joueur;

import java.util.Random;
import model.arme.Bombe;
import model.arme.Bouclier;
import model.arme.Fusil;
import model.arme.Mine;
import model.grille.Position;

/**
 * Implémentation de la stratégie d'un joueur intelligent.
 * Ce joueur prend des décisions basées sur la détection des ennemis, la disponibilité
 * des armes et des boucliers, et effectue des actions en conséquence.
 */
public class StrategieJoueurIntelligent extends Joueur {

    /**
     * Constructeur de la classe StrategieJoueurIntelligent.
     *
     * @param nom      Le nom du joueur.
     * @param energie La quantité d'énergie du joueur.
     * @param position La position du joueur sur la grille.
     * @param conf     La configuration du jeu.
     */
    public StrategieJoueurIntelligent(String nom, int energie, Position position, ConfigDuJeu conf) {
        super(nom, energie, position, conf);
    }

    /**
     * Demande une action au joueur intelligent.
     * L'action est déterminée en fonction de la présence d'un autre joueur sur le même axe
     * et de la disponibilité des armes et boucliers.
     */
    @Override
    public void demanderAction() {
        // Vérifie s'il y a un joueur sur le même axe
        if (autreJoueurSurMemeAxe()) {
            if (contientArme(Fusil.class)) {
                System.out.println(getNom() + " détecte un ennemi sur le même axe et tire !");
                tirerSurAxe();
                return;
            } else {
                System.out.println(getNom()
                        + " détecte un ennemi sur le même axe mais ne peut pas tirer. Il se déplace aléatoirement.");
                deplacerAleatoire();
                return;
            }
        }

        // Si aucun joueur n'est sur le même axe, essaye de poser une bombe ou une mine
        if (contientArme(Bombe.class)) {
            placerBombe();
            System.out.println(getNom() + " ne détecte aucun ennemi, mais place une bombe !");
            return;
        }

        if (contientArme(Mine.class)) {
            placerMine();
            System.out.println(getNom() + " ne détecte aucun ennemi, mais place une mine !");
            return;
        }

        // Si un bouclier est disponible, l'activer
        if (contientArme(Bouclier.class) && !bouclierActif) {
            activerBouclier();
            System.out.println(getNom() + " active son bouclier !");
            return;
        }

        // Si aucune arme n'est disponible (fusil, bombe, mine), déplacement aléatoire
        System.out.println(getNom() + " n'a ni fusil, ni bombe, ni mine. Il se déplace aléatoirement.");
        deplacerAleatoire();
    }

    /**
     * Tire dans la direction où un autre joueur est détecté sur le même axe.
     * Le tir est effectué vers le joueur situé sur le même axe (haut, bas, gauche ou droite).
     */
    public void tirerSurAxe() {
        if (estJoueurSurAxe("h")) {
            tirer("h");
            System.out.println(getNom() + " tire vers le haut !");
        } else if (estJoueurSurAxe("b")) {
            tirer("b");
            System.out.println(getNom() + " tire vers le bas !");
        } else if (estJoueurSurAxe("g")) {
            tirer("g");
            System.out.println(getNom() + " tire vers la gauche !");
        } else if (estJoueurSurAxe("d")) {
            tirer("d");
            System.out.println(getNom() + " tire vers la droite !");
        }
    }

    /**
     * Vérifie s'il y a un joueur sur un axe donné (haut, bas, gauche, droite).
     *
     * @param direction la direction à vérifier ("h", "b", "g", "d")
     * @return true si un joueur est détecté dans cette direction
     */
    private boolean estJoueurSurAxe(String direction) {
        int x = this.position.getX();
        int y = this.position.getY();
        int distance = 8;

        switch (direction) {
            case "h":
                for (int i = 1; i <= distance; i++) {
                    if (grille.estDansLesLimites(x - i, y) && grille.getCellule(x - i, y).getType() instanceof Joueur) {
                        return true;
                    }
                }
                break;
            case "b":
                for (int i = 1; i <= distance; i++) {
                    if (grille.estDansLesLimites(x + i, y) && grille.getCellule(x + i, y).getType() instanceof Joueur) {
                        return true;
                    }
                }
                break;
            case "g":
                for (int i = 1; i <= distance; i++) {
                    if (grille.estDansLesLimites(x, y - i) && grille.getCellule(x, y - i).getType() instanceof Joueur) {
                        return true;
                    }
                }
                break;
            case "d":
                for (int i = 1; i <= distance; i++) {
                    if (grille.estDansLesLimites(x, y + i) && grille.getCellule(x, y + i).getType() instanceof Joueur) {
                        return true;
                    }
                }
                break;
        }
        return false;
    }

    /**
     * Méthode pour déplacer le joueur dans une direction aléatoire si aucune autre
     * action utile n'est possible.
     */
    public void deplacerAleatoire() {
        Random random = new Random();
        int[][] directions = {
                { -1, 0 }, // haut
                { 1, 0 }, // bas
                { 0, -1 }, // gauche
                { 0, 1 } // droite
        };

        // Choisir une direction aléatoire
        int[] directionChoisie = directions[random.nextInt(directions.length)];
        int deltaX = directionChoisie[0];
        int deltaY = directionChoisie[1];

        System.out.println(getNom() + " se déplace aléatoirement.");
        deplacer(deltaX, deltaY);
    }
}
