package model.arme;

import model.grille.Position;

/**
 * La classe {@code Bale} représente une balle dans le jeu, 
 * associée à une position spécifique sur une grille.
 */
public class Bale {
    /** La position actuelle de la balle sur la grille. */
    protected Position position;

    /**
     * Constructeur par défaut de la classe {@code Bale}.
     */
    public Bale() {
    }

    /**
     * Définit la position de la balle.
     * 
     * @param position La nouvelle position de la balle.
     */
    public void setPosition(Position position) {
        this.position = position;
    }

    /**
     * Obtient la position actuelle de la balle.
     * 
     * @return La position actuelle de la balle.
     */
    public Position getPosition() {
        return position;
    }

    /**
     * Déplace la balle dans une direction donnée en modifiant ses coordonnées.
     * 
     * @param deltaX Le déplacement sur l'axe X.
     * @param deltaY Le déplacement sur l'axe Y.
     */
    public void deplacer(int deltaX, int deltaY) {
        this.position = new Position(
            this.position.getX() + deltaX, 
            this.position.getY() + deltaY
        );
    }
}
