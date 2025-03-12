package model.arme;

import model.grille.*;
import model.joueur.Joueur;

/**
 * La classe {@code Bombe} représente une bombe dans le jeu. 
 * Elle implémente les interfaces {@code TypeCellule} et {@code Equipement}.
 */
public class Bombe implements TypeCellule, Equipement {

    /** Symbole utilisé pour représenter la bombe dans la grille. */
    protected final String SYMBOLE = "*";

    /** Les dégâts causés par la bombe lors de son activation. */
    protected int degat;

    /** Le coût pour utiliser ou poser la bombe. */
    protected int cout;

    /** La position actuelle de la bombe sur la grille. */
    public Position position;

    /** Le délai avant l'activation de la bombe. */
    public int delais;

    /** Le joueur associé à cette bombe. */
    private Joueur joueur;

    /**
     * Constructeur de la classe {@code Bombe}.
     * 
     * @param position La position initiale de la bombe sur la grille.
     * @param joueur   Le joueur qui possède ou utilise la bombe.
     */
    public Bombe(Position position, Joueur joueur) {
        this.degat = 30;
        this.cout = 5;
        this.position = position;
        this.delais = 1;
        this.joueur = joueur;
    }

    /**
     * Récupère le joueur associé à cette bombe.
     * 
     * @return Le joueur qui possède cette bombe.
     */
    public Joueur getJoueur() {
        return joueur;
    }

    /**
     * Récupère le symbole utilisé pour représenter la bombe.
     * Implémenté depuis l'interface {@code TypeCellule}.
     * 
     * @return Le symbole de la bombe (une chaîne de caractères).
     */
    @Override
    public String getSymbol() {
        return this.SYMBOLE;
    }

    /**
     * Active l'équipement (bombe) et retourne si l'activation a réussi.
     * Implémenté depuis l'interface {@code Equipement}.
     * 
     * @return {@code true} si l'activation est réussie.
     */
    @Override
    public boolean activerEquipement() {
        return true;
    }

    /**
     * Retourne une représentation sous forme de chaîne de caractères de l'objet bombe.
     * 
     * @return Une chaîne indiquant "bombe".
     */
    @Override
    public String toString() {
        return "bombe";
    }
}
