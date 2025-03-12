package model.arme;

import model.grille.*;
import model.joueur.Joueur;

/**
 * La classe {@code Mine} représente un équipement explosif pouvant être placé sur une grille.
 * Elle inflige des dégâts lorsqu'elle est activée et est associée à un joueur.
 */
public class Mine implements TypeCellule, Equipement {
    /**
     * Symbole représentant une mine sur la grille.
     */
    protected final String SYMBOLE = "M";

    /**
     * Dégâts infligés par l'explosion de la mine.
     */
    protected int degat;

    /**
     * Coût en points pour poser la mine.
     */
    protected int cout;

    /**
     * Position de la mine sur la grille.
     */
    protected Position position;

    /**
     * Joueur qui a posé la mine.
     */
    private Joueur joueur;

    /**
     * Constructeur de la classe {@code Mine}.
     * 
     * @param position la position initiale de la mine.
     * @param joueur   le joueur associé à cette mine.
     */
    public Mine(Position position, Joueur joueur) {
        this.degat = 30;
        this.cout = 5;
        this.position = position;
        this.joueur = joueur;
    }

    /**
     * Retourne le joueur associé à la mine.
     * 
     * @return le joueur ayant posé la mine.
     */
    public Joueur getJoueur() {
        return joueur;
    }

    /**
     * Retourne la position de la mine sur la grille.
     * 
     * @return la position actuelle de la mine.
     */
    public Position getPosition() {
        return position;
    }

    /**
     * Retourne le symbole représentant la mine sur la grille.
     * 
     * @return une chaîne contenant le symbole de la mine.
     */
    @Override
    public String getSymbol() {
        return this.SYMBOLE;
    }

    /**
     * Active l'équipement (la mine) lorsqu'elle est déclenchée.
     * Cette méthode n'est pas encore implémentée.
     * 
     * @return {@code true} si l'activation est réussie.
     * @throws UnsupportedOperationException si la méthode n'est pas encore implémentée.
     */
    @Override
    public boolean activerEquipement() {
        throw new UnsupportedOperationException("Unimplemented method 'activerEquipement'");
    }

    /**
     * Retourne une représentation textuelle de la mine.
     * 
     * @return une chaîne indiquant qu'il s'agit d'une mine.
     */
    @Override
    public String toString() {
        return "mine";
    }
}
